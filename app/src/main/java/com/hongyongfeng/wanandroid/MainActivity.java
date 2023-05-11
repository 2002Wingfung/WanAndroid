package com.hongyongfeng.wanandroid;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.hongyongfeng.wanandroid.util.StatusBarUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    TextView tvTitle;
    FrameLayout content;
    private DrawerLayout drawer;
    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deprecated);
        initView();
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        NavigationView drawerLayout=findViewById(R.id.nav_view);
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
        para.width=width1/4*3;//修改宽度
        para.height=height1;//修改高度
        drawerLayout.setLayoutParams(para); //设置修改后的布局。
        StatusBarUtils.setWindowStatusBarColor(MainActivity.this, R.color.transparent);
        //makeStatusBarTransparent(this);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void initView() {
        tvTitle = findViewById(R.id.textView_topTitle);//标题
        content = findViewById(R.id.content);//Fragment碎片布局
        //左侧隐藏的NavigationView布局
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);//nva菜单的Item点击事件钮监听
        //左上角导航按钮
        ImageButton btnNavigation = findViewById(R.id.btn_nva);
        btnNavigation.setOnClickListener(this);//监听是否按下导航按钮
        //activity_main文件内最外层布局
        drawer = findViewById(R.id.drawer_layout);
        drawer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nva://左上角导航按钮
                drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
                StatusBarUtils.setWindowStatusBarColor(MainActivity.this, R.color.transparent);
                break;
        }
    }

    /*
        侧滑栏导航item点击事件监听
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.fragment_05:
                Toast.makeText(MainActivity.this, "界面1", Toast.LENGTH_SHORT).show();
                //加载碎片
                //getSupportFragmentManager().beginTransaction().replace(R.id.content,new Fragment_05()).commit();
                drawer.closeDrawer(GravityCompat.START);//关闭侧滑栏
                break;
            case R.id.fragment_06:
                Toast.makeText(MainActivity.this, "界面2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_07:
                Toast.makeText(MainActivity.this, "界面3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_08:
                Toast.makeText(MainActivity.this, "界面4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_09:
                Toast.makeText(MainActivity.this, "界面5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_10:
                Toast.makeText(MainActivity.this, "界面6", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
