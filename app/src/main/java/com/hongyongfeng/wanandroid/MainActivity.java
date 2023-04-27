package com.hongyongfeng.wanandroid;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView textView_topTitle;
    private FrameLayout content;
    private NavigationView nav_view;
    private ImageButton btn_nva;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        NavigationView drawerLayout=findViewById(R.id.nav_view);
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
        para.width=width1/4*3;//修改宽度
        para.height=height1;//修改高度
        drawerLayout.setLayoutParams(para); //设置修改后的布局。

//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, android.R.string.yes, android.R.string.cancel) {
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//                float slideX = drawerView.getWidth() * slideOffset;
//                contentView.setTranslationX(slideX);
//            }
//        };

        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
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
        textView_topTitle = (TextView) findViewById(R.id.textView_topTitle);//标题
        content = (FrameLayout) findViewById(R.id.content);//Fragment碎片布局
        //左侧隐藏的NavigationView布局
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);//nva菜单的Item点击事件钮监听
        //左上角导航按钮
        btn_nva = (ImageButton) findViewById(R.id.btn_nva);
        btn_nva.setOnClickListener(this);//监听是否按下导航按钮
        //activity_main文件内最外层布局
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nva://左上角导航按钮
                drawer_layout.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
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
                drawer_layout.closeDrawer(GravityCompat.START);//关闭侧滑栏
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
