package com.example.rhumphrey.temp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Debug";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private Button mPairBtn;
    private Button mFwdBtn;
    private Button mRevBtn;
    private Button mRightBtn;
    private Button mLeftBtn;
    private TextView mStatusTv;
    private BluetoothAdapter mBluetoothAdapter;
    private Boolean isPaired;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPairBtn = (Button) findViewById(R.id.pairBtn);
        mFwdBtn = (Button) findViewById(R.id.fwdBtn);
        mRevBtn = (Button) findViewById(R.id.revBtn);
        mRightBtn = (Button) findViewById(R.id.rightBtn);
        mLeftBtn = (Button) findViewById(R.id.leftBtn);
        mStatusTv = (TextView) findViewById(R.id.statusTv);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices == null || pairedDevices.size() == 0) {
            isPaired = false;
            showDisabled();
        } else {
            isPaired = true;
            showPairing();
        }

        mPairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaired) {
                    isPaired = false;
                    showDisabled();
                } else {
                    isPaired = true;
                    showEnabled();
                }
            }
        });

    }

    private void showPairing() {
        mStatusTv.setText("starting discoverability...");
        mStatusTv.setTextColor(Color.GRAY);

        mPairBtn.setText("CANCEL");
        mFwdBtn.setEnabled(false);
        mRevBtn.setEnabled(false);
        mRightBtn.setEnabled(false);
        mLeftBtn.setEnabled(false);
    }

    private void showEnabled() {
        mStatusTv.setText("Paired to XXX");
        mStatusTv.setTextColor(Color.BLUE);

        mPairBtn.setText("UNPAIR");
        mFwdBtn.setEnabled(true);
        mRevBtn.setEnabled(true);
        mRightBtn.setEnabled(true);
        mLeftBtn.setEnabled(true);
    }

    private void showDisabled() {
        mStatusTv.setText("UNPAIRED");
        mStatusTv.setTextColor(Color.RED);

        mPairBtn.setText("PAIR");
        mFwdBtn.setEnabled(false);
        mRevBtn.setEnabled(false);
        mRightBtn.setEnabled(false);
        mLeftBtn.setEnabled(false);
    }
}
