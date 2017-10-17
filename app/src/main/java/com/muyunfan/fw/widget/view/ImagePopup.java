package com.muyunfan.fw.widget.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.muyunfan.fw.R;
import com.muyunfan.fw.basemodule.bean.task.ImgBean;
import com.muyunfan.fw.widget.utils.common.CheckUtil;
import com.muyunfan.fw.widget.utils.common.CommonUtil;
import com.muyunfan.fw.widget.utils.common.LogUtil;
import com.muyunfan.fw.widget.utils.common.ToastUtil;
import com.muyunfan.fw.widget.view.indicator.CirclePageIndicator;
import com.muyunfan.fw.widget.view.indicator.ImageAdapter;
import com.muyunfan.fw.widget.view.indicator.ImageClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.muyunfan.fw.widget.utils.common.ToastUtil.showShort;

/**
 * 作者：李程伟
 * 时间：2017/5/15 0015.14:34
 * 类描述：图片放大popupWindow
 */

public class ImagePopup {

    private static ImagePopup mInstance;

    private Context context;

    private ViewPager viewpager;
    private CirclePageIndicator indicatorGuide;

    private List<ImgBean> mImgBeanList;
    private List<SmoothImageView> mImgViewList;
    private int mPositionIn;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private int mImgWidthSpace;

    private PopupWindow warnPopupWindow;
    private Handler handler = new Handler();

    public ImagePopup() {
    }

    public static ImagePopup getInstance() {
        if (mInstance == null) {
            mInstance = new ImagePopup();
        }
        return mInstance;
    }

    /*
    单个图片放大显示
     */
    public void showImagePopupWindow(Context context,
                                     View parent,
                                     ImageView imageView,
                                     String imgUrl,
                                     int imgWidthSpace){
        this.mImgWidthSpace = imgWidthSpace;
        this.showImagePopupWindow(context,parent,imageView,imgUrl);
    }

    public void showImagePopupWindow(Context context,
                                     View parent,
                                     ImageView imageView,
                                     String imgUrl){
        if(CheckUtil.isEmpty(imgUrl)){
            showShort("获取图片列表失败img empty error");
            return;
        }
        List<ImgBean> mImgBeanList = new ArrayList<>();
        ImgBean imgBean = new ImgBean();
        imgBean.longPath=imgUrl;
        mImgBeanList.add(imgBean);
        this.showImagePopupWindow(context,parent,imageView,mImgBeanList,0);
    }

    /*
    多个图片放大显示
     */
    public void showImagePopupWindow(Context context,
                                     View parent,
                                     ImageView imageView,
                                     List<ImgBean> imgBeanList,
                                     int position,
                                     int imgWidthSpace){
        this.mImgWidthSpace = imgWidthSpace;
        this.showImagePopupWindow(context,parent,imageView,imgBeanList,position);
    }

    public void showImagePopupWindow(Context context,
                                     View parent,
                                     ImageView imageView,
                                     List<ImgBean> imgBeanList,
                                     int positionIn) {
        if(mImgWidthSpace == 0){
            mImgWidthSpace = 80;
        }
        //初始化点击图片的location，width，height
        this.mPositionIn = positionIn;
        this.context = context;
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        this.mLocationX = location[0];
        this.mLocationY = location[1];
        this.mWidth = imageView.getWidth();
        this.mHeight = imageView.getHeight();
        this.mImgBeanList = imgBeanList;
        initPopup(parent);
    }

    private void initPopup(View parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_image, null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        indicatorGuide = (CirclePageIndicator) view.findViewById(R.id.indicator_guide);
        warnPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        warnPopupWindow.setFocusable(true);
        view.setFocusable(true); // 这个很重要
        view.setFocusableInTouchMode(true);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mImgViewList.get(viewpager.getCurrentItem()) != null) {
                        mImgViewList.get(viewpager.getCurrentItem()).transformOut();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            warnPopupWindow.dismiss();
                        }
                    }, 500);
                    return true;
                }
                return false;
            }
        });

        if(!initImage()){
            return;
        }

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        warnPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        CommonUtil.setPopupWindowTouchModal(warnPopupWindow, false);

        /**
         * 设置好参数之后再show
         * 第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档”a parent view to get the android.view.View.getWindowToken() token from
         “,这个parent的作用应该是调用其getWindowToken()方法获取窗口的Token,所以，只要是该窗口上的控件就可以了。
         */
//        warnPopupWindow.showAtLocation(parent,Gravity.LEFT|Gravity.TOP, 0, 0);
//        warnPopupWindow.showAsDropDown(parent);
        warnPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        //-CommonUtil.convertDipToPx(context,34)为activity中title底部到屏幕顶部的距离
        warnPopupWindow.showAsDropDown(parent,0,-CommonUtil.convertDipToPx(context,34));

        warnPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LogUtil.i("onDismiss");
            }
        });
    }

    /*
    初始化图片列表
     */
    private boolean initImage() {
        if (null == mImgBeanList || mImgBeanList.size() <= 0){
            ToastUtil.showShort("获取图片列表失败list null error");
            return false;
        }
        //获取横向两张图片的左上角之间的间距，用于计算图片所在屏幕位置
        int widthSpace = CommonUtil.convertDipToPx(context,mImgWidthSpace);

        mImgViewList = new ArrayList<>();
        //先添加所点击的图片的容器，并添加放大动画transformIn
        SmoothImageView imageViewIn = new SmoothImageView(context);
        imageViewIn.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageViewIn.transformIn();
        imageViewIn.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageViewIn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImgViewList.add(imageViewIn);

        //遍历加载所有图片的容器
        for(int i = 0 ; i < mImgBeanList.size() ; i ++){
            if(CheckUtil.isEmpty(mImgBeanList.get(i).longPath)){
                ToastUtil.showShort("获取图片列表失败img empty error");
                return false;
            }
            if(i != mPositionIn){
                SmoothImageView imageView = new SmoothImageView(context);
                imageView.setOriginalInfo(
                        mWidth,
                        mHeight,
                        mLocationX + ((i - mPositionIn)*widthSpace),
                        mLocationY);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mImgViewList.add(i,imageView);
            }
        }
        if(mImgBeanList.size() != mImgViewList.size()){
            ToastUtil.showShort("获取图片列表失败list size error");
            return false;
        }

        ImageAdapter imageAdapter = new ImageAdapter(mImgBeanList,mImgViewList);
        imageAdapter.setOnImageClickListener(new ImageClickListener() {
            @Override
            public void imageClick(ImgBean imgBean) {
                if (mImgViewList.get(viewpager.getCurrentItem()) != null) {
                    mImgViewList.get(viewpager.getCurrentItem()).transformOut();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        warnPopupWindow.dismiss();
                    }
                }, 500);
            }
        });
        viewpager.setAdapter(imageAdapter);
        viewpager.setCurrentItem(mPositionIn);
        indicatorGuide.setViewPager(viewpager);
        //小圆点跟随手指移动
        indicatorGuide.setSnap(false);
        return true;
    }
}
