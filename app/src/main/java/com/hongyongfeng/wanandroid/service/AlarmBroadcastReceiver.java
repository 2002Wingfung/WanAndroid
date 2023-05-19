package com.hongyongfeng.wanandroid.service;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.ARTICLE_URL;
import static com.hongyongfeng.wanandroid.util.Constant.ACTION;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wingfung Hung
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    /**
     *  新建了一个线程池，用于活动还未启动时接收广播并发送通知
     */
    public ThreadPoolExecutor es =new ThreadPoolExecutor(1,3,15, TimeUnit.MINUTES,new ArrayBlockingQueue<>(3),
            Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    //请求获取一篇最新的文章
                    HttpUtil.sendHttpRequest(ARTICLE_URL, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            int indexStart=response.indexOf('[');
                            int indexEnd=response.lastIndexOf(']');
                            try {
                                JSONArray jsonArray=new JSONArray(response.substring(indexStart,indexEnd+1));
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                String title=jsonObject.getString("title");
                                String link=jsonObject.getString("link");
                                //获取状态通知栏管理器
                                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                //延迟的意图跳转
                                PendingIntent pendingIntent;
                                //跳转活动并传递参数
                                Intent intent=new Intent(context, WebViewActivity.class);
                                intent.putExtra("url",link);
                                intent.putExtra("state",1);
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    //设置信息通道,否则不能发送信息
                                    NotificationChannel mChannel = new NotificationChannel("channelId", "123", NotificationManager.IMPORTANCE_HIGH);
                                    manager.createNotificationChannel(mChannel);
                                }
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                    //设置延迟意图
                                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
                                } else {
                                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_UPDATE_CURRENT);
                                }
                                //设置通知的内容、图片以及点击事件
                                Notification notification=new NotificationCompat.Builder(context,"channelId")
                                        .setContentTitle("每日文章")
                                        .setContentText(title)
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.ic_notification)
                                        .setContentIntent(pendingIntent)
                                        .build();
                                manager.notify(ONE,notification);
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