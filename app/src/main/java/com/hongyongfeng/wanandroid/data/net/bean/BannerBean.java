package com.hongyongfeng.wanandroid.data.net.bean;

import java.io.Serializable;

/**
 *轮播图实体类
 * @author Wingfung Hung
 */
public class BannerBean implements Serializable {
    /**
     * 图片地址
     */
    private String imagePath;
    /**
     * 图片所对应的链接
     */
    private String url;
    /**
     * 图片所对应的id
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BannerBean(String imagePath, String url, int id) {
        this.imagePath = imagePath;
        this.url = url;
        this.id=id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
