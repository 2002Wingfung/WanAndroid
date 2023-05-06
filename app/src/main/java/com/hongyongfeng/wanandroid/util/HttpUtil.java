package com.hongyongfeng.wanandroid.util;

import android.util.Log;

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
            int indexEnd=toString.lastIndexOf(']');
            Field[] fields=c.getDeclaredFields();
            T t;
            JSONArray jsonArray=new JSONArray(toString.substring(indexStart,indexEnd+1));
//            for (int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                Iterator<String> keys = jsonObject.keys();
//                List<String> jsonFieldList=new ArrayList<>();
//                while (keys.hasNext()){
//                    String jsonField=keys.next();
//                    jsonFieldList.add(jsonField);
//                }
//                t=c.newInstance();
//
//
//                list.add(t);
//            }
//            JSONObject jsonObject=jsonArray.getJSONObject(0);
//            Iterator<String> keys = jsonObject.keys();
//            List<String> jsonFieldList=new ArrayList<>();
//            while (keys.hasNext()){
//                String jsonField=keys.next();
//                jsonFieldList.add(jsonField);
//            }
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                t=c.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Class<?> type = field.getType();
                    String classType = type.toString();
//                    System.out.print(classType);
//                    System.out.println(" "+fieldName);
                    switch (classType) {
                        case "int":
                            field.set(t, jsonObject.getInt(fieldName));
                            break;
                        case "class java.lang.String":
                            String value = jsonObject.getString(fieldName);
                            if ("".equals(value)) {
                                //Log.d("value", "null");
                            } else {
                                field.set(t, value);
                            }
                            break;
                        default:
                            //Log.d("defaultType", classType);
                            break;
                    }
                }
//                for (int j=0;j<fields.length;j++){
//                    fields[j].setAccessible(true);
//                    String fieldName=fields[j].getName();
//                    Class<?> type = fields[j].getType();
//                    String classType=type.toString();
////                    System.out.print(classType);
////                    System.out.println(" "+fieldName);
//                    switch (classType){
//                        case "int":
//                            fields[j].set(t,jsonObject.getInt(fieldName));
//                            break;
//                        case "class java.lang.String":
//                            String value=jsonObject.getString(fieldName);
//                            if ("".equals(value)){
//                                Log.d("value","null");
//                            }else {
//                                fields[j].set(t,value);
//                            }
//                            break;
//                        default:
//                            Log.d("defaultType",classType);
//                            break;
//                    }
//                }
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
