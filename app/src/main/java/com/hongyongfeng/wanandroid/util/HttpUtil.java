package com.hongyongfeng.wanandroid.util;

import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import android.os.Build;
import android.util.Log;

import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.exception.HttpException;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

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
                            //这里可以开启另一种容量很大的线程池，用于请求图片
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
                    connection.setConnectTimeout(1000);
                    connection.setReadTimeout(1000);
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
    public static void postLoginRequest(final HttpCookiesListener cookiesListener, final HttpCallbackListener listener, String...strings){
        es.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL(strings[0]);
                    connection=(HttpURLConnection) url.openConnection();


                    // 设置请求方式,请求超时信息
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    // 设置运行输入,输出:
                    // 设置是否从httpUrlConnection读入，默认情况下是true;

                    connection.setDoInput(true);
                    // post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;

                    connection.setDoOutput(true);
                    // Post方式不能缓存,需手动设置为false
                    connection.setUseCaches(false);
                    // 我们请求的数据:
                    String data = "username=" + URLEncoder.encode(strings[1], "UTF-8")
                            + "&password=" + URLEncoder.encode(strings[2], "UTF-8");
                    //System.out.println("str"+strings.length);
                    if (strings.length==4){
                        data = "username=" + URLEncoder.encode(strings[1], "UTF-8")
                                + "&password=" + URLEncoder.encode(strings[2], "UTF-8")
                                + "&repassword=" + URLEncoder.encode(strings[3], "UTF-8");
                    }
                    // 获取输出流
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() == 200) {
//                        CookieManager manager = new CookieManager();
//                        CookieHandler.setDefault(manager);
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//                            manager.getCookieStore().getCookies().forEach(httpCookie -> {
//                                System.out.println("class      : "+httpCookie.getClass());
//                                System.out.println("comment    : "+httpCookie.getComment());
//                                System.out.println("commentURL : "+httpCookie.getCommentURL());
//                                System.out.println("discard    : "+httpCookie.getDiscard());
//                                System.out.println("domain     : "+httpCookie.getDomain());
//                                System.out.println("maxAge     : "+httpCookie.getMaxAge());
//                                System.out.println("name       : "+httpCookie.getName());
//                                System.out.println("path       : "+httpCookie.getPath());
//                                System.out.println("portlist   : "+httpCookie.getPortlist());
//                                System.out.println("secure     : "+httpCookie.getSecure());
//                                System.out.println("value      : "+httpCookie.getValue());
//                                System.out.println("version    : "+httpCookie.getVersion());
//                                System.out.println("httpCookie : "+httpCookie);
//                            });
//                        }
                        //接收Cookie
                        CookieManager cookieManager = new CookieManager();
                        //将地址中返回的数据放入Cookie管理器中
                        cookieManager.put(new URI(strings[0]),connection.getHeaderFields());
                        //将数据存入到CookieStore的一个对象中
                        CookieStore cookieStore = cookieManager.getCookieStore();
                        //cookieStore.getCookies()返回的是一个HttpCookie的集合，利用接口回调将其抛出到model层
                        cookiesListener.onFinish(cookieStore.getCookies());
//                        System.out.println(connection.getHeaderField("Set-Cookie"));
//                        System.out.println(connection.getHeaderFields().toString());
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
                    }
                    else {
                        checkHttpCode(connection.getResponseCode());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
    public static void postCollectRequest(final String address,String cookies, final HttpCallbackListener listener){
        es.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    // 设置请求方式,请求超时信息
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    // 设置运行输入,输出:
                    // 设置是否从httpUrlConnection读入，默认情况下是true;

                    connection.setDoInput(true);
                    // post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;

                    connection.setDoOutput(true);
                    // Post方式不能缓存,需手动设置为false
                    connection.setUseCaches(false);
                    // 我们请求的数据:
                    if (cookies != null){
                        connection.setRequestProperty("Cookie", cookies);
                        System.out.println(cookies);
                    }
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
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
                    }
                    else {
                        checkHttpCode(connection.getResponseCode());
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
    public static void postQueryRequest(final String address,String key, final HttpCallbackListener listener){
        es.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    // 设置请求方式,请求超时信息
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    // 设置运行输入,输出:
                    // 设置是否从httpUrlConnection读入，默认情况下是true;

                    connection.setDoInput(true);
                    // post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;

                    connection.setDoOutput(true);
                    // Post方式不能缓存,需手动设置为false
                    connection.setUseCaches(false);
                    // 我们请求的数据:
                    String data = "k=" + URLEncoder.encode(key, "UTF-8");
                    // 获取输出流
                    OutputStream out = connection.getOutputStream();
                    out.write(data.getBytes());
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() == 200) {
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
                    }
                    else {
                        checkHttpCode(connection.getResponseCode());
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
    public static void checkHttpCode(int code) throws HttpException {
//        for (String name : names) {
//            if(name.equals(uname)){//如果名字在这里面 就抛出登陆异常
//                throw new RegisterException("亲"+name+"已经被注册了！");
//            }
//        }
        if (code!=200){
            throw new HttpException("请求网络错误:"+code);
        }
        //return true;
    }

}
