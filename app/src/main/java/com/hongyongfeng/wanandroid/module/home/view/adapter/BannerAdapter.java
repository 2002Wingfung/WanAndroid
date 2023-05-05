package com.hongyongfeng.wanandroid.module.home.view.adapter;

import static com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment.mHandler;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.OnLoadImageListener;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    View view1,view2,view3,view4;
    private List<View> viewList;
    public int up=0;
    public static int down=0;
    private List<BannerBean> mBannerBean;
    private OnLoadImageListener mOnLoadImageListener;

    /**
     * @param bannerBean          装有图片路径的数据源
     * @param onLoadImageListener 加载图片的回调接口 让调用层处理加载图片的逻辑
     */
    public BannerAdapter(List<View> viewList,List<BannerBean> bannerBean, OnLoadImageListener onLoadImageListener) {
        this.mBannerBean = bannerBean;
        this.mOnLoadImageListener = onLoadImageListener;
        this.viewList = viewList;
    }

    public BannerAdapter( List<View> viewList) {
        this.viewList = viewList;

    }
    public interface OnPictureClickListener{
        void onPictureClick(int position);
    }
    private OnPictureClickListener onPictureClickListener;
    public void setOnPictureClickListener(OnPictureClickListener onPictureClickListener){
        this.onPictureClickListener=onPictureClickListener;
    }
    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            @NonNull Object object) {
        container.removeView(viewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        ImageView iv= view.findViewById(R.id.img_banner);
        //等比例缩放图片,占满容器
        //iv.setScaleType(ImageView.ScaleType.FIT_XY);
        if (null!=mOnLoadImageListener){
            //设置回调,传入数据 让调用层(Activity)去处理加载图片的逻辑
            //mOnLoadImageListener.loadImage(container.getContext(),mBannerBean.get(position),position,iv);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调机制
                onPictureClickListener.onPictureClick(position);
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //按下
                        mHandler.removeCallbacksAndMessages(null);
                        down=1;
                        up=0;
                        System.out.println("Down");
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //抬起
                        down=0;
                        up=1;
                        mHandler.sendEmptyMessageDelayed(0, 1000*3);
                        System.out.println("up");
                        break;
                    }
                }
                return false;
            }
        });
        container.addView(view);
        return view;
    }
}
