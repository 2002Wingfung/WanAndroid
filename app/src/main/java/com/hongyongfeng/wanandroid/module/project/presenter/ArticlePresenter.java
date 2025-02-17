package com.hongyongfeng.wanandroid.module.project.presenter;


import android.graphics.Bitmap;
import com.hongyongfeng.wanandroid.base.BaseFragmentPresenter;
import com.hongyongfeng.wanandroid.data.net.bean.ProjectBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.CollectListener;
import com.hongyongfeng.wanandroid.module.project.interfaces.ArticleInterface;
import com.hongyongfeng.wanandroid.module.project.model.ArticleModel;
import com.hongyongfeng.wanandroid.module.project.view.fragment.ProjectArticleFragment;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class ArticlePresenter extends BaseFragmentPresenter<ArticleModel, ProjectArticleFragment, ArticleInterface.Vp> {
    @Override
    public ArticleModel getModelInstance() {
        return new ArticleModel(this);
    }

    @Override
    public ArticleInterface.Vp getContract() {
        return new ArticleInterface.Vp() {
            @Override
            public void requestTitleVp(int id, int page) {
                //调用model层
                try {
                    mModel.getContract().requestTitleM(id,page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseTitleResult(List<ProjectBean> projectList) {
                mView.getContract().responseTitleResult(projectList);
            }

            @Override
            public void responseImageResult(Bitmap bitmap) {
                mView.getContract().responseImageResult(bitmap);
            }

            @Override
            public void saveProject(ProjectBean project) {
                try {
                    mModel.getContract().saveProjectM(project);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void collectVp(int id, CollectListener listener) {
                try {
                    mModel.getContract().collectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void unCollectVp(int id, CollectListener listener) {
                try {
                    mModel.getContract().unCollectM(id,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void collectResponse(int code) {
                mView.getContract().collectResponse(code);
            }

            @Override
            public void unCollectResponse(int code) {
                mView.getContract().unCollectResponse(code);
            }
        };
    }
}
