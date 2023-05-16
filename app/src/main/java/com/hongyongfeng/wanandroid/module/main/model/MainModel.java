package com.hongyongfeng.wanandroid.module.main.model;

import static android.content.Context.MODE_PRIVATE;
import static com.hongyongfeng.wanandroid.module.signinorup.login.model.LoginFragmentModel.COOKIE_PREF;

import android.content.SharedPreferences;
import android.util.Log;

import com.hongyongfeng.wanandroid.base.BaseModel;
import com.hongyongfeng.wanandroid.module.main.interfaces.MainInterface;
import com.hongyongfeng.wanandroid.module.main.presenter.MainPresenter;
import com.hongyongfeng.wanandroid.util.MyApplication;

public class MainModel extends BaseModel<MainPresenter, MainInterface.M> {
    public MainModel(MainPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public MainInterface.M getContract() {
        return new MainInterface.M() {
            @Override
            public void requestM() throws Exception {
                try {
                    SharedPreferences preferences= MyApplication.getContext().getSharedPreferences(COOKIE_PREF,MODE_PRIVATE);
                    String cookies=preferences.getString("login","");
                    //System.out.println(cookies);
                    int first=cookies.indexOf(";")+1;
                    int last=cookies.lastIndexOf(";");
                    cookies=cookies.substring(first,last);
                    first=cookies.indexOf("=")+1;
                    mPresenter.getContract().responseResult(cookies.substring(first));
                }catch (Exception e){
                    Log.d("MainModel","还没登录");
                    //e.printStackTrace();
                }
            }
        };
    }
}
