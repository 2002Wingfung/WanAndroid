package com.hongyongfeng.wanandroid.module.home.model;

import android.util.Log;

import com.hongyongfeng.wanandroid.base.BaseFragmentModel;
import com.hongyongfeng.wanandroid.base.HttpCallbackListener;
import com.hongyongfeng.wanandroid.data.net.bean.BannerBean;
import com.hongyongfeng.wanandroid.module.home.interfaces.HomeFragmentInterface;
import com.hongyongfeng.wanandroid.module.home.presenter.HomeFragmentPresenter;
import com.hongyongfeng.wanandroid.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentModel extends BaseFragmentModel<HomeFragmentPresenter, HomeFragmentInterface.M> {
    public HomeFragmentModel(HomeFragmentPresenter mPresenter) {
        super(mPresenter);
    }
    private List<BannerBean> parseJSONWithJSONObject(String toString) {
        List<BannerBean> beanList=new ArrayList<>();
        try {
            int indexStart=toString.indexOf('[');
            int indexEnd=toString.indexOf(']');
            JSONArray jsonArray=new JSONArray(toString.substring(indexStart,indexEnd+1));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id =jsonObject.getString("id");
                String imagePath =jsonObject.getString("imagePath");
                String url =jsonObject.getString("url");
                Log.d("MainActivity","id is "+id);
                Log.d("MainActivity","imagePath is "+imagePath);
                Log.d("MainActivity","url is "+url);
                BannerBean bannerBean=new BannerBean(imagePath,url);
                beanList.add(bannerBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    List<BannerBean> beanList;

    @Override
    public HomeFragmentInterface.M getContract() {
        return new HomeFragmentInterface.M() {
            @Override
            public void requestImageM() throws Exception {

                String address="https://www.wanandroid.com/banner/json";
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        beanList= parseJSONWithJSONObject(response);
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        };
    }
    public void finish(){
        mPresenter.getContract().responseImageResult(beanList);
    }
}
