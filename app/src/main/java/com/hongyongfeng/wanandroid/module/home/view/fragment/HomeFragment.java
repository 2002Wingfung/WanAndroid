package com.hongyongfeng.wanandroid.module.home.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.interfaces.OnLoadImageListener;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.home.view.adapter.BannerAdapter;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeFragmentPresenter, HomeFragmentInterface.VP> {
    @Override
    public HomeFragmentInterface.VP getContract() {
        return new HomeFragmentInterface.VP() {
            @Override
            public void requestImageVP() {
                mPresenter.getContract().requestImageVP();
            }

            @Override
            public void responseImageResult(List<BannerBean> beanList,List<Bitmap> bitmapList) {
                beanLists=beanList;
                for (BannerBean banner:beanList) {
                    String url=banner.getUrl();
                    String imagePath=banner.getImagePath();
                    System.out.println(url);
                    System.out.println(imagePath);
                }
                //System.out.println(bitmapList);
                bitmapLists=bitmapList;
                dialog.dismiss();
            }
        };
    }

    View view1,view2,view3;
    private List<View> viewList;
    static ViewPager viewPager;
    List<BannerBean> beanLists;
    List<Bitmap> bitmapLists;

    ProgressDialog dialog;
    public static List<ArticleBean> articleList=new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private FragmentActivity fragmentActivity;
    @SuppressLint("StaticFieldLeak")
    static ArticleAdapter adapter=new ArticleAdapter(articleList);
    private NestedScrollView scrollView;
    ConstraintLayout layout;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentActivity=requireActivity();
        super.onViewCreated(view, savedInstanceState);
        Log.d("HomeFragment","onViewCreated"+ SystemClock.elapsedRealtime());
        //setRecyclerView();
        SetRecyclerView.setRecyclerView(fragmentActivity,recyclerView,adapter);
    }



    private void setRecyclerView() {
        //获取LinearLayoutManager实例，设置布局方式
        LinearLayoutManager layoutManager=new LinearLayoutManager(fragmentActivity,LinearLayoutManager.VERTICAL,false);
        //将LinearLayoutManager实例传入RecycleView的实例中，设置RecycleView的item布局
        recyclerView.setLayoutManager(layoutManager);
        //通过设置ItemDecoration 来装饰Item的效果,设置间隔线
        DividerItemDecoration mDivider = new
                DividerItemDecoration(fragmentActivity,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        //将adapter传入recyclerView对象中
        recyclerView.setAdapter(adapter);
        //添加默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //使recyclerView滚动到0索引的位置
        recyclerView.scrollToPosition(0);
    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void initView(View view) {
        layout= fragmentActivity.findViewById(R.id.fragment_home);
        scrollView=layout.findViewById(R.id.scroll_view_home);
        //根据id获取RecycleView的实例
        recyclerView= fragmentActivity.findViewById(R.id.rv_article);
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {   //scrollY是滑动的距离
                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    //滑动到底部
                    Toast.makeText(fragmentActivity, "滑动到了底部", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                int number2=2;
                int number0=0;
                if (count[0]%number2==number0){
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes,null));
                    Toast.makeText(fragmentActivity, "点赞成功", Toast.LENGTH_SHORT).show();
                }else {
                    likes.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_likes_gray,null));
                    Toast.makeText(fragmentActivity, "取消点赞", Toast.LENGTH_SHORT).show();
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                Intent intent=new Intent(fragmentActivity, WebViewActivity.class);
                intent.putExtra("url","https://www.baidu.com");
                startActivity(intent);
                Toast.makeText(fragmentActivity, "点击了view"+(position+1), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomeFragment","onCreate"+ SystemClock.elapsedRealtime());
        dialog=ProgressDialog.show(requireActivity(),"","正在加载",false,false);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter=getPresenterInstance();
        mPresenter.bindView(this);

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        viewPager = (ViewPager) view.findViewById(R.id.indicator_all);

        inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);

        viewList = new ArrayList<>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        getContract().requestImageVP();

        BannerAdapter pagerAdapter=new BannerAdapter(viewList,beanLists,new OnLoadImageListener(){

            @Override
            public void loadImage(Context context, BannerBean bannerBean, int position, View imageView) {
                //String imagePath = bannerBean.getImagePath();
                //((ImageView)imageView).setImageBitmap(getImageBitmap(imagePath));
            }
        });

        pagerAdapter.setOnPictureClickListener(new BannerAdapter.OnPictureClickListener() {
            @Override
            public void onPictureClick(int position) {
                String url=beanLists.get(position).getUrl();
                Intent intent=new Intent(fragmentActivity,WebViewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                //Toast.makeText(getActivity(), "clicked"+position, Toast.LENGTH_SHORT).show();
            }
        });

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state==0){
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(0,3000);
                }
            }
        });
        mHandler.sendEmptyMessageDelayed(0, 1000*3);
        return view;
    }


    @SuppressLint("HandlerLeak")
    public static Handler mHandler = new Handler(Looper.getMainLooper())
    {
        public void handleMessage(Message msg) {
            int count = 3;
            int index=viewPager.getCurrentItem();
            index=(index+1)%count;
            viewPager.setCurrentItem(index);
            mHandler.sendEmptyMessageDelayed(0, 1000*3);
        }
    };
    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

        Log.d("HomeFragment","onDestroy"+ SystemClock.elapsedRealtime());
        super.onDestroy();
    }

    protected void initData() {
        if (articleList.size()==0){
            for (int i =0;i<20;i++){
                articleList.add(new ArticleBean(i));
            }
        }
    }

    @Override
    protected HomeFragmentPresenter getPresenterInstance() {
        return new HomeFragmentPresenter();
    }

    @Override
    protected <ERROR> void responseError(ERROR error, Throwable throwable) {
    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
    }

}
