package com.hongyongfeng.wanandroid.module.alarm;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Alarm extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        long time=System.currentTimeMillis()+10*1000;
//        manager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }
}
