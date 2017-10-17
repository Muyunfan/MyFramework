package com.muyunfan.fw.widget.recyclerview;

/**
 * 项目名称：wk_as_reconsitution
 * 类描述：
 * 创建人：李程伟
 * 创建时间：2016/8/31 11:05
 * 修改人：李程伟
 * 修改时间：2016/8/31 11:05
 * 修改备注：
 * @version
 */

class CheckRecyclerAdapter {
    static boolean checkInRange(int size, int position) {
        return (position >= 0 && position < size);
    }

    static boolean checkExits(int position) {
        return (position != -1);
    }

    static boolean haveYouRegistered(int type) {
        return (type == -1);
    }
}
