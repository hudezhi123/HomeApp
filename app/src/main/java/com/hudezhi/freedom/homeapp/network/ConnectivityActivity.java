

package com.hudezhi.freedom.homeapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;

public class ConnectivityActivity extends AppCompatActivity {
    private ConnectivityManager networkManager;
    private NetworkInfo networkInfo;
    private TextView textNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connctivity_main);
        initConnectivityManager();
        getNetworkInfo();
    }

    private void initConnectivityManager() {
        networkManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void getNetworkInfo() {
        Network[] networks = networkManager.getAllNetworks();
        for (Network net : networks) {
            NetworkInfo networkInfo = networkManager.getNetworkInfo(net);
            int type = networkInfo.getType();
            String typeName = networkInfo.getTypeName();
            NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
            String extraInfo = networkInfo.getExtraInfo();
            String reason = networkInfo.getReason();
            NetworkInfo.State state = networkInfo.getState();
            int subType = networkInfo.getSubtype();
            String subTypeName = networkInfo.getSubtypeName();

            textNetworkInfo = (TextView) findViewById(R.id.text_network_into);
            textNetworkInfo.setText("type : " + type + "\n"
                    + "typeName :" + typeName + "\n"
                    + "detailedState :" + detailedState + "\n"
                    + "extraInfo :" + extraInfo + "\n"
                    + "reason :" + reason + "\n"
                    + "state :" + state.toString() + "\n"
                    + "subType :" + subType + "\n"
                    + "subTypeName :" + subTypeName + "\n");
        }
    }
}
