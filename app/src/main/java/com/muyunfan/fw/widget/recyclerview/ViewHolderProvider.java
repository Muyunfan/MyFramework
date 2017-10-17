package com.muyunfan.fw.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muyunfan.fw.R;

/**
 * 项目名称：wk_as_reconsitution
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2016/8/31 11:06
 * 修改人：李程伟
 * 修改时间：2016/8/31 11:06
 * 修改备注：
 * @version
 */

public abstract class ViewHolderProvider<Model,VH extends RecyclerViewHolder> {

    protected OnClickByViewIdListener mOnClickByViewIdListener;
    protected OnLongClickByViewIdListener mOnLongClickByViewListener;

    public VH onCreateViewHolder(@NonNull LayoutInflater layoutInflater,
                                          @NonNull ViewGroup parent,
                                          @NonNull OnClickByViewIdListener onClickByViewIdListener,
                                          @NonNull OnLongClickByViewIdListener onLongClickByViewIdListener){
        this.mOnClickByViewIdListener = onClickByViewIdListener;
        this.mOnLongClickByViewListener = onLongClickByViewIdListener;
        return (VH)new RecyclerViewHolder(layoutInflater.inflate(setLayout(), parent, false));
    }

    public abstract int setLayout();

    public abstract void onBindViewHolder(Model model, VH viewHolder,int position);
}
