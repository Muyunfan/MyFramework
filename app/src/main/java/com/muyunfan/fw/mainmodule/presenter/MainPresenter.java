package com.muyunfan.fw.mainmodule.presenter;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.muyunfan.fw.R;
import com.muyunfan.fw.core.bean.account.Student;
import com.muyunfan.fw.core.bean.account.StudentInList;
import com.muyunfan.fw.core.presenter.BasePresenter;
import com.muyunfan.fw.mainmodule.model.MainModel;
import com.muyunfan.fw.mainmodule.recycler.model.ItemStudentM;
import com.muyunfan.fw.mainmodule.recycler.provider.ItemStudentProvider;
import com.muyunfan.fw.mainmodule.ui.MainActivity;
import com.muyunfan.fw.widget.recyclerview.LoadMoreFooterModel;
import com.muyunfan.fw.widget.recyclerview.LoadMoreFooterViewHolderProvider;
import com.muyunfan.fw.widget.recyclerview.OnClickByViewIdListener;
import com.muyunfan.fw.widget.recyclerview.RecyclerAdapter;
import com.muyunfan.fw.widget.utils.common.GsonUtil;
import com.muyunfan.fw.widget.utils.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：com.muyunfan.fw.mainmodule.presenter
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/10/17.17:19
 * 修改人： 李程伟
 * 修改时间：2017/10/17.17:19
 * 修改备注：
 */
public class MainPresenter extends BasePresenter<MainActivity, MainModel> implements OnClickByViewIdListener, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooterModel.LoadMoreListener {
    @Override
    protected MainModel createPresenter() {
        return new MainModel();
    }

    @Override
    public String setViewTag() {
        return getView().getViewTag();
    }

    private RecyclerAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private Handler handler = new Handler();
    private LoadMoreFooterModel mLoadMoreFooterModel;

    @Override
    public void initialize() {
        initViews();
        registerModel();
        initEvent();
        getStudents();
    }

    private void initViews() {
        mAdapter = new RecyclerAdapter(getView());
        getView().srlRefresh.setColorSchemeResources(R.color.color_blue, R.color.color_red, R.color.color_green, R.color.color_orange);
        layoutManager = new LinearLayoutManager(getView());
        getView().rvRecyclerView.setLayoutManager(layoutManager);
        getView().rvRecyclerView.setAdapter(mAdapter);
    }

    private void registerModel() {
        mAdapter.register(ItemStudentM.class, new ItemStudentProvider(getView()));
        mAdapter.register(LoadMoreFooterModel.class, new LoadMoreFooterViewHolderProvider());
    }

    private void initEvent() {
        mLoadMoreFooterModel = new LoadMoreFooterModel();
        mLoadMoreFooterModel.setLoadMoreListener(this);
        mAdapter.setOnClickByViewIdListener(this);
        getView().srlRefresh.setOnRefreshListener(this);
    }

    /**
     * 数据请求与回调
     */
    private void getStudents() {
        showProgressViewDialog();
        getView().srlRefresh.setRefreshing(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.clearData();
                model.getStudents();
            }
        }, 1000);
    }


    @Override
    public void modelCallBackSuccess(String requestCode, Object data) {
        switch (requestCode) {
            case MainModel.GET_STUDENTS:
                StudentInList studentInList = GsonUtil.fromJson((String) data, StudentInList.class);
                if (studentInList.students != null) {
                    dataFilter(studentInList.students);
                }
                break;
        }
    }

    private List<ItemStudentM> itemList;
    private void dataFilter(List<Student> studentList) {
        itemList = new ArrayList<>();
        for (Student student : studentList) {
            ItemStudentM model = new ItemStudentM();
            model.student = student;
            itemList.add(model);
        }
        updateData();
    }


    private boolean hasMore;

    private void updateData() {
        getView().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (itemList.size() < 10) {
                    hasMore = false;
                    mAdapter.addData(itemList);
                    mAdapter.removeFooter(mLoadMoreFooterModel);
                } else if (itemList.size() >= 10) {
                    hasMore = true;
                    mAdapter.addData(itemList);
                    mAdapter.addFooter(mLoadMoreFooterModel);
                }
            }
        });
    }


    /**
     * 点击事件及事件处理
     */

    @Override
    public void clickByViewId(View view, Object o, int position) {
        ToastUtil.showShort(((ItemStudentM) o).student.name);
    }


    /**
     * 列表刷新
     */

    @Override
    public void onRefresh() {
        getStudents();
    }

    /**
     * 上拉加载更多
     */

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasMore) {
                    mLoadMoreFooterModel.canLoadMore();
                    model.getStudents();
                }
            }
        }, 1000);
    }
}
