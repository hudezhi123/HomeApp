package com.hudezhi.freedom.homeapp.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.bluetooth.adapter.ChatAdapter;
import com.hudezhi.freedom.homeapp.bluetooth.bean.DeviceInfo;
import com.hudezhi.freedom.homeapp.bluetooth.bean.HAPMessage;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

public class ChatActivity extends Activity {

    private BluetoothSocket serverGet;
    private BluetoothSocket clientGet;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager manager;
    private BluetoothSocket finalSocket;

    @BindView(id = R.id.btn_send_message)
    private Button btnSend;
    @BindView(id = R.id.edit_content_blue)
    private EditText editContent;
    @BindView(id = R.id.chat_recycler)
    private RecyclerView recyclerView;

    private BluetoothAdapter mBluetoothAdapter;
    private DeviceInfo deviceInfo;
    private String address = "";
    private String name;
    private BluetoothDevice device = null;

    private BluetoothServerSocket server;


    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private static final int SEND = 1;
    private static final int RECEIVE = 2;
    private static final int BTN_ENABLE = 3;
    private static final int CONNECT_SUCCESS = 4;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEND:
                    String content_send = (String) msg.obj;
                    HAPMessage mHAP = new HAPMessage();
                    mHAP.setContent(content_send);
                    mHAP.setName(name);
                    mHAP.setViewType(HAPMessage.VIEW_TYPE_SEND);
                    chatAdapter.addMsgItem(mHAP);
                    break;
                case RECEIVE:
                    String content_receive = (String) msg.obj;
                    HAPMessage mHAPMessage = new HAPMessage();
                    mHAPMessage.setContent(content_receive);
                    mHAPMessage.setName(name);
                    mHAPMessage.setViewType(HAPMessage.VIEW_TYPE_RECEIVE);
                    chatAdapter.addMsgItem(mHAPMessage);
                    break;
                case BTN_ENABLE:
                    btnSend.setEnabled(true);
                    btnSend.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    break;
                case CONNECT_SUCCESS:
                    Toast.makeText(ChatActivity.this, "连接成功！", Toast.LENGTH_SHORT).show();
//                    if (clientGet != null) {
//                        try {
//                            clientGet.close();
//                            clientGet = null;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        AnnotateUtil.initBindView(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            deviceInfo = bundle.getParcelable("DeviceInfo");
            address = deviceInfo.getAddress();
            name = deviceInfo.getName();
            device = deviceInfo.getDevice();
        }
        init();

//        startServerSocket();
        connectToServerSocket();
    }

    private void bindSocket() {
        final BluetoothDevice knowDevice = mBluetoothAdapter.getRemoteDevice(address);
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        if (bondedDevices.contains(knowDevice)) {

        }
    }

    private void connectToServerSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());
                    clientGet = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                    clientGet.connect();
                    finalSocket = clientGet;
                    getSocketContent(clientGet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btnSend.setBackgroundColor(getResources().getColor(R.color.colorPressGray));
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        chatAdapter = new ChatAdapter(this);
        recyclerView.setAdapter(chatAdapter);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void startServerSocket() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(mBluetoothAdapter.getName(), uuid);
                    // 阻塞等待
                    serverGet = server.accept();
                    finalSocket = serverGet;
                    if (serverGet != null) {
                        mHandler.sendEmptyMessage(CONNECT_SUCCESS);

                        getSocketContent(serverGet);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void getSocketContent(final BluetoothSocket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream input = null;
                try {
                    input = socket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    try {
                        String result = "";
                        while ((len = input.read(buffer)) != -1) {
                            result += new String(buffer, 0, len);
                        }
                        if (TextUtils.isEmpty(result)) {
                            return;
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.obj = result;
                        msg.what = RECEIVE;
                        mHandler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }

    private void sendMessage(String message) {
        OutputStream output = null;
        try {
            output = finalSocket.getOutputStream();
            byte[] byteArray = (message + " ").getBytes();
            byteArray[byteArray.length - 1] = 0;
            output.write(byteArray);
            output.flush();
            Message msg = mHandler.obtainMessage();
            msg.what = SEND;
            msg.obj = message;
            mHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onDestroy() {
        try {
            if (serverGet != null) {
                serverGet.close();
            }
            if (clientGet != null) {
                clientGet.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void onSend(View view) {
        final String message = editContent.getText().toString();
        if (TextUtils.isEmpty(message)) {
            return;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendMessage(message);
                }
            }).start();

        }

    }
}
