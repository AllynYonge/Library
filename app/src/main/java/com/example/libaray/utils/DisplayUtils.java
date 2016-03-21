package com.example.libaray.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public class DisplayUtils {

    private static Context mContext = AppUtils.getAppication();
    private static WindowManager mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, mContext.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxVal) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * 得到的屏幕的宽度
     */
    public static int getWidthPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.widthPixels;
    }

    /**
     * 得到的屏幕的高度
     */
    public static int getHeightPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.heightPixels;
    }

    /**
     * 得到屏幕的dpi
     *
     * @return
     */
    public static int getDensityDpi() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        mWindowManager.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        return displaysMetrics.densityDpi;
    }

    /**
     * 返回状态栏/通知栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getColumnWidth(int columnNumber) {
        return (getWidthPx() - dip2px(24)) / columnNumber;
    }
}
