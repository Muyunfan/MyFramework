package com.muyunfan.fw.widget.view.indicator;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.muyunfan.fw.core.bean.task.ImgBean;
import com.muyunfan.fw.widget.utils.bitmap.GlideUtil;
import com.muyunfan.fw.widget.view.SmoothImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：GuidePagerAdapter
 * 类描述：引导页的ViewPager适配器
 * 创建人：lei.zhang
 * 创建时间：on 2017/2/7
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class ImageAdapter extends PagerAdapter {

    private List<ImgBean> imgBeenList;
    private List<SmoothImageView> imageViewList;

    public ImageAdapter() {
    }

    public ImageAdapter(List<ImgBean> imgBeenList, List<SmoothImageView> imageViewList) {
        if(null == imgBeenList || null == imageViewList){
            imgBeenList = new ArrayList<>();
            imageViewList = new ArrayList<>();
        }
        this.imgBeenList = imgBeenList;
        this.imageViewList = imageViewList;
    }

    /**
     * 销毁一个Item
     *
     * @param container ViewPager
     * @param position  要销毁item的位置
     * @param object    instantiateItem方法的返回值
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((SmoothImageView) object);
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return imgBeenList.size();
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        GlideUtil.loadImage(imgBeenList.get(position).longPath, imageViewList.get(position));
        if(mImageClickListener != null){
            imageViewList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageClickListener.imageClick(imgBeenList.get(position));
                }
            });
        }
        container.addView(imageViewList.get(position)); //注意需要主动将Item加入到容器中
        return imageViewList.get(position);
    }

    private ImageClickListener mImageClickListener;

    public void setOnImageClickListener(ImageClickListener imageClickListener){
        this.mImageClickListener = imageClickListener;
    }

}
