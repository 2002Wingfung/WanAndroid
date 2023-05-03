package com.hongyongfeng.wanandroid.data.net.bean;

public class ProjectBean {
    private int articleId;
    private int id;
    private String author;
    private String category;
    private boolean likes;
    private boolean top;
    private String url;
    private String title;
    private long publishTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public ProjectBean(int articleId) {
        this.articleId = articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
