package com.hongyongfeng.wanandroid.module.query.view;

import static com.hongyongfeng.wanandroid.util.Constant.ZERO;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.hongyongfeng.wanandroid.R;
import com.hongyongfeng.wanandroid.base.BaseActivity;
import com.hongyongfeng.wanandroid.data.net.bean.ArticleBean;
import com.hongyongfeng.wanandroid.module.query.interfaces.Query;
import com.hongyongfeng.wanandroid.module.query.presenter.QueryPresenter;
import com.hongyongfeng.wanandroid.module.query.view.fragment.ArticleFragment;
import com.hongyongfeng.wanandroid.module.query.view.fragment.HeatedWordsFragment;
import com.hongyongfeng.wanandroid.module.query.view.fragment.LoadingFragment;
import com.hongyongfeng.wanandroid.module.query.view.fragment.NoReturn;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wingfung Hung
 */
public class QueryActivity extends BaseActivity<QueryPresenter, Query.Vp> implements HeatedWordsFragment.CallBackListener {
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private final HeatedWordsFragment heatedWordsFragment=new HeatedWordsFragment();
    private final LoadingFragment loadingFragment=new LoadingFragment();
    private final NoReturn noReturn=new NoReturn();
    private final ArticleFragment articleFragment=new ArticleFragment();
    private TextView tvBack;
    private TextView tvClear;
    private EditText edtKeyWords;
    private ArrayList<ArticleBean> articleBeanLists;
    @Override
    public Query.Vp getContract() {
        return new Query.Vp() {
            @Override
            public void requestQueryVp(String key, int page) {
                mPresenter.getContract().requestQueryVp(key,page);
            }

            @Override
            public void responseQueryResult(List<ArticleBean> articleBeanList) {
                loadFragment();
                //获取查询的数据
                //然后传入articleFragment
                //直接把list数据传给Fragment
                //然后直接showFragment，不用搞延迟了。
                handler.sendEmptyMessageDelayed(ZERO,300);
                articleBeanLists=(ArrayList<ArticleBean>) articleBeanList;
            }

            @Override
            public void error(int code) {
                runOnUiThread(() -> {
                    loadFragment();
                    if (!noReturn.isAdded()){
                        transaction.hide(loadingFragment).add(R.id.fragment_query,noReturn).show(noReturn).commit();
                    }else {
                        transaction.hide(loadingFragment).show(noReturn).commit();
                    }
                    Toast.makeText(QueryActivity.this, "无查询结果,请更换关键字喔~", Toast.LENGTH_SHORT).show();
                });
            }
        };
    }
    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (!heatedWordsFragment.isVisible()&&!fragmentManager.isDestroyed()){
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("list",articleBeanLists);
                if (!articleFragment.isAdded()){
                    articleFragment.setArguments(bundle);
                    transaction.hide(loadingFragment).add(R.id.fragment_query,articleFragment).show(articleFragment).commit();
                }else {
                    articleFragment.setArguments(bundle);
                    transaction.hide(loadingFragment).show(articleFragment).commit();
                }
            }
        }
    };

    @Override
    public void initListener() {
        tvClear.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        edtKeyWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (edtKeyWords.getText().toString().length()>0){
                    tvClear.setVisibility(View.VISIBLE);
                }else {
                    tvClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtKeyWords.getText().toString().length()>ZERO){
                    tvClear.setVisibility(View.VISIBLE);
                }else {
                    tvClear.setVisibility(View.INVISIBLE);
                    loadFragment();
                    if (articleFragment.isVisible()){
                        transaction.hide(articleFragment).show(heatedWordsFragment).commit();
                    }else if (loadingFragment.isVisible()){
                        transaction.hide(loadingFragment).show(heatedWordsFragment).commit();
                    }else {
                        transaction.hide(noReturn).show(heatedWordsFragment).commit();
                    }
                }
            }
        });
        edtKeyWords.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                loadFragment();
                if (!loadingFragment.isAdded()){
                    if (articleFragment.isVisible()){
                        transaction.hide(heatedWordsFragment).hide(noReturn).hide(articleFragment).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
                    }else{
                        transaction.hide(heatedWordsFragment).hide(noReturn).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
                    }
                }else {
                    if (articleFragment.isVisible()) {
                        transaction.hide(heatedWordsFragment).hide(noReturn).hide(articleFragment).show(loadingFragment).commit();
                    }else{
                        transaction.hide(heatedWordsFragment).hide(noReturn).show(loadingFragment).commit();
                    }
                }
                getContract().requestQueryVp(edtKeyWords.getText().toString(),0);
            }
            //点击搜索的时候隐藏软键盘
            return false;
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_query;
    }


    @Override
    public QueryPresenter getPresenterInstance() {
        return new QueryPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !heatedWordsFragment.isVisible()) {
            //loadFragment();
            edtKeyWords.setText("");
            if (articleFragment.isVisible()){
                loadFragment();
                transaction.hide(articleFragment).show(heatedWordsFragment).commit();
            }else if (loadingFragment.isVisible()){
                loadFragment();
                transaction.hide(loadingFragment).show(heatedWordsFragment).commit();
            }else {
                loadFragment();
                transaction.hide(noReturn).show(heatedWordsFragment).commit();
            }
            //返回搜索热词页面
            return true;
        }
        //结束查询活动
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void initView() {
        tvBack=findViewById(R.id.tv_back);
        tvClear=findViewById(R.id.tv_clear);
        edtKeyWords=findViewById(R.id.edt_keyword);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(heatedWordsFragment);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_clear:
                edtKeyWords.setText("");
                loadFragment();
                if (articleFragment.isVisible()){
                    transaction.hide(articleFragment).show(heatedWordsFragment).commit();
                }else if (loadingFragment.isVisible()){
                    transaction.hide(loadingFragment).show(heatedWordsFragment).commit();
                }else {
                    transaction.hide(noReturn).show(heatedWordsFragment).commit();
                }
                break;
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        loadFragment();
        transaction.add(R.id.fragment_query,fragment).show(fragment);
        transaction.commit();
    }
    private void loadFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction= fragmentManager.beginTransaction();
    }

    @Override
    public void sendValue(String value) {
        loadFragment();
        if (!loadingFragment.isAdded()){
            transaction.hide(heatedWordsFragment).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
        }else {
            transaction.hide(heatedWordsFragment).show(loadingFragment).commit();
        }
        getContract().requestQueryVp(value,ZERO);
    }
}
