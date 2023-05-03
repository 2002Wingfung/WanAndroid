package com.hongyongfeng.wanandroid.data.net.bean;

public class KnowledgeCategoryBean {
    private int id;

    private String detailedCategory;
    private String category;

    private String url;

    public KnowledgeCategoryBean(String category, String detailedCategory) {
        this.detailedCategory = detailedCategory;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDetailedCategory() {
        return detailedCategory;
    }

    public void setDetailedCategory(String detailedCategory) {
        this.detailedCategory = detailedCategory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
