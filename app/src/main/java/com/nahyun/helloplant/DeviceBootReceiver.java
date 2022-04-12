package com.nahyun.helloplant;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class DeviceBootReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.BOOT_COMPLETED")) {
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences sharedPreferences = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE);
            long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

            Calendar current_calendar = Calendar.getInstance();
            Calendar nextNofityTime = new GregorianCalendar();
            nextNofityTime.setTimeInMillis(sharedPreferences.getLong("nextNotifyTime", millis));

            if (current_calendar.after(nextNofityTime)) {
                nextNofityTime.add(Calendar.DATE, 1);
            }

            Date currentDateTime = nextNofityTime.getTime();
            String date_string = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context.getApplicationContext(), "재부팅 후, 다음 알람은 " + date_string + " 입니다.", Toast.LENGTH_SHORT).show();

            if (manager != null) {
                manager.setRepeating(AlarmManager.RTC_WAKEUP, nextNofityTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }
}
