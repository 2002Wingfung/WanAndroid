package com.hongyongfeng.wanandroid.data.net.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ArticleBean implements Parcelable {
    public ArticleBean() {
    }

    private int id;
    private String author;
    private String chapterName;
    private String link;
    private String title;
    private String niceDate;
    private String superChapterName;

    protected ArticleBean(Parcel in) {
        id = in.readInt();
        author = in.readString();
        chapterName = in.readString();
        link = in.readString();
        title = in.readString();
        niceDate = in.readString();
        superChapterName = in.readString();
        top = in.readInt();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(chapterName);
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(niceDate);
        dest.writeString(superChapterName);
        dest.writeInt(top);
    }
    public static final Parcelable.Creator<ArticleBean> CREATOR  = new Creator<ArticleBean>() {
        //实现从source中创建出类的实例的功能
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            ArticleBean article  = new ArticleBean();
            article.id = source.readInt();
            article.author= source.readString();
            article.chapterName = source.readString();
            article.link = source.readString();
            article.title = source.readString();
            article.niceDate = source.readString();
            article.superChapterName = source.readString();
            article.top = source.readInt();
            return article;
        }
        //创建一个类型为T，长度为size的数组
        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
