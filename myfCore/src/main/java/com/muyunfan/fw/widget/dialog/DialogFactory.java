package com.muyunfan.fw.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muyunfan.fw.R;

/**
 * 项目名称：wk_as_reconsitution
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2017/3/3 13:36
 * 修改人：李程伟
 * 修改时间：2017/3/3 13:36
 * 修改备注：
 */

public class DialogFactory {


    /**
     * 通用Dialog
     *
     * @param context
     * @return
     */
    public static Dialog getFinishDialog(Context context,
                                         View.OnClickListener cancelClick,
                                         View.OnClickListener confirmClick) {
        View mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_finish, null);
        TextView tvTitle = (TextView) mDialogView.findViewById(R.id.tv_title);
        Button btnCancel = (Button) mDialogView.findViewById(R.id.btn_cancel);
        Button btnConfirm = (Button) mDialogView.findViewById(R.id.btn_confirm);
        btnCancel.setOnClickListener(cancelClick);
        btnConfirm.setOnClickListener(confirmClick);
        RelativeLayout dialogLayout = (RelativeLayout) mDialogView.findViewById(R.id.dialog_bg);
        Dialog mDialog = new Dialog(context,
                android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setContentView(mDialogView);

        return mDialog;
    }
}
