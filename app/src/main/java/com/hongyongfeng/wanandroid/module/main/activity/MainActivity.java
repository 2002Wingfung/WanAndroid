package com.hongyongfeng.wanandroid.module.main.activity;

import static com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel.ARTICLE_URL;
import static com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment.mHandler;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.home.view.adapter.BannerAdapter;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeFragment;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MainPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectFragment;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.service.LongRunningTimeService;
import com.hongyongfeng.wanandroid.test.FragmentAdapter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import com.hongyongfeng.wanandroid.util.ThreadPools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 主活动
 * @author Wingfung Hung
 */
public class MainActivity extends BaseActivity<MainPresenter, MainInterface.VP> {
    public static ThreadPools threadPools=new ThreadPools();
    private TextView tvQuery;
    private TextView tvTitle;
    private TextView navMenu;
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
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private List<Fragment> fragmentList;
    private TextView tvWelcome;
    private TextView tvName;
    //以上为底部导航栏所需的成员变量
    private ListView listView;
    private final String[] listData={"我的收藏","浏览历史","关于","设置","退出登录"};
    private int count=0;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //AppCompatDelegate.MODE_NIGHT_NO
    }

    @Override
    public MainInterface.VP getContract() {
        return new MainInterface.VP() {
            @Override
            public void requestVP() {
                mPresenter.getContract().requestVP();
            }

            @Override
            public void responseResult(String name) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvName.setText(name);
                        tvWelcome.setText("欢迎");
                    }
                });
            }
        };
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
        //navigationView.setNavigationItemSelectedListener(this);//nva菜单的Item点击事件钮监听
        navMenu.setOnClickListener(this);//监听是否按下导航按钮
        drawer.setOnClickListener(this);
        home.setOnClickListener(this);
        knowledge.setOnClickListener(this);
        project.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,
