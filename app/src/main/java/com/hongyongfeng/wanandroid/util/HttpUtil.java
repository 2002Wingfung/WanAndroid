package com.hongyongfeng.wanandroid.util;

import static com.hongyongfeng.wanandroid.util.Constant.FOUR;
import static com.hongyongfeng.wanandroid.util.Constant.THREE_THOUSAND;
import static com.hongyongfeng.wanandroid.util.Constant.TWO_HUNDRED;
import static com.hongyongfeng.wanandroid.util.ThreadPools.es;

import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.exception.HttpException;
import com.hongyongfeng.wanandroid.module.signinorup.login.interfaces.HttpCookiesListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
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

/**
 * HTTP工具类
 * @author Wingfung Hung
 */
public class HttpUtil {
    /**
     * 封装了一个解析json数据的方法，
     * @param string 请求网络后返回的json字符串
     * @return 返回的是一个List<Map>集合
     */
    public static List<Map<String,Object>> parseJsonWithJsonObject(String string){
        List<Map<String,Object>> stringListMap=new ArrayList<>();
        //截取json字符串,因为jsonArray的格式是[]，只能解析这种格式的数据,而返回的数据的格式是{}
        try {
            int indexStart=string.indexOf('[');
            int indexEnd=string.lastIndexOf(']');
            JSONArray jsonArray=new JSONArray(string.substring(indexStart,indexEnd+1));
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            Iterator<String> keys = jsonObject.keys();
            List<String> jsonFieldList=new ArrayList<>();
            //读取其中一个JsonObject的字段
            while (keys.hasNext()){
                String jsonField=keys.next();
                jsonFieldList.add(jsonField);
            }
            for (int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                Map<String,Object> stringMap = new HashMap<>(1);
                //将jsonObject中所有的字段的值都存进map中
                for (String field:jsonFieldList){
                    stringMap.put(field,jsonObject.get(field));
                }
                stringListMap.add(stringMap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringListMap;
    }

    /**
     * 封装了一个解析json数据的方法，
     * @param toString 请求网络后返回的json字符串
     * @param c 反射所用到的实体类的类型
     * @return 返回一个List泛型的集合
     */
    public static <T> List<T> parseJsonWithObject(String toString, Class<T> c) {
        List<T> list=new ArrayList<>();
        try {
            //截取字符串
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
                            try {
                                int valueInt=jsonObject.getInt(fieldName);
                                field.set(t, valueInt);
                            }catch (Exception e){
                                break;
                            }
                            break;
                        case "class java.lang.String":
                            try {
                                String value = jsonObject.getString(fieldName);
                                //这里可以开启另一种容量很大的线程池，用于请求图片
                                if (!"".equals(value)) {
                                    field.set(t, value);
                                }
                            }catch (Exception e){
                                break;
                            }
                            break;
                        case "boolean":
                            try {
                                field.set(t, jsonObject.getBoolean(fieldName));
                            }catch (Exception e){
                                break;
                            }
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

    /**
     * 发送http请求
     * @param address 请求的地址
     * @param listener 回调监听
     * @param parameter cookies参数
     */
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener,String parameter){
        es.execute(() -> {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(address);
                connection=(HttpURLConnection) url.openConnection();
                //GET方法
                connection.setRequestMethod("GET");
                //超时时间3000毫秒
                connection.setConnectTimeout(THREE_THOUSAND);
                connection.setReadTimeout(THREE_THOUSAND);
                connection.setDoInput(true);
                if (parameter!=null){
                    connection.setRequestProperty("Cookie", parameter);
                }
                connection.connect();
                //获取输入流，得到返回的数据
                InputStream in=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(in));
                //使用字符串拼接
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
        });
    }

    /**
     * 用post方法请求登录接口
     * @param cookiesListener 完成请求时保存cookies接口
     * @param listener 回调接口
     * @param strings 字符串参数数组
     */
    public static void postLoginRequest(final HttpCookiesListener cookiesListener, final HttpCallbackListener listener, String...strings){
        es.execute(() -> {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection) url.openConnection();
                // 设置请求方式,请求超时信息
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(THREE_THOUSAND);
                connection.setReadTimeout(THREE_THOUSAND);
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
                if (strings.length==FOUR){
                    data = "username=" + URLEncoder.encode(strings[1], "UTF-8")
                            + "&password=" + URLEncoder.encode(strings[2], "UTF-8")
                            + "&repassword=" + URLEncoder.encode(strings[3], "UTF-8");
                }
                // 获取输出流
                OutputStream out = connection.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();
                if (connection.getResponseCode() == TWO_HUNDRED) {
                    //接收Cookie
                    CookieManager cookieManager = new CookieManager();
                    //将地址中返回的数据放入Cookie管理器中
                    cookieManager.put(new URI(strings[0]),connection.getHeaderFields());
                    //将数据存入到CookieStore的一个对象中
                    CookieStore cookieStore = cookieManager.getCookieStore();
                    //cookieStore.getCookies()返回的是一个HttpCookie的集合，利用接口回调将其抛出到model层
                    cookiesListener.onFinish(cookieStore.getCookies());
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
        });
    }

    /**
     * post收藏请求
     * @param address 请求地址
     * @param cookies cookies字段
     * @param listener 回调接口
     */
    public static void postCollectRequest(final String address,String cookies, final HttpCallbackListener listener){
        es.execute(() -> {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(address);
                connection=(HttpURLConnection) url.openConnection();
                // 设置请求方式,请求超时信息
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(THREE_THOUSAND);
                connection.setReadTimeout(THREE_THOUSAND);
                // 设置是否从httpUrlConnection读入，默认情况下是true;
                connection.setDoInput(true);
                // post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
                connection.setDoOutput(true);
                // Post方式不能缓存,需手动设置为false
                connection.setUseCaches(false);
                // 我们请求的数据:
                if (cookies != null){
                    connection.setRequestProperty("Cookie", cookies);
                }
                connection.connect();
                //状态码为200时回调finish方法
                if (connection.getResponseCode() == TWO_HUNDRED) {
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
        });
    }

    /**
     * post搜索请求
     * @param address 搜索地址
     * @param key 搜索关键字
     * @param listener 回调接口
     */
    public static void postQueryRequest(final String address,String key, final HttpCallbackListener listener,String cookies){
        es.execute(() -> {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(address);
                connection=(HttpURLConnection) url.openConnection();
                // 设置请求方式,请求超时信息
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(THREE_THOUSAND);
                connection.setReadTimeout(THREE_THOUSAND);
                // 设置运行输入,输出:
                // 设置是否从httpUrlConnection读入，默认情况下是true;
                connection.setDoInput(true);
                // post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
                connection.setDoOutput(true);
                // Post方式不能缓存,需手动设置为false
                connection.setUseCaches(false);
                if (cookies != null){
                    connection.setRequestProperty("Cookie", cookies);
                }
                // 我们请求的数据：参数k为搜索的关键字
                String data = "k=" + URLEncoder.encode(key, "UTF-8");
                // 获取输出流
                OutputStream out = connection.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();
                //搜索成功返回状态码为200
                if (connection.getResponseCode() == TWO_HUNDRED) {
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
        });
    }

    /**
     * 检查http状态码
     * @param code http状态码
     * @throws HttpException Http异常
     */
    public static void checkHttpCode(int code) throws HttpException {
        if (code!=TWO_HUNDRED){
            throw new HttpException("请求网络错误:"+code);
        }
    }
}
