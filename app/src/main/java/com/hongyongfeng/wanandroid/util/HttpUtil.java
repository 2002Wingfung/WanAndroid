package com.hongyongfeng.wanandroid.util;

import static com.hongyongfeng.wanandroid.util.ThreadPools.es;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
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
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                t=c.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Class<?> type = field.getType();
                    String classType = type.toString();
                    switch (classType) {
                        case "int":
                            field.set(t, jsonObject.getInt(fieldName));
                            break;
                        case "class java.lang.String":
                            String value = jsonObject.getString(fieldName);
                            if (!"".equals(value)) {
                                field.set(t, value);
                            }
                            break;
                        default:
                            break;
                    }
                }
                list.add(t);
            }
        } catch (JSONException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        es.execute(new Runnable() {
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
        });

    }
}
