package com.hongyongfeng.wanandroid.data.net.bean;

/**
 * @author Wingfung Hung
 */
public class ProjectBean {
    public ProjectBean() {
    }
    /**
     * 项目id
     */
    private int id;
    /**
     * 项目作者
     */
    private String author;
    /**
     * 项目详情
     */
    private String desc;
    /**
     * 项目图片地址
     */
    private String envelopePic;
    /**
     * 项目链接
     */
    private String link;
    /**
     * 项目发布时间
     */
    private String niceDate;
    /**
     * 项目标题
     */
    private String title;
    /**
     * 是否点赞
     */
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
}
