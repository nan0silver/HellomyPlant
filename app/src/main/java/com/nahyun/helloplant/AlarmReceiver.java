package com.nahyun.helloplant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "알람!", Toast.LENGTH_SHORT).show();
        Log.e("AlarmReceiver", "alarm start");
    }
}
