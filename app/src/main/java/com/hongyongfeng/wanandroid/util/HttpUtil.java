package com.hongyongfeng.wanandroid.util;

import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import android.util.Log;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static List<Map<String,Object>> parseJsonWithJSONObject(String string){
        List<Map<String,Object>> stringListMap=new ArrayList<>();
        try {
            int indexStart=string.indexOf('[');
            int indexEnd=string.lastIndexOf(']');
            JSONArray jsonArray=new JSONArray(string.substring(indexStart,indexEnd+1));
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            Iterator<String> keys = jsonObject.keys();
            List<String> jsonFieldList=new ArrayList<>();
            while (keys.hasNext()){
                String jsonField=keys.next();
                jsonFieldList.add(jsonField);
            }
            for (int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                Map<String,Object> stringMap = new HashMap<String,Object>();
                for (String field:jsonFieldList){
                    stringMap.put(field,jsonObject.get(field));
                    //System.out.println(jsonObject.get(field));
                }
                stringListMap.add(stringMap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringListMap;
    }


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
//                    field.set(t,jsonObject.get(fieldName));
                    Class<?> type = field.getType();
                    String classType = type.toString();
                    switch (classType) {
                        case "int":
                            try {
                                int valueInt=jsonObject.getInt(fieldName);
                                field.set(t, valueInt);
                            }catch (Exception e){
                                //Log.d("No fields",e.toString());
                                break;
                            }
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
