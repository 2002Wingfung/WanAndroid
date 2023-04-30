package com.hongyongfeng.wanandroid.module.home.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.module.home.interfaces.Home;
import com.hongyongfeng.wanandroid.module.home.presenter.HomePresenter;
import com.hongyongfeng.wanandroid.module.login.view.LoginActivity;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;
import com.hongyongfeng.wanandroid.test.TestNavFragment;
import com.hongyongfeng.wanandroid.util.StatusBarUtils;


public class HomeActivity extends BaseActivity<HomePresenter, Home.VP> implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    TextView tvQuery;
    float percent1 =1.0F;
    float percent0 =0.0F;
    TextView tvTitle;
    TextView navMenu;
    View header;
    FrameLayout content;
    private DrawerLayout drawer;

    //以下为底部导航栏所需的成员变量
    private LinearLayout home;
    private LinearLayout knowledge;
    private LinearLayout project;
    private ImageView imgHome;
    private ImageView imgKnowledge;
    private ImageView imgProject;
    private TextView tvHome;
    private TextView tvKnowledge;
    private TextView tvProject;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //以上为底部导航栏所需的成员变量

    @Override
    public Home.VP getContract() {
        return null;
    }
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
    public void initListener() {
        tvQuery.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);//nva菜单的Item点击事件钮监听
        navMenu.setOnClickListener(this);//监听是否按下导航按钮
        drawer.setOnClickListener(this);
        home.setOnClickListener(this);
        knowledge.setOnClickListener(this);
        project.setOnClickListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//                if (slideOffset>percent0){
//                    percent0=slideOffset;
//                    StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.transparent);
//
//                }
//                if (slideOffset< percent0){
//                    StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.blue);
//                    percent0 =slideOffset;
//
//                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
//                ConstraintLayout layout=findViewById(R.id.include);

                //makeStatusBarTransparent(HomeActivity.this);

//                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
//                StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.blue);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        NavigationView drawerLayout=findViewById(R.id.nav_view);
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
        para.width=width1/7*5;//修改宽度
        para.height=height1;//修改高度
        drawerLayout.setLayoutParams(para); //设置修改后的布局。
        initEvent();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
//                Window window = HomeActivity.this.getWindow();
//                View decorView = window.getDecorView();
//                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//                //导航栏颜色也可以正常设置
////                window.setNavigationBarColor(Color.TRANSPARENT);
//            } else {
//                Window window = HomeActivity.this.getWindow();
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
////                attributes.flags |= flagTranslucentNavigation;
//                window.setAttributes(attributes);
//            }
//        }
        //StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.transparent);

    }

    private void initEvent() {
        //添加Fragment
        //设置默认是首页
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        TestNavFragment fragment=TestNavFragment.newInstance("这是首页","");
        //这里可以传递两个参数
        fragmentTransaction.replace(R.id.fragment_01,fragment).commit();
        imgHome.setSelected(true);
        tvHome.setTextColor(getResources().getColor(R.color.blue));

    }

    private void initBottomNavigationView() {
        home =findViewById(R.id.ll_home);
        knowledge=findViewById(R.id.ll_knowledge);
        project =findViewById(R.id.ll_project);

        imgHome =findViewById(R.id.img_home);
        imgKnowledge=findViewById(R.id.img_knowledge);
        imgProject =findViewById(R.id.img_project);

        tvHome =findViewById(R.id.tv_home);
        tvKnowledge=findViewById(R.id.tv_knowledge);
        tvProject =findViewById(R.id.tv_project);
    }
    private void resetBottomState(){
        //重置底部导航栏的按钮颜色以及文字的颜色
        imgHome.setSelected(false);
        tvHome.setTextColor(getResources().getColor(R.color.gray));
        imgKnowledge.setSelected(false);
        tvKnowledge.setTextColor(getResources().getColor(R.color.gray));
        imgProject.setSelected(false);
        tvProject.setTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }


    @Override
    public HomePresenter getPresenterInstance() {
        return new HomePresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {
        tvQuery=findViewById(R.id.tv_query);
        tvTitle = findViewById(R.id.tv_title);//标题
        content = findViewById(R.id.content);//Fragment碎片布局
        //左侧隐藏的NavigationView布局
        navigationView = findViewById(R.id.nav_view);
        //左上角导航按钮
        navMenu = findViewById(R.id.tv_menu);
        //activity_main文件内最外层布局
        drawer = findViewById(R.id.drawer_layout);
        initBottomNavigationView();

    }

    public void headerOnClick(View v) {
        Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        boolean navBottom=(v.getId()==R.id.ll_home||v.getId()==R.id.ll_knowledge||v.getId()==R.id.ll_project);
        if (navBottom){
            resetBottomState();
        }
        switch (v.getId()){
            case R.id.tv_query:
                Intent intent=new Intent(HomeActivity.this, QueryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_menu://左上角导航按钮
                drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
//                StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.transparent);
                break;
            case R.id.ll_home:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                TestNavFragment fragmentHome=TestNavFragment.newInstance("这是首页文章","");
                fragmentTransaction.replace(R.id.fragment_01,fragmentHome).commit();
                imgHome.setSelected(true);
                tvHome.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.ll_knowledge:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                TestNavFragment fragmentKnowledge=TestNavFragment.newInstance("这是知识体系","");
                fragmentTransaction.replace(R.id.fragment_01,fragmentKnowledge).commit();
                imgKnowledge.setSelected(true);
                tvKnowledge.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.ll_project:
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                TestNavFragment fragmentProject=TestNavFragment.newInstance("这是项目","");
                fragmentTransaction.replace(R.id.fragment_01,fragmentProject).commit();
                imgProject.setSelected(true);
                tvProject.setTextColor(getResources().getColor(R.color.blue));
                break;
            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.fragment_05:
                Toast.makeText(HomeActivity.this, "界面1", Toast.LENGTH_SHORT).show();
                //加载碎片
                //getSupportFragmentManager().beginTransaction().replace(R.id.content,new Fragment_05()).commit();
                drawer.closeDrawer(GravityCompat.START);//关闭侧滑栏
                break;
            case R.id.fragment_06:
                Toast.makeText(HomeActivity.this, "界面2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_07:
                Toast.makeText(HomeActivity.this, "界面3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_08:
                Toast.makeText(HomeActivity.this, "界面4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_09:
                Toast.makeText(HomeActivity.this, "界面5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_10:
                Toast.makeText(HomeActivity.this, "界面6", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
