package com.talkweb.cloudcampus.module.homeworkCheck.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.talkweb.cloudcampus.manger.ImageManager;
import com.talkweb.cloudcampus.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 :  by lcq on 16/3/10.
 * 功能 :
 */
public class GridImageView extends ViewGroup {
    private int columns = 4;
    private int splitLineCount = columns - 1;
    int CRACK_WIDTH = DisplayUtils.dip2px(4);
    private List<String> images = new ArrayList<>();

    public GridImageView(Context context) {
        super(context);
        init(context);
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mViewCache.put(hashCode(), new ArrayList<ImageView>());
        setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureWidth = Math.min(getScreenWidth(), measureWidth);
        int childViewSize = (measureWidth - CRACK_WIDTH * splitLineCount) / columns;
        int count = getChildCount();
        int visibleCount = 0;
        if (count != 0) {
            measureHeight = childViewSize; //设置 最小宽度
        }
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) { //如果属性为gone的不测量
                continue;
            }
            LayoutParams layoutParams = childView.getLayoutParams();
            layoutParams.width = layoutParams.height = childViewSize;
            childView.setLayoutParams(layoutParams);
            visibleCount++;
        }
        if (visibleCount > columns) {
            int heightCountSize = measureHeight * visibleCount / columns;
            if (visibleCount % columns != 0)
                heightCountSize = heightCountSize + measureHeight;
            measureHeight = heightCountSize;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int getScreenWidth() {
        return DisplayUtils.getWidthPx();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int mPainterPosX = left;  //当前绘图光标横坐标位置
        int mPainterPosY = top;  //当前绘图光标纵坐标位置

        int count = getChildCount();
        for (int x = 0; x < count; x++) {
            //layout all child view
            View childView = getChildAt(x); //获取一个view
            if (childView.getVisibility() == GONE) {
                continue;
            }

            //获取子view的宽度和高度
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //换行
            if (mPainterPosX + childWidth > getMeasuredWidth()) {
                mPainterPosX = 0; //光标回到view最前面
                mPainterPosY = (childHeight * (x / columns)) + DisplayUtils.dip2px(4) * (x / columns);
            }

            //layout 所有的view
            childView.layout(mPainterPosX, mPainterPosY, mPainterPosX + childWidth, mPainterPosY + childHeight);

//            ViewGroup.LayoutParams lp = (LayoutParams) childView.getLayoutParams();
            mPainterPosX = mPainterPosX + childWidth + CRACK_WIDTH /*lp.rightMargin*/;
        }

    }

    private List<ImageView> imageViews = new ArrayList<>();

    @NonNull
    private void createImages() {
        removeAllViews();
        List<ImageView> tempViews = new ArrayList<>();
        for (int x = 0; x < images.size(); x++) {
            ImageView imageView = generateView();
            addView(imageView);
            ImageLoader.getInstance().displayImage(images.get(x), imageView, ImageManager.getThumbImageOptions());
            tempViews.add(imageView);
        }
        mViewCache.put(hashCode(), tempViews);
        requestLayout();
    }

    @NonNull
    private ImageView generateView() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //params.rightMargin = CRACK_WIDTH;
        imageView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(params);

        return imageView;
    }

    public void setImagesURL(List<String> images) {
        this.images.clear();
        this.images.addAll(images);
        List<ImageView> cacheImageView = mViewCache.get(hashCode());
        if (cacheImageView != null && cacheImageView.size() != 0) {
            setCacheImageViewUrl(cacheImageView);
        } else {
            createImages();
        }

    }

    private void setCacheImageViewUrl(List<ImageView> cacheImageView) {
        //缓存的image对象 少于 url数量 创建
        List<ImageView> temp = new ArrayList<>();
        if (cacheImageView.size() != images.size()) {
            if (cacheImageView.size() < images.size()) {
                //添加view 两种方法添加view
                //从缓存的view中添加
                int oddSize = oddImageView.size();
                int differ = images.size() - cacheImageView.size();
                if (differ <= oddSize) {
                    temp.addAll(oddImageView.subList(0, differ));
                    oddImageView.removeAll(temp);
                } else {
                    //如果缓存部分数据不够 则创建
                    differ = differ - oddSize;
                    temp.addAll(oddImageView);
                    oddImageView.clear();
                    for (int x = 0; x < differ; x++) {
                        cacheImageView.add(generateView());
                    }
                }
                for (int x = 0; x < temp.size(); x++) {
                    ImageView imageView = temp.get(x);
                    ViewParent parent = imageView.getParent();
                    if (null == parent) {
                        addView(imageView);
                    }
                }
                cacheImageView.addAll(temp);
            } else {//cache
                temp.addAll(cacheImageView.subList(images.size(), cacheImageView.size()));
                for (int x = 0; x < temp.size(); x++) {
                    ImageView imageView = temp.get(x);
                    ViewParent parent = imageView.getParent();
                    if (null != parent && parent instanceof ViewGroup) {
                        ((ViewGroup) parent).removeView(imageView);
                    }
                }
                oddImageView.addAll(temp);
                cacheImageView.removeAll(temp);
            }
            temp.clear();
        }
        for (int x = 0; x < cacheImageView.size(); x++) {
            ImageLoader.getInstance().displayImage(images.get(x), cacheImageView.get(x), ImageManager.getThumbImageOptions());
        }
    }

    @Override
    public String toString() {
        return String.valueOf(hashCode());
    }

    /**
     * 设置一行展示列数
     *
     * @param number
     */
    public void setColumns(int number) {
        this.columns = number;
    }


    /*
     * cache
     */
    private SimpleArrayMap<Integer, List<ImageView>> mViewCache = new ArrayMap<>();
    private static List<ImageView> oddImageView = new ArrayList<>();
}
