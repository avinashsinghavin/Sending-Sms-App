package com.example.smsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.service.autofill.TextValueSanitizer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG="MAIN";
    private BroadcastReceiver reciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"onRECIVER :CALL........###");
            Bundle b=intent.getExtras();
            Object obj[]=(Object[])b.get("pdus");
            SmsMessage msgs[]=new SmsMessage[obj.length];
            TextView tv=(TextView)findViewById(R.id.tv);
            for(int i=0;i<msgs.length;i++){
                msgs[i]=SmsMessage.createFromPdu((byte[])obj[i]);
            }
            for(SmsMessage msg:msgs){
                String addr=msg.getOriginatingAddress();
                String body=msg.getMessageBody();
                tv.setText(body);
                Log.i("INRECIVER","##########message "+addr+" MESSAGE  "+body);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(reciver,new IntentFilter("android.provider.Telephony.SMS_RECIVED"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(reciver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
