package com.hongyongfeng.wanandroid.module.signinorup.register.interfaces;

/**
 * 注册接口
 * @author Wingfung Hung
 */
public interface RegisterInterface {
    /**
     * model层接口
     */
    interface Model {
        /**
         * 请求注册接口
         * @param name 用户名
         * @param pwd 密码
         * @throws Exception 异常
         */
        void requestRegisterM(String name, String pwd)throws Exception;
    }

    /**
     * vp层接口
     */
    interface Vp {
        /**
         * 请求注册接口
         * @param name 用户名
         * @param pwd 密码
         */
        void requestRegisterVp(String name, String pwd);

        /**
         * 返回注册结果接口
         * @param loginStatusResult 传回布尔值
         */
        void responseRegisterResult(boolean loginStatusResult);

        /**
         * 错误信息接口
         * @param error 服务器返回的错误提示
         */
        void error(String error);

    }
}
