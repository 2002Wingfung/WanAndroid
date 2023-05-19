package com.hongyongfeng.wanandroid.module.webview.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.main.activity.MainActivity;
import com.hongyongfeng.wanandroid.module.webview.presenter.WebViewPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class WebViewActivity extends BaseActivity<WebViewPresenter, com.hongyongfeng.wanandroid.module.webview.interfaces.WebView.VP> {
    private static final long CLICK_INTERVAL_TIME = 300;
    private static long lastClickTime = 0;
    TextView responseText;
    ConstraintLayout layout;
    ProgressBar progressBar;
    WebView webView;
    TextView tvTitle;
    TextView tvLikes;
    TextView tvBack;
    int count=0;
    String url;
    private int state=0;
    @Override
    public com.hongyongfeng.wanandroid.module.webview.interfaces.WebView.VP getContract() {
        return new com.hongyongfeng.wanandroid.module.webview.interfaces.WebView.VP() {
            @Override
            public void requestWebViewVP(String name, String pwd) {

            }

            @Override
            public void responseWebViewResult(boolean loginStatusResult) {

            }
        };
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            //获取intent中的参数
            url = intent.getStringExtra("url");
            state=intent.getIntExtra("state",0);
            //Bundle bundle = intent.getExtras();
            // 4.输出值和对象的name属性
            //state=bundle.getInt("state");
            //url=bundle.getString("url1");
            //System.out.println("web"+url);
//            ArrayList<String> list=intent.getStringArrayListExtra("list");
//            System.out.println("web"+list.get(1));

//            System.out.println(bundle.getString("url"));
//            System.out.println(bundle.getInt("state"));
        }
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //隐藏缩放按钮
        webSettings.setDisplayZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                //显示进度条
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    //加载完毕隐藏进度条
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title != null) {
                    if (title.contains("404")|| title.contains("System Error")) {
                        //加载错误显示的页面
                        //showErrorPage();
                        Toast.makeText(WebViewActivity.this, "出错了!", Toast.LENGTH_SHORT).show();
                    } else {
                        //title为webview标题内容
                        tvTitle.setText(title);
                    }
                }
                super.onReceivedTitle(view, title);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.getSettings()
                            .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    handler.proceed();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    //处理intent协议
                    if (url.startsWith("intent://")) {
                        Intent intent;
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            intent.addCategory("android.intent.category.BROWSABLE");
                            intent.setComponent(null);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                intent.setSelector(null);
                            }
                            List<ResolveInfo> resolves = getApplicationContext().getPackageManager().queryIntentActivities(intent,0);
                            if(resolves.size()>0){
                                startActivityIfNeeded(intent, -1);
                            }
                            return true;
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    // 处理自定义scheme协议
                    if (!url.startsWith("http")) {
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } catch (Exception e) {
                            // 防止没有安装的情况
                            e.printStackTrace();
                            Toast.makeText(WebViewActivity.this, "您所打开的第三方App未安装！", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(url);
                //禁止跳转外部网页
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);//开启硬件加速


    }

    @Override
    public void initListener() {
        tvLikes.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    public WebViewPresenter getPresenterInstance() {
        return new WebViewPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {
        webView=findViewById(R.id.web_view);
        layout=findViewById(R.id.include);
        tvTitle=layout.findViewById(R.id.tv_title);
        tvLikes=layout.findViewById(R.id.tv_likes);
        tvBack=layout.findViewById(R.id.tv_back);
        progressBar=findViewById(R.id.progressBar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上个页面
            return true;
        }else {
            if (state==1){
                Intent intent=new Intent(WebViewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //System.out.println(true);
            }
            url=null;
            state=0;
            //退出H5界面
            return super.onKeyDown(keyCode, event);
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title:
                //webView.scrollBy(0,-1000);
                //获取系统当前毫秒数，从开机到现在的毫秒数(手机睡眠时间不包括在内)
                long currentTimeMillis = SystemClock.uptimeMillis();
                //两次点击间隔时间小于300ms代表双击
                if (currentTimeMillis - lastClickTime < CLICK_INTERVAL_TIME) {
                    webView.scrollTo(0,0);

                }
                lastClickTime = currentTimeMillis;

                break;
            case R.id.tv_back:
                if (webView.canGoBack()) {
                    webView.goBack();//返回上个页面
                }else{
                    if (state==1){
                        Intent intent=new Intent(WebViewActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    url=null;
                    state=0;
                    WebViewActivity.this.finish();
                }
                break;
            case R.id.tv_likes:
                int number2=2;
                int number0=0;
                if (count%number2==number0){
                    tvLikes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes,null));
                    Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    //调用收藏的https接口
                }else {
                    tvLikes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes_none,null));
                    Toast.makeText(WebViewActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    //调用收藏的https接口
                }
                count++;
            default:
                String address="http://10.0.2.2/get_data.json";
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        showResponse(response);
                        parseJSONWithJSONObject(response);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                },null);
                break;
        }
    }

    private void parseJSONWithJSONObject(String toString) {
        try {
            System.out.println(toString);
            JSONArray jsonArray=new JSONArray(toString);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id =jsonObject.getString("id");
                String name =jsonObject.getString("name");
                String version =jsonObject.getString("version");
                Log.d("MainActivity","id is "+id);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","version is "+version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}