package com.hongyongfeng.wanandroid.service;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.ARTICLE_URL;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wingfung Hung
 */
public class LongRunningTimeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service","create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Long secondsNextMorning =getSecondsNext(10,0);
        Intent intentMorning = new Intent(this, AlarmBroadcastReceiver.class);
        intentMorning.setAction("CLOCK_IN");
        //获取到PendingIntent的意图对象
        PendingIntent piMorning = PendingIntent.getBroadcast(this, 0, intentMorning, PendingIntent.FLAG_IMMUTABLE);
        //设置事件
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +secondsNextMorning, piMorning);
        //提交事件，发送给 广播接收器
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
