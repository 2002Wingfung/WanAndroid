package com.hongyongfeng.wanandroid.module.main.activity;

import static com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment.mHandler;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;
import static com.hongyongfeng.wanandroid.util.Constant.FIVE;
import static com.hongyongfeng.wanandroid.util.Constant.FOUR;
import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.SEVEN;
import static com.hongyongfeng.wanandroid.util.Constant.THREE;
import static com.hongyongfeng.wanandroid.util.Constant.THREE_THOUSAND;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
import com.hongyongfeng.wanandroid.test.FragmentAdapter;
import com.hongyongfeng.wanandroid.util.ThreadPools;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 主活动
 * @author Wingfung Hung
 */
public class MainActivity extends BaseActivity<MainPresenter, MainInterface.Vp> {
    //public static ThreadPools threadPools=new ThreadPools();
    private static final String WAN="玩安卓";
    private TextView tvQuery;
    private TextView tvTitle;
    private TextView navMenu;
    private DrawerLayout drawer;
    private LinearLayout home;
    private LinearLayout knowledge;
    private LinearLayout project;
    private ImageView imgHome;
    private ImageView imgKnowledge;
    private ImageView imgProject;
    private TextView tvHome;
    private TextView tvKnowledge;
    private TextView tvProject;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private TextView tvWelcome;
    private TextView tvName;
    private ListView listView;
    private final String[] listData={"我的收藏","浏览历史","关于","设置","退出登录"};
    private int count=ZERO;

    @Override
    public MainInterface.Vp getContract() {
        return new MainInterface.Vp() {
            @Override
            public void requestVp() {
                mPresenter.getContract().requestVp();
            }

            @Override
            public void responseResult(String name) {
                runOnUiThread(() -> {
                    tvName.setText(name);
                    tvWelcome.setText("欢迎");
                });
            }
        };
    }

    @Override
    public void initListener() {
        tvQuery.setOnClickListener(this);
        //监听是否按下导航按钮
        navMenu.setOnClickListener(this);
        drawer.setOnClickListener(this);
        home.setOnClickListener(this);
        knowledge.setOnClickListener(this);
        project.setOnClickListener(this);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(MainActivity.this,MoreActivity.class);
            if (position!=FOUR){
                switch (position){
                    case ZERO:
                        intent.putExtra("title","我的收藏");
                        intent.putExtra("index",ZERO);
                        break;
                    case ONE:
                        intent.putExtra("title","浏览历史");
                        intent.putExtra("index",ONE);
                        break;
                    case TWO:
                        intent.putExtra("title","关于");
                        break;
                    case THREE:
                        intent.putExtra("title","设置");
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }else {
                if (WAN.equals(tvName.getText().toString())){
                    Toast.makeText(MainActivity.this, "还没登录喔", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("确认退出登录?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", (dialog1, which) -> {
                        SharedPreferences preferences = MainActivity.this.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        tvName.setText(WAN);
                        tvWelcome.setText("欢迎");
                        Toast.makeText(MainActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                    });
                    dialog.setNegativeButton("取消", (dialog12, which) -> {
                    });
                    dialog.show();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==ZERO&&positionOffsetPixels==ZERO){
                    count++;
                }else {
                    count=ZERO;
                }
                if (count>=25){
                    drawer.openDrawer(GravityCompat.START);
                    //设置左边菜单栏显示出来
                    count=ZERO;
                }
            }

            @Override
            public void onPageSelected(int position) {
                onViewPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state==ZERO){
                    count=ZERO;
                }
                if(BannerAdapter.down==ONE&&state==ZERO){
                    mHandler.sendEmptyMessageDelayed(ZERO,THREE_THOUSAND);
                }
            }
        });

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
    private void onViewPagerSelected(int position) {
        resetBottomState();
        if (position==ZERO){
            setBottomItemSelected(R.id.ll_home);
        } else if (position==ONE) {
            setBottomItemSelected(R.id.ll_knowledge);
        }else {
            setBottomItemSelected(R.id.ll_project);
        }
    }
    @Override
    public void initData() {
        fragmentList=new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new KnowledgeFragment());
        fragmentList.add(new ProjectFragment());
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认进入选中首页
        onViewPagerSelected(ZERO);
        //获取屏幕宽高
        WindowManager wm = this.getWindowManager();
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        LinearLayout drawerLayout=findViewById(R.id.left_layout);
        //获取DrawerLayout的布局
        ViewGroup.LayoutParams para= drawerLayout.getLayoutParams();
        //修改宽度
        para.width=width1/SEVEN*FIVE;
        //修改高度
        para.height=height1;
        //设置修改后的布局。
        drawerLayout.setLayoutParams(para);
        initEvent();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(TWO);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<>(this,R.layout.item_list_menu,listData);
        listView.setAdapter(listViewAdapter);
        Intent intent=new Intent(this, LongRunningTimeService.class);
        intent.setAction("com.hongyongfeng.wanandroid.service.LongRunningTimeService");
        startService(intent);
        requestNotificationPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getContract().requestVp();
    }

    private void initEvent() {
        //添加Fragment
        //设置默认是首页
        setBottomItemSelected(R.id.ll_home);
        tvTitle.setText("首页文章");
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
        int color=ResourcesCompat.getColor(getResources(),R.color.gray,null);
        tvHome.setTextColor(color);
        imgKnowledge.setSelected(false);
        tvKnowledge.setTextColor(color);
        imgProject.setSelected(false);
        tvProject.setTextColor(color);
    }
    @SuppressLint("NonConstantResourceId")
    private void setBottomItemSelected(int id){
        int color=ResourcesCompat.getColor(getResources(),R.color.blue,null);
        switch (id){
            case R.id.ll_home:
                imgHome.setSelected(true);
                tvHome.setTextColor(color);
                tvTitle.setText("首页文章");
                break;
            case R.id.ll_knowledge:
                imgKnowledge.setSelected(true);
                tvKnowledge.setTextColor(color);
                tvTitle.setText("知识体系");
                break;
            case R.id.ll_project:
                imgProject.setSelected(true);
                tvProject.setTextColor(color);
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
        if (WAN.equals(tvName.getText().toString())){
            Intent intent=new Intent(MainActivity.this, SignInUpActivity.class);
            startActivityForResult(intent,ONE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断请求码
        if (requestCode==ONE){
            //结果码
            if (resultCode==ONE)
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
            resetBottomState();
            setBottomItemSelected(id);
        }
        switch (id){
            case R.id.tv_query:
                Intent intent=new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_menu:
                //左上角导航按钮
                drawer.openDrawer(GravityCompat.START);
                //设置左边菜单栏显示出来
                break;
            case R.id.ll_home:
                viewPager.setCurrentItem(ZERO);
                break;
            case R.id.ll_knowledge:
                viewPager.setCurrentItem(ONE);
                break;
            case R.id.ll_project:
                viewPager.setCurrentItem(TWO);
                break;
            default:
                break;
        }
    }
    private void requestNotificationPermission() {
        List<String> needRequestList = checkPermission(this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS,Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,Manifest.permission_group.NOTIFICATIONS});
        if (needRequestList.isEmpty()) {
            System.out.println(true);
            //已授权
        } else {
            //申请权限
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
        ActivityCompat.requestPermissions(MainActivity.this, needRequestList.toArray(new String[ZERO]), ONE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ONE) {
            for (int i = ZERO; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //已授权
                } else {
                    //未授权
                }
            }
        }
    }
}
