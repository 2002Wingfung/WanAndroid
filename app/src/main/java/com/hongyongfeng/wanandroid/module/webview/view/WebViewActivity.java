package com.hongyongfeng.wanandroid.module.webview.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
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
import java.net.URL;

/**
 * @author Wingfung Hung
 */
public class WebViewActivity extends BaseActivity<WebViewPresenter, com.hongyongfeng.wanandroid.module.webview.interfaces.WebView.VP> {

    TextView responseText;
    ConstraintLayout layout;
    ProgressBar progressBar;
    WebView webView;
    TextView tvTitle;
    TextView tvLikes;
    TextView tvBack;
    int count=0;
    String url;
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

        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://www.baidu.com");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
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
        });

    }

    @Override
    public void initListener() {
        tvLikes.setOnClickListener(this);
        tvBack.setOnClickListener(this);

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
        }
        return super.onKeyDown(keyCode, event);//退出H5界面
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                if (webView.canGoBack()) {
                    webView.goBack();//返回上个页面
//                    webView.setWebChromeClient(new WebChromeClient() {
//                        @Override
//                        public void onProgressChanged(WebView view, int newProgress) {
//                            //显示进度条
//                            progressBar.setProgress(newProgress);
//                            if (newProgress == 100) {
//                                //加载完毕隐藏进度条
//                                progressBar.setVisibility(View.GONE);
//                            }
//                            super.onProgressChanged(view, newProgress);
//                        }
//                    });
                }else{
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
            //sendRequestWithHttpURLConnection();
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
                });
                break;
        }
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    //URL url=new URL("https://www.baidu.com");
                    URL url=new URL("http://10.0.2.2/get_data.json");
                    connection =(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                    parseJSONWithJSONObject(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

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

    private  void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}