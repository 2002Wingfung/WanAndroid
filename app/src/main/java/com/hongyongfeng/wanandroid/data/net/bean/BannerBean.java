package com.hongyongfeng.wanandroid.data.net.bean;

import java.io.Serializable;

public class BannerBean implements Serializable {
    private String imagePath;
    private String Url;

    public BannerBean(String imagePath, String url) {
        this.imagePath = imagePath;
        Url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
