package com.hongyongfeng.wanandroid.module.home.interfaces;

import android.content.Context;
import android.view.View;

import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

public interface OnLoadImageListener {
    //最后一个参数类型为View而不是ImageView，主要为了适应item布局的多样性 使用时强转一下就行了
    void loadImage( int position, View imageView);
}
