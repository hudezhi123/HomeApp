package com.hudezhi.freedom.homeapp.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.bluetooth.bean.DeviceInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Communication extends Activity {

    private BluetoothSocket transferSocket;

    private DeviceInfo deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            deviceInfo = (DeviceInfo) bundle.getSerializable("DeviceInfo");
        }
    }

    private UUID startServerSocket(BluetoothAdapter bluetooth) {
        UUID uuid = UUID.fromString("");
        String name = deviceInfo.getName();
        try {
            final BluetoothServerSocket btServer = bluetooth.listenUsingRfcommWithServiceRecord(name, uuid);
            Thread acceptThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BluetoothSocket serverSocket = btServer.accept();
                        // TODO: 2017/8/21  开始监听消息

                        transferSocket = serverSocket;
                    } catch (Exception e) {

                    }
                }
            });
            acceptThread.start();
        } catch (Exception e) {

        }
        return uuid;
    }

    private void connectToServerSocket(BluetoothDevice device, UUID uuid) {
        try {
            BluetoothSocket clientSocket = device.createRfcommSocketToServiceRecord(uuid);
            clientSocket.connect();
            // TODO: 2017/8/21  监听消息

            transferSocket = clientSocket;
        } catch (Exception e) {

        }
    }

    private void sendMessage(BluetoothSocket socket, String message) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();

            byte[] byteArray = (message + " ").getBytes();
            byteArray[byteArray.length - 1] = 0;
            outputStream.write(byteArray);
        } catch (Exception e) {

        }
    }

    private boolean listening = false;

    private void listenForMessage(BluetoothSocket socket, StringBuilder incoming) {
        listening = true;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            InputStream inputStream = socket.getInputStream();
            int byteRead = -1;
            while (listening) {
                byteRead = inputStream.read(buffer);
                if (byteRead != -1) {
                    String result = "";
                    while ((byteRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
                        result = result + new String(buffer, 0, byteRead - 1);
                        byteRead = inputStream.read(buffer);
                    }
                    result = result + new String(buffer, 0, byteRead - 1);
                    incoming.append(result);
                }
            }
        } catch (Exception e) {

        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
