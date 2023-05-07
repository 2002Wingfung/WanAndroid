package com.hongyongfeng.wanandroid.data.net.bean;

public class ArticleBean {
    public ArticleBean() {
    }

    private int id;
    private String author;
    private String chapterName;

    private String link;
    private String title;
    private String niceDate;
    private String superChapterName;

    public String getSuperChapterName() {
        return superChapterName;
    }
    public int top;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
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

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
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

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }



    public ArticleBean(int id) {
        this.id = id;
    }

}
