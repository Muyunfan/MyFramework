package com.muyunfan.fw.mainmodule.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.muyunfan.fw.R;
import com.muyunfan.fw.basemodule.app.BaseActivity;
import com.muyunfan.fw.basemodule.bean.EventCenter;
import com.muyunfan.fw.mainmodule.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainActivity, MainPresenter> {

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected String setViewTag() {
        return "MainActivity";
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    public SwipeRefreshLayout srlRefresh;
    public RecyclerView rvRecyclerView;

    @Override
    protected void initView() {
        srlRefresh = genericFindViewById(R.id.refresh);
        rvRecyclerView = genericFindViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void eventThread(EventCenter event) {

    }
}
