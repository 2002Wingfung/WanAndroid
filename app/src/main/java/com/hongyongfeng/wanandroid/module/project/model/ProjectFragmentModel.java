
package com.hongyongfeng.wanandroid.module.project.model;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import java.util.List;
import java.util.Map;

public class ProjectFragmentModel extends BaseFragmentModel<ProjectFragmentPresenter, ProjectFragmentInterface.M> {
    public ProjectFragmentModel(ProjectFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String TREE_URL="https://www.wanandroid.com/project/tree/json";

    @Override
    public ProjectFragmentInterface.M getContract() {
        return new ProjectFragmentInterface.M() {
            @Override
            public void requestTitleM() throws Exception {
                HttpUtil.sendHttpRequest(TREE_URL, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        List<Map<String,Object>> titleMapList=HttpUtil.parseJsonWithJSONObject(response);
                        mPresenter.getContract().responseTitleResult(titleMapList);
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
