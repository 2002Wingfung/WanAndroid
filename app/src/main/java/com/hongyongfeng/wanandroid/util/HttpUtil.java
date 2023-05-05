package com.hongyongfeng.wanandroid.util;

import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HttpUtil {


    public static <T> List<T> parseJSONWithJSONObject(String toString,Class<T> c) {
        List<T> list=new ArrayList<>();
        try {
            int indexStart=toString.indexOf('[');
            int indexEnd=toString.indexOf(']');
            Field[] fields=c.getDeclaredFields();
            T t;
            JSONArray jsonArray=new JSONArray(toString.substring(indexStart,indexEnd+1));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Iterator<String> keys = jsonObject.keys();
                List<String> jsonFieldList=new ArrayList<>();
                while (keys.hasNext()){
                    String jsonField=keys.next();
                    System.out.println(jsonField);
                    jsonFieldList.add(jsonField);
                    System.out.println(jsonObject.optString(jsonField));
                }

//                String id =jsonObject.getString("id");
//                String imagePath =jsonObject.getString("imagePath");
//                String url =jsonObject.getString("url");
//                Log.d("MainActivity","id is "+id);
//                Log.d("MainActivity","imagePath is "+imagePath);
//                Log.d("MainActivity","url is "+url);

                t=c.newInstance();
                for (int j=0;j<fields.length;j++){
                    fields[i].setAccessible(true);

                    fields[i].set(t,jsonObject.getString(jsonFieldList.get(j)));
                }
                list.add(t);
            }
        } catch (JSONException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (listener!=null){
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener!=null){
                        //回调onError()方法
                        listener.onError(e);
                    }
                }finally {
                    if (reader!=null&&listener!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            listener.onError(e);
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
