package com.hudezhi.freedom.homeapp.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.nfc.bean.AppInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NfcMainActivity extends Activity implements View.OnClickListener {

    private NfcAdapter nfcAdapter;
    private Button btnChoose, btnWrite;

    private PendingIntent pendingIntent;
    private static final int REQUEST_CODE = 101;
    private String package_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_main);
        prepareNFC();
        initView();
    }

    private void initView() {
        btnChoose = (Button) findViewById(R.id.btn_choose_app_start);
        btnWrite = (Button) findViewById(R.id.btn_write_to_nfc);
        btnChoose.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

    /**
     * NFC功能正式应用的准备
     */
    private void prepareNFC() {
        if (isNFCExist()) {
            isNFCTurnOn();
        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, NFCPopActivity.class), PendingIntent.FLAG_ONE_SHOT);
    }

    /**
     * 检测是否存在Nfc功能
     *
     * @return
     */
    private boolean isNFCExist() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "该设备不支持NFC功能！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 判断Nfc功能是否打开，如果没有打开，则开启该功能
     *
     * @return
     */
    private boolean isNFCTurnOn() {
        if (!nfcAdapter.isEnabled()) {
            Intent nfcIntent = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(nfcIntent);
        }
        return true;
    }


    /**
     * 向NFC标签中写东西
     */
    private void writeToNFC(Intent intent) {

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        } else {
            NdefRecord[] ndefRecords = new NdefRecord[]{NdefRecord.createApplicationRecord(package_name)};
            NdefMessage ndefMessage = new NdefMessage(ndefRecords);
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                NdefFormatable ndefFormatable = NdefFormatable.get(tag);
                try {
                    ndefFormatable.connect();
                    ndefFormatable.format(ndefMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ndef.connect();
                    if (ndef.isWritable()) {
                        int size = ndefMessage.toByteArray().length;
                        if (ndef.getMaxSize() >= size) {
                            ndef.writeNdefMessage(ndefMessage);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == 102) {
            package_name = data.getStringExtra("package_name");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        writeToNFC(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            //设置当前程序为优先处理NFC的程序
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_app_start:
                Intent intent = new Intent(NfcMainActivity.this, AppListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_write_to_nfc:

                break;
        }
    }
}
