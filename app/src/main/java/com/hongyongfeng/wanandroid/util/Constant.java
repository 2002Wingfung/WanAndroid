package com.hongyongfeng.wanandroid.util;

/**
 * @author Wingfung Hung
 */
public class Constant {
    public static final String DOMAIN_URL="https://www.wanandroid.com/";
    public static final String REGISTER_URL="user/register";
    public static final String LOGIN_URL="user/login";
    public static final String COLLECT_URL="lg/collect/";
    public static final String ARTICLE_URL_1="article/list/";
    public static final String CANCEL_COLLECT_URL ="lg/uncollect_originId/";
    public static final String JSON_URL="/json";
    public static final String COLLECTION_URL=DOMAIN_URL+COLLECT_URL+"list/";
    /**
     *创建文章表，用于浏览历史记录
     */
    public static final String ARTICLE_BEAN=
            "create table if not exists article_bean(\n" +
                    "id int ,\n" +
                    "author varchar(10),\n" +
                    "chapterName varchar(10),\n" +
                    "link varchar(80) unique,\n" +
                    "title varchar(30),\n"+
                    "niceDate varchar(20),\n"+
                    "superChapterName varchar(10),\n"+
                    "top int,"+
                    "main_id INTEGER primary key autoincrement"+
                    ");";
    public static final String DELETE_SQL="select id from article_bean where id=?";
    public static final int ZERO=0;
    public static final int ONE=1;
    public static final int TWO=2;
    public static final int FOUR=4;
    public static final int ONE_HUNDRED=100;
    public static final int TWO_HUNDRED=200;
    public static final int THREE_HUNDRED=300;
    public static final int THREE_THOUSAND=3000;
    public static final String ACTION="CLOCK_IN";
}
