package com.muyunfan.fw.widget.recyclerview;

import android.view.View;

/**
 * 项目名称：wk_as_reconsitution
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2016/10/25 11:06
 * 修改人：李程伟
 * 修改时间：2016/10/25 11:06
 * 修改备注：
 * @version
 */

public interface OnLongClickByViewIdListener<T> {
    public void longClickByViewId(View view, T t, int position);
}
