
package com.hongyongfeng.wanandroid.module.project.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ArticlePresenter;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;
import java.util.Map;

public class ArticleModel extends BaseFragmentModel<ArticlePresenter, ArticleInterface.M> {
    public ArticleModel(ArticlePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public ArticleInterface.M getContract() {
        return new ArticleInterface.M() {
            @Override
            public void requestTitleM(int id,int page) throws Exception {
                String url="https://www.wanandroid.com/project/list/"+page+"/json?cid="+id;
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<ProjectBean> projectList=HttpUtil.parseJSONWithJSONObject(response, ProjectBean.class);
                        mPresenter.getContract().responseTitleResult(projectList);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        };
    }

//    @Override
//    public void requestLogin(String name, String pwd) throws Exception {
//
//        //请求服务器登录接口，然后拿到
//        //...
//        mPresenter.responseLoginResult("wbc".equals(name) && "123".equals(pwd));
//    }
}
