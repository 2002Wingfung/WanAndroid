package com.hongyongfeng.wanandroid.module.home.presenter;


import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.model.HomeFragmentModel;
import com.hongyongfeng.wanandroid.module.home.view.fragment.HomeFragment;

public class HomeFragmentPresenter extends BaseFragmentPresenter<HomeFragmentModel, HomeFragment, HomeFragmentInterface.VP> {
    @Override
    public HomeFragmentModel getModelInstance() {
        return new HomeFragmentModel(this);
    }

    @Override
    public HomeFragmentInterface.VP getContract() {
        return new HomeFragmentInterface.VP() {
            @Override
            public void requestLoginVP(String name, String pwd) {
                //核验请求的信息，进行逻辑处理
                //调用model层
                try {
//                    mModel.requestLogin(name,pwd);
                    mModel.getContract().requestLoginM(name,pwd);

                } catch (Exception e) {
                    e.printStackTrace();
                    //异常的处理
                    //保存到日志
                    //一系列的异常处理
                    //...
                }
            }

            @Override
            public void responseLoginResult(boolean loginStatusResult) {
                //真实开发过程中，是要解析数据的
                mView.getContract().responseLoginResult(loginStatusResult);
            }
        };
    }
}
