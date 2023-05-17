package com.hongyongfeng.wanandroid.module.main.activity;

import static com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment.mHandler;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;

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
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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
import androidx.appcompat.app.AppCompatDelegate;
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
import com.hongyongfeng.wanandroid.module.home.view.adapter.BannerAdapter;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;
import com.hongyongfeng.wanandroid.module.knowledge.view.fragment.KnowledgeFragment;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MainPresenter;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectFragment;
import com.hongyongfeng.wanandroid.module.query.view.QueryActivity;
import com.hongyongfeng.wanandroid.service.LongRunningTimeService;
import com.hongyongfeng.wanandroid.test.FragmentVPAdapter;
import com.hongyongfeng.wanandroid.test.VPFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity<MainPresenter, MainInterface.VP> implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    TextView tvQuery;
    float percent1 =1.0F;
    float percent0 =0.0F;
    TextView tvTitle;
    TextView navMenu;
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
    private ViewPager viewPager;
    private FragmentVPAdapter adapter;
    private List<Fragment> fragmentList;
    private TextView tvWelcome;
    private TextView tvName;

    private int stateDefault;
    private int stateStart=0;
    //以上为底部导航栏所需的成员变量
    ListView listView;
    private String[] listData={"我的收藏","浏览历史","关于","设置","退出登录"};
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
                                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                PendingIntent pendingIntent;
                                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    NotificationChannel mChannel = new NotificationChannel("channelId", "123", NotificationManager.IMPORTANCE_HIGH);
                                    manager.createNotificationChannel(mChannel);
                                }
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                    pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                                } else {
                                    pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                }
                                //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                Notification notification=new NotificationCompat.Builder(MainActivity.this,"channelId")
                                        .setContentTitle("This is content title")
                                        .setContentText("This is content text")
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.ic_notification)
                                        .setContentIntent(pendingIntent)
                                        .build();
                                manager.notify(1,notification);
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
                stateDefault=state;
                if (state==0){
                    stateStart=0;
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
        //NavigationView drawerLayout=findViewById(R.id.nav_view);
        LinearLayout drawerLayout=findViewById(R.id.left_layout);
        //drawerLayout.findViewById(R.id.menu).setBackgroundColor(getResources().getColor(R.color.layout));
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();//获取drawerlayout的布局
        para.width=width1/7*5;//修改宽度
        para.height=height1;//修改高度
        drawerLayout.setLayoutParams(para); //设置修改后的布局。
        initEvent();
        adapter=new FragmentVPAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<>(this,R.layout.item_list_menu,listData);
        listView.setAdapter(listViewAdapter);

        Intent intent=new Intent(this, LongRunningTimeService.class);
        startService(intent);

        //stopService(intent);
        //readSharedPreference();
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
        }
    }

    @Override
    public int getContentViewId() {
        //return R.layout.activity_home;
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
        tvTitle = findViewById(R.id.tv_title);//标题
        content = findViewById(R.id.content);//Fragment碎片布局
        //左侧隐藏的NavigationView布局
        navigationView = findViewById(R.id.nav_view);
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
//        startActivity(intent);
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
//                fragment=TestNavFragment.newInstance("这是首页文章","");
//                fragmentTransaction.replace(R.id.fragment_01,fragment).commit();
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_knowledge:
//                fragment=TestNavFragment.newInstance("这是知识体系","");
//                fragmentTransaction.replace(R.id.fragment_01,fragment).commit();
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_project:
//                fragment=TestNavFragment.newInstance("这是项目","");
//                fragmentTransaction.replace(R.id.fragment_01,fragment).commit();
                viewPager.setCurrentItem(2);
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
