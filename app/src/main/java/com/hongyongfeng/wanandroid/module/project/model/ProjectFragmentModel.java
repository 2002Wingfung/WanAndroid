package com.hongyongfeng.wanandroid.module.project.model;

import static com.hongyongfeng.wanandroid.util.Constant.DOMAIN_URL;
import static com.hongyongfeng.wanandroid.util.Constant.JSON_URL;
import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.module.project.interfaces.ProjectFragmentInterface;
import com.hongyongfeng.wanandroid.module.project.presenter.ProjectFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;
import java.util.List;
import java.util.Map;

/**
 * @author Wingfung Hung
 */
public class ProjectFragmentModel extends BaseFragmentModel<ProjectFragmentPresenter, ProjectFragmentInterface.Model> {
    public ProjectFragmentModel(ProjectFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private static final String TREE_URL=DOMAIN_URL+"project/tree"+JSON_URL;

    @Override
    public ProjectFragmentInterface.Model getContract() {
        return () -> HttpUtil.sendHttpRequest(TREE_URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                List<Map<String,Object>> titleMapList=HttpUtil.parseJsonWithJsonObject(response);
                mPresenter.getContract().responseTitleResult(titleMapList);
            }

            @Override
            public void onError(Exception e) {
            }
        },null);
    }
}
