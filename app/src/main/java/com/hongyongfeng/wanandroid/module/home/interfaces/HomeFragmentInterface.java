package com.hongyongfeng.wanandroid.module.home.interfaces;

import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;

import java.util.List;

public interface HomeFragmentInterface {
    interface M{
        void requestImageM()throws Exception;
    }
    interface VP{
        void requestImageVP();
        void responseImageResult(List<BannerBean> beanList);
    }
}
