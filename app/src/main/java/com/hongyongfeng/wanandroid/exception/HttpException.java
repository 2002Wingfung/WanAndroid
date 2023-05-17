package com.hongyongfeng.wanandroid.exception;

/**
 * @author Wingfung Hung
 */
public class HttpException extends Exception{

    /**
     * 空参构造
     */
    public HttpException() {
    }

    /**
     *请求网络异常
     * @param message 表示异常的提示,如errorMsg
     */
    public HttpException(String message) {
        super(message);
    }
}
