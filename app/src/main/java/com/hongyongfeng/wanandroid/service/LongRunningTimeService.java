package com.hongyongfeng.wanandroid.service;

import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class LongRunningTimeService extends Service {

    //第一次执行时的具体时间


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        es.execute(new Runnable() {
//            @Override
//            public void run() {
//                //在这里执行具体的逻辑操作
//
//            }
//        });

//        long time=System.currentTimeMillis();
//
        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        time+=24*60*60*1000;
//        //一天的毫秒数
//        Intent intent1=new Intent(this,LongRunningTimeService.class);
//        PendingIntent pendingIntent=PendingIntent.getService(this,0,intent1,0);
//        manager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        Long secondsNextMorning =getSecondsNext(10,0);
        Intent intentMorning = new Intent(this, AlarmBroadcastReceiver.class);
        intentMorning.setAction("CLOCK_IN");
        //获取到PendingIntent的意图对象
        PendingIntent piMorning = PendingIntent.getBroadcast(this, 0, intentMorning, PendingIntent.FLAG_UPDATE_CURRENT);     //设置事件
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + secondsNextMorning, piMorning); //提交事件，发送给 广播接收器
        return super.onStartCommand(intent, flags, startId);
    }

    private Long getSecondsNext(int hour,int minute) {
        long systemTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 选择的定时时间
        long selectTime = calendar.getTimeInMillis();
        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            selectTime = calendar.getTimeInMillis();
        }
        // 计算设定时间到现在时间的时间差
        Long seconds = selectTime-systemTime;

        return seconds.longValue();
    }
}
