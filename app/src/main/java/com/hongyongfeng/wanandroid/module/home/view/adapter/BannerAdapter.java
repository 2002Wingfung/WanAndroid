package com.hongyongfeng.wanandroid.module.home.view.adapter;

import static com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment.mHandler;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class BannerAdapter extends PagerAdapter {
    /**
     * 装有图片的View集合
     */
    private final List<View> viewList;
    /**
     * 用于标识是否按下，按下为0，松开为1
     */
    public int up=0;
    /**
     * 用于标识是否按下，按下为1，松开为0
     */
    public static int down=0;

    public BannerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }
    public interface OnPictureClickListener{
        /**
         * Banner图片点击事件接口
         * @param position 图片的索引标号
         */
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
    public void destroyItem(@NonNull ViewGroup container, int position,
                            @NonNull Object object) {
        //不调用父类的销毁ViewPager的方法
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = viewList.get(position);
        view.setOnClickListener(v -> {
            //回调机制，点击图片事件回调
            onPictureClickListener.onPictureClick(position);
        });
        //监听是否触摸该view
        view.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //按下，则清除Handler中的信息
                        mHandler.removeCallbacksAndMessages(null);
                        down=1;
                        up=0;
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //抬起
                        down=0;
                        up=1;
                        //重新发送信息使得轮播图滚动
                        mHandler.sendEmptyMessageDelayed(0, 1000*3);
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        container.addView(view);
        return view;
    }
}
