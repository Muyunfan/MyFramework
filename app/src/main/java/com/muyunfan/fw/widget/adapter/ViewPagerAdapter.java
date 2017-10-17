package com.muyunfan.fw.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 类名称：com.hbh.hbhforworkers.tasklibrary.presenter.adapter
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/2/8.13:27
 * 修改人： 李程伟
 * 修改时间：2017/2/8.13:27
 * 修改备注：
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitleArray;
    private List<Fragment> fragmentList;
    private Context context;


    public ViewPagerAdapter(FragmentManager fm,
                            String[] tabTitleArray,
                            @NonNull List<Fragment> fragmentList,
                            Context context) {
        super(fm);
        this.tabTitleArray = tabTitleArray;
        this.fragmentList = fragmentList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleArray[position % tabTitleArray.length];
    }
}
