package com.hongyongfeng.wanandroid.data.net.bean;

public class ProjectBean {
    //最后把这两个字段删除
    private int articleId;

    public ProjectBean() {
    }

    private int id;
    //下面开始才是有用的
    private String author;
    private String desc;
    private String envelopePic;
    private String link;
    private String niceDate;
    private String title;
    private boolean collect;

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArticleId() {
        return articleId;
    }

    public ProjectBean(int articleId) {
        this.articleId = articleId;
    }

    public ProjectBean(String title, String desc) {
        this.desc = desc;
        this.title = title;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
