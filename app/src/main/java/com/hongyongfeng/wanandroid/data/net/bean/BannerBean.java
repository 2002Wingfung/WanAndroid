package com.hongyongfeng.wanandroid.data.net.bean;

public class BannerBean {
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