//                                "你点击了:"+listData[position],
//                                Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,MoreActivity.class);
                if (position!=4){
                    switch (position){
                        case 0:
                            intent.putExtra("title","我的收藏");
                            intent.putExtra("index",0);
                            break;
                        case 1:
                            intent.putExtra("title","浏览历史");
                            intent.putExtra("index",1);
                            break;
                        case 2:
                            intent.putExtra("title","关于");
                            break;
                        case 3:
                            intent.putExtra("title","设置");
                            break;
                        default:
                            break;
                    }
                    startActivity(intent);
                }else {
                    if (tvName.getText().toString().equals("玩安卓")){
                        Toast.makeText(MainActivity.this, "还没登录喔", Toast.LENGTH_SHORT).show();
                    }else {
                        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("确认退出登录?");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = MainActivity.this.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                tvName.setText("玩安卓");
                                tvWelcome.setText("欢迎");
                                Toast.makeText(MainActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                    }
                    
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                if (position==0&&positionOffsetPixels==0&&stateDefault==1&&stateStart==0){
//                    drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
//                    stateStart=1;
//                }
                if (position==0&&positionOffsetPixels==0){
                    count++;
                    //System.out.println(count);
                }else {
                    count=0;
                }
                if (count>=25){
                    drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
                    count=0;
                }
//                if (position==0&&positionOffsetPixels==0&&stateStart==0){
//                    //drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
//                    left=true;
//                    stateStart=1;
//                    System.out.println("dui");
//                }else{
//                    left=false;
//                }
            }

            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state==0){
                    count=0;
                }
                if(BannerAdapter.down==1&&state==0){
                    mHandler.sendEmptyMessageDelayed(0,3000);
                }
            }
        });

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

    private void onViewPagerSelected(int position) {
        loadFragment();
        resetBottomState();
        if (position==0){
            setBottomItemSelected(R.id.ll_home);
        } else if (position==1) {
            setBottomItemSelected(R.id.ll_knowledge);

        }else {
            setBottomItemSelected(R.id.ll_project);
        }
    }

    @Override
    public void initData() {
        fragmentList=new ArrayList<>();
//        VPFragment fragmentHome=VPFragment.newInstance("首页文章","");
//
//        VPFragment fragmentKnowledge=VPFragment.newInstance("知识体系","");
//        VPFragment fragmentProject=VPFragment.newInstance("项目","");
        fragmentList.add(new HomeFragment());
//        fragmentList.add(fragmentHome);
        fragmentList.add(new KnowledgeFragment());
//        fragmentList.add(fragmentProject);
        fragmentList.add(new ProjectFragment());

    }

    @Override
    public void destroy() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认进入选中首页
        onViewPagerSelected(0);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        LinearLayout drawerLayout=findViewById(R.id.left_layout);
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
        para.width=width1/7*5;//修改宽度
        para.height=height1;//修改高度
        drawerLayout.setLayoutParams(para); //设置修改后的布局。
        initEvent();
        adapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<>(this,R.layout.item_list_menu,listData);
        listView.setAdapter(listViewAdapter);
        Intent intent=new Intent(this, LongRunningTimeService.class);
        intent.setAction("com.hongyongfeng.wanandroid.service.LongRunningTimeService");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);
        requestStoragePermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getContract().requestVP();
    }

    private void readSharedPreference() {
        try {
            SharedPreferences preferences=getSharedPreferences(COOKIE_PREF,MODE_PRIVATE);
            String cookies=preferences.getString("login","");
            System.out.println(cookies);
            int first=cookies.indexOf(";")+1;
            int last=cookies.lastIndexOf(";");
            cookies=cookies.substring(first,last);
            first=cookies.indexOf("=")+1;
            tvWelcome.setText("欢迎");
            tvName.setText(cookies.substring(first));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initEvent() {
        //添加Fragment
        //设置默认是首页
        loadFragment();
        //fragment=TestNavFragment.newInstance("这是首页","");
        //这里可以传递两个参数
        //fragmentTransaction.replace(R.id.fragment_01,fragment).commit();
        setBottomItemSelected(R.id.ll_home);
        tvTitle.setText("首页文章");
    }
    private void loadFragment(){
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
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
    @SuppressLint("NonConstantResourceId")
    private void setBottomItemSelected(int id){
        switch (id){
            case R.id.ll_home:
                imgHome.setSelected(true);
                tvHome.setTextColor(getResources().getColor(R.color.blue));
                tvTitle.setText("首页文章");
                break;
            case R.id.ll_knowledge:
                imgKnowledge.setSelected(true);
                tvKnowledge.setTextColor(getResources().getColor(R.color.blue));
                tvTitle.setText("知识体系");
                break;
            case R.id.ll_project:
                imgProject.setSelected(true);
                tvProject.setTextColor(getResources().getColor(R.color.blue));
                tvTitle.setText("项目");
                break;
            default:
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main_latest;
    }


    @Override
    public MainPresenter getPresenterInstance() {
        return new MainPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }

    @Override
    public void initView() {
        tvQuery=findViewById(R.id.tv_query);
        //标题
        tvTitle = findViewById(R.id.tv_title);
        //左上角导航按钮
        navMenu = findViewById(R.id.tv_menu);
        //activity_main文件内最外层布局
        drawer = findViewById(R.id.drawer_layout);
        viewPager=findViewById(R.id.vp_main);
        initBottomNavigationView();
        listView=findViewById(R.id.left_drawer_listView);
        tvWelcome=findViewById(R.id.tv_login1);
        tvName=findViewById(R.id.user_name);
    }

    public void headerOnClick(View v) {
        if (tvName.getText().toString().equals("玩安卓")){
            Intent intent=new Intent(MainActivity.this, SignInUpActivity.class);
            startActivityForResult(intent,1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断请求码
        if (requestCode==1){
            //结果码
            if (resultCode==1)
            {
                //取数据
                String name = Objects.requireNonNull(data).getStringExtra("name");

                //给控件赋值
                tvName.setText(name);
                tvWelcome.setText("欢迎");
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id=v.getId();
        boolean navBottom=(id==R.id.ll_home||id==R.id.ll_knowledge||id==R.id.ll_project);
        if (navBottom){
            loadFragment();
            resetBottomState();
            setBottomItemSelected(id);
        }
        switch (id){
            case R.id.tv_query:
                Intent intent=new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_menu://左上角导航按钮
                drawer.openDrawer(GravityCompat.START);//设置左边菜单栏显示出来
//                StatusBarUtils.setWindowStatusBarColor(HomeActivity.this, R.color.transparent);
                break;
            case R.id.ll_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_knowledge:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_project:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
    private void requestStoragePermission() {
        List<String> needRequestList = checkPermission(this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS,Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,Manifest.permission_group.NOTIFICATIONS});
        if (needRequestList.isEmpty()) {
            System.out.println(true);
            //已授权
        } else {
            //申请权限
            System.out.println("first,false");

            requestPermission(needRequestList);
        }
    }
    private List<String> checkPermission(Context context, String[] checkList) {
        List<String> list = new ArrayList<>();
        for (String s : checkList) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat
                    .checkSelfPermission(context, s)) {
                list.add(s);
            }
        }
        return list;
    }
    private void requestPermission(List<String> needRequestList) {
        ActivityCompat
                .requestPermissions(MainActivity.this, needRequestList.toArray(new String[0]),
                        111);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println(true);
                    //已授权
                } else {
                    //未授权
                    System.out.println(false);
                }
            }
        }
    }
}
