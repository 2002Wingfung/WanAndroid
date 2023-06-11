package com.hongyongfeng.wanandroid.module.home.view.fragment;

import static com.hongyongfeng.wanandroid.util.Constant.ONE;
import static com.hongyongfeng.wanandroid.util.Constant.THREE_THOUSAND;
import static com.hongyongfeng.wanandroid.util.Constant.TWO;
import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import static com.hongyongfeng.wanandroid.util.SaveArticle.CACHE_BITMAP;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseFragment;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.home.view.adapter.ArticleAdapter;
import com.hongyongfeng.wanandroid.module.home.view.adapter.BannerAdapter;
import com.hongyongfeng.wanandroid.module.query.view.fragment.LoadingFragment;
import com.hongyongfeng.wanandroid.module.signinorup.SignInUpActivity;
import com.hongyongfeng.wanandroid.module.webview.view.WebViewActivity;
import com.hongyongfeng.wanandroid.util.SaveArticle;
import com.hongyongfeng.wanandroid.util.SetRecyclerView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class HomeFragment extends BaseFragment<HomeFragmentPresenter, HomeFragmentInterface.ViewPresenter> {
    public static byte[] getBytes(Bitmap bitmap){
        //实例化字节数组输出流
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //压缩位图
        bitmap.compress(Bitmap.CompressFormat.PNG, ZERO, stream);
        return stream.toByteArray();
        //创建分配字节数组
    }
    @Override
    public HomeFragmentInterface.ViewPresenter getContract() {
        return new HomeFragmentInterface.ViewPresenter() {
            @Override
            public void collectVp(int id, CollectListener listener) {
                //请求收藏
                mPresenter.getContract().collectVp(id,listener);
            }
            @Override
            public void unCollectVp(int id, CollectListener listener) {
                //请求取消收藏
                mPresenter.getContract().unCollectVp(id,listener);
            }
            @Override
            public void collectResponse(int code) {
                //返回收藏是否成功的数据
                fragmentActivity.runOnUiThread(() -> {
                    if (code==0){
                        Toast.makeText(fragmentActivity, "点赞成功", Toast.LENGTH_SHORT).show();
                    }else if (code==1){
                        Toast.makeText(fragmentActivity, "还没登录", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(fragmentActivity, SignInUpActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(fragmentActivity, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void unCollectResponse(int code) {
                //返回取消收藏是否成功的数据
                fragmentActivity.runOnUiThread(() -> {
                    if (code==0){
                        Toast.makeText(fragmentActivity, "取消点赞", Toast.LENGTH_SHORT).show();
                    }else if (code==1){
                        Toast.makeText(fragmentActivity, "还没登录", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(fragmentActivity, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void requestImageVp() {
                //请求banner的图片
                mPresenter.getContract().requestImageVp();
            }

            @Override
            public void responseImageResult(List<BannerBean> beanList, List<Bitmap> bitmapList) {
                beanLists = beanList;
                bitmapLists = bitmapList;
                //file.delete();
                fragmentActivity.runOnUiThread(() -> {
                    if(!file.exists()){
                        //判断图片文件是否存在
                        for (int i = 0; i < viewList.size(); i++) {
                            View view = viewList.get(i);
                            ImageView imgBanner = view.findViewById(R.id.img_banner);
                            Bitmap bitmap=bitmapLists.get(i);
                            imgBanner.setImageBitmap(bitmap);
                            bitmapByteList.add(getBytes(bitmap));
                        }
                        //如果文件不存在，则保存图片到本地储存中
                        SaveArticle.setData(fragmentActivity,beanList,TWO);
                        SaveArticle.setData(fragmentActivity,bitmapByteList,ONE);
                        //dialogHandler.sendEmptyMessage(ONE);
                        loadFragment();
                        transaction.hide(fragment).commit();
                    }else {
                        for (int i = 0; i < viewList.size(); i++) {
                            View view = viewList.get(i);
                            ImageView imgBanner = view.findViewById(R.id.img_banner);
                            Bitmap bitmap=bitmapLists.get(i);
                            imgBanner.setImageBitmap(bitmap);
                        }
                    }
                });
            }
            @Override
            public void requestArticleVp() {
                mPresenter.getContract().requestArticleVp();
            }

            @Override
            public void responseArticleResult(List<ArticleBean> articleLists, List<ArticleBean> articleTopLists) {
                fragmentActivity.runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        loadFragment();
                        if ((articleList.size()==0)){
                            if (articleTopLists!=null){
                                if (articleTopLists.size()!=0){
                                    for (ArticleBean article:articleTopLists){
                                        article.setTop(-1);
                                        articleList.add(article);
                                    }
                                }
                            }
                            articleList.addAll(articleLists);
                            if (file.exists()){
                                transaction.hide(fragment).commit();
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            transaction.hide(fragment).commit();
                        }
                    }
                });
            }
            @Override
            public void error(int error) {
                errorCode=error;
                fragmentActivity.runOnUiThread(() -> Toast.makeText(fragmentActivity, "网络请求错误", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void requestLoadMoreVp(int page) {
                mPresenter.getContract().requestLoadMoreVp(page);
            }

            @Override
            public void responseLoadMoreVp(List<ArticleBean> articleLists) {
                fragmentActivity.runOnUiThread(() -> {
                    if (articleLists.size()!=0){
                        articleList.addAll(articleLists);
                        adapter.notifyItemInserted(articleList.size());
                    }else {
                        Toast.makeText(fragmentActivity, "已加载全部内容", Toast.LENGTH_SHORT).show();
                        View view=recyclerView.getChildAt(recyclerView.getChildCount()-1);
                        if (view!=null){
                            ProgressBar bar=view.findViewById(R.id.progressBar);
                            bar.setVisibility(View.INVISIBLE);
                            TextView tv=view.findViewById(R.id.tv);
                            tv.setVisibility(View.VISIBLE);
                        }
                    }
                    dialog.dismiss();
                });
            }

            @Override
            public void saveHistory(ArticleBean article) {
                mPresenter.getContract().saveHistory(article);
            }
        };
    }
    private int page = 0;
    private File file=null;
    private List<View> viewList;
    static ViewPager viewPager;
    private List<BannerBean> beanLists;
    private List<Bitmap> bitmapLists;
    private final List<byte[]> bitmapByteList=new ArrayList<>();
    private int errorCode=0;
    private ProgressDialog dialog;
    static List<ArticleBean> articleList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private FragmentActivity fragmentActivity;
    @SuppressLint("StaticFieldLeak")
    static ArticleAdapter adapter = new ArticleAdapter(articleList);
    private NestedScrollView scrollView;
    private RecyclerView recyclerView;
    private int count = 0;
    private int count1 = 0;
    private FragmentTransaction transaction;
    private final LoadingFragment fragment=new LoadingFragment();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetRecyclerView.setRecyclerViewScroll(fragmentActivity, recyclerView, adapter);
    }
    /**
     * 该方法应该放在onViewCreated方法中执行
     */
    private void calculate(){
        ConstraintLayout ll=fragmentActivity.findViewById(R.id.fragment_home);
        ViewTreeObserver vto2 = ll.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.i("TAG", "方法3："+ll.getWidth()+":"+ll.getHeight());
            }
        });
        Log.i("TAG", "屏幕的宽度："+fragmentActivity.getWindowManager().getDefaultDisplay().getWidth());
    }

    @Override
    protected void destroy() {
    }

    @Override
    public void onPause() {
        super.onPause();
        //碎片暂停时就保存一下文章列表到本地
        SaveArticle.setData(fragmentActivity,articleList,0);
    }

    @Override
    protected void initView(View view) {
        ConstraintLayout layout = fragmentActivity.findViewById(R.id.fragment_home);
        scrollView = layout.findViewById(R.id.scroll_view_home);
        //根据id获取RecycleView的实例
        recyclerView = fragmentActivity.findViewById(R.id.rv_article);
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {   //scrollY是滑动的距离
                if (scrollY == (v.getChildAt(ZERO).getMeasuredHeight() - v.getMeasuredHeight())) {
                    //滑动到底部
                    dialog= ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
                    //加载失败时
                    if (errorCode==ONE){
                        articleList.clear();
                        adapter.notifyDataSetChanged();
                        //如果第一次请求网络失败则重新加载首页文章
                        getContract().requestArticleVp();
                        errorCode=ZERO;
                    }else {
                        page++;
                        getContract().requestLoadMoreVp(page);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onLikesClicked(View view, int position, TextView likes, int[] count) {
                int number0 = ZERO;
                if (articleList.get(position).isCollect()){
                    number0=ONE;
                }
                if (count[ZERO] % TWO == number0) {
                    //点赞
                    getContract().collectVp(articleList.get(position).getId(), new CollectListener() {
                        @Override
                        public void onFinish() {
                            //收藏成功则改变点赞图标为红心
                            likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes, null));
                        }
                        @Override
                        public void onError() {
                        }
                    });
                } else {
                    //取消点赞
                    getContract().unCollectVp(articleList.get(position).getId(), new CollectListener() {
                        @Override
                        public void onFinish() {
                            //取消收藏成功则改变点赞图标为红心
                            likes.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_likes_gray, null));
                        }
                        @Override
                        public void onError() {
                        }
                    });
                }
                count[0]++;
            }

            @Override
            public void onArticleClicked(View view, int position) {
                ArticleBean article=articleList.get(position);
                Intent intent = new Intent(fragmentActivity, WebViewActivity.class);
                intent.putExtra("url", article.getLink());
                startActivity(intent);
                //将点击的文章插入数据库表
                getContract().saveHistory(article);
            }
        });
    }
    private void loadFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        transaction= fragmentManager.beginTransaction();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = getPresenterInstance();
        mPresenter.bindView(this);
        fragmentActivity = requireActivity();
        file=new File(fragmentActivity.getCacheDir(),CACHE_BITMAP);
        if (count == ZERO) {
            //如果是第一次打开主页Fragment,则加载数据
            //setRetainInstance(true);
            loadFragment();
            transaction.add(R.id.fragment_home,fragment).show(fragment);
            transaction.commit();
            //dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
            getContract().requestArticleVp();
            count = ONE;
        }
        //获取Fragment的布局视图
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.indicator_all);
        inflater = getLayoutInflater();
        //获取轮播图的view视图
        View view1 = inflater.inflate(R.layout.layout1, container,false);
        View view2 = inflater.inflate(R.layout.layout2, container,false);
        View view3 = inflater.inflate(R.layout.layout3, container,false);
        // 将要分页显示的View装入数组中
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        if (count1==ZERO){
            count1=ONE;
            //如果是第一次打开主页Fragment,则加载轮播图
            getContract().requestImageVp();
        }
        BannerAdapter pagerAdapter = new BannerAdapter(viewList);
        pagerAdapter.setOnPictureClickListener(position -> {
            //点击轮播图则跳转对应的网页
            String url = beanLists.get(position).getUrl();
            Intent intent = new Intent(fragmentActivity, WebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            //有机会可以做保存轮播图对应的文章的历史记录
        });
        viewPager.setAdapter(pagerAdapter);
        //设置ViewPager的缓存页数为3
        viewPager.setOffscreenPageLimit(TWO);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ZERO) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(ZERO, THREE_THOUSAND);
                }
            }
        });
        //发送信息使轮播图开始滚动
        mHandler.sendEmptyMessageDelayed(ZERO, THREE_THOUSAND);
        return view;
    }

    @SuppressLint("HandlerLeak")
    public static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==ZERO){
                //用于每隔3秒切换轮播图
                int count = 3;
                int index = viewPager.getCurrentItem();
                index = (index + ONE) % count;
                viewPager.setCurrentItem(index);
                mHandler.sendEmptyMessageDelayed(ZERO, THREE_THOUSAND);
            }else {
                //加载文章完成则通知adapter进行更新
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void initData() {
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
