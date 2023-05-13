package com.hongyongfeng.wanandroid.exception;

public class HttpException extends Exception{

    /**
     * 空参构造
     */
    public HttpException() {
    }

    /**
     *
     * @param message 表示异常提示
     */
    public HttpException(String message) {
        super(message);
    }
}
