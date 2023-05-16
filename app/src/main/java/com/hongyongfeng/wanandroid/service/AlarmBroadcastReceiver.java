package com.hongyongfeng.wanandroid.service;

import static androidx.core.app.NotificationCompat.DEFAULT_ALL;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("CLOCK_IN")) {
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
            //对builder进行配置
            builder.setContentTitle("上班打卡") //设置通知栏标题
                    .setContentText("上班打卡") //设置通知栏显示内容
                    .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知优先级
                    .setDefaults(DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_launcher_android)
                    .setAutoCancel(true); //设置这个标志当用户单击面板就可以将通知取消
            Intent mIntent=new Intent(context, MainActivity.class);  //绑定intent，点击图标能够进入某activity
            PendingIntent mPendingIntent=PendingIntent.getActivity(context, 0, mIntent,PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(mPendingIntent);
            manager.notify(0, builder.build());  //绑定Notification，发送通知请求
            System.out.println(true);
        }
    }

}