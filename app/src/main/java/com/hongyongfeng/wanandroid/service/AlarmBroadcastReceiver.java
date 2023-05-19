package com.hongyongfeng.wanandroid.service;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.app.NotificationCompat.DEFAULT_ALL;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.ARTICLE_URL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wingfung Hung
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public ThreadPoolExecutor es =new ThreadPoolExecutor(1,3,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(3),
            Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("CLOCK_IN")) {
//            //获取状态通知栏管理
//            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
//            //对builder进行配置
//            builder.setContentTitle("上班打卡") //设置通知栏标题
//                    .setContentText("上班打卡") //设置通知栏显示内容
//                    .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知优先级
//                    .setDefaults(DEFAULT_ALL)
//                    .setSmallIcon(R.drawable.ic_notification)
//                    .setAutoCancel(true); //设置这个标志当用户单击面板就可以将通知取消
//            Intent mIntent=new Intent(context, MainActivity.class);  //绑定intent，点击图标能够进入某activity
//            PendingIntent mPendingIntent=PendingIntent.getActivity(context, 0, mIntent,PendingIntent.FLAG_IMMUTABLE);
//            builder.setContentIntent(mPendingIntent);
//            manager.notify(0, builder.build());  //绑定Notification，发送通知请求
//            System.out.println(true);
            es.execute(new Runnable() {
                @Override
                public void run() {
                    HttpUtil.sendHttpRequest(ARTICLE_URL, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            //System.out.println(response);
                            int indexStart=response.indexOf('[');
                            int indexEnd=response.lastIndexOf(']');
                            try {
                                JSONArray jsonArray=new JSONArray(response.substring(indexStart,indexEnd+1));
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String title=jsonObject.getString("title");
                                String link=jsonObject.getString("link");
                                //System.out.println(link);

                                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                PendingIntent pendingIntent;
                                Intent intent=new Intent(context, WebViewActivity.class);
//                                Bundle bundle=new Bundle();
//                                bundle.putInt("state",1);
                                //bundle.putString("url1",link);
                                //intent.putExtras(bundle);
                                intent.putExtra("url",link);
                                intent.putExtra("state",1);
                                //System.out.println(bundle.getString("url1"));
//                                ArrayList<String > list=new ArrayList<>();
//                                list.add("1");
//                                list.add(link);
                                //System.out.println(list.get(1));
                                //intent.putStringArrayListExtra("list",list);
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel mChannel = new NotificationChannel("channelId", "123", NotificationManager.IMPORTANCE_HIGH);
                                    manager.createNotificationChannel(mChannel);
                                }
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
                                } else {
                                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_UPDATE_CURRENT);
                                }
                                Notification notification=new NotificationCompat.Builder(context,"channelId")
                                        .setContentTitle("每日文章")
                                        .setContentText(title)
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.ic_notification)
                                        .setContentIntent(pendingIntent)
                                        .build();
                                manager.notify(1,notification);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    }, null);
                }
            });
        }
    }

}