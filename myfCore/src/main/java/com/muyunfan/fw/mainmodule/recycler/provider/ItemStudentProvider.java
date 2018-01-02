package com.muyunfan.fw.mainmodule.recycler.provider;

import android.content.Context;
import android.view.View;

import com.muyunfan.fw.R;
import com.muyunfan.fw.mainmodule.recycler.model.ItemStudentM;
import com.muyunfan.fw.widget.recyclerview.RecyclerViewHolder;
import com.muyunfan.fw.widget.recyclerview.ViewHolderProvider;

/**
 * 类名称：com.smdhx.dhx.taskmodule.recycler.provider
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/6/15.16:05
 * 修改人： 李程伟
 * 修改时间：2017/6/15.16:05
 * 修改备注：
 */
public class ItemStudentProvider extends ViewHolderProvider<ItemStudentM, RecyclerViewHolder> {

    private Context context;

    public ItemStudentProvider(Context context) {
        this.context = context;
    }

    @Override
    public int setLayout() {
        return R.layout.layout_item_student;
    }

    @Override
    public void onBindViewHolder(final ItemStudentM model, RecyclerViewHolder viewHolder, final int position) {
        viewHolder.setTVText(R.id.tv_name, model.student.name);
        viewHolder.setTVText(R.id.tv_age, String.valueOf(model.student.age));
        if (mOnClickByViewIdListener != null) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickByViewIdListener.clickByViewId(v, model, position);
                }
            });
        }
    }
}
