package com.example.libaray.utils;

import android.app.Application;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {

    public static void show(Application app, int resId) {
        show(app, resId, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(Application app, final int resId, final int duration) {
        String text = app.getString(resId);
        show(text, duration);
    }

    public static void show(final CharSequence text, final int duration) {
        AppUtils.getMainHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppUtils.getAppication(), text, duration).show();
                    }
                });
    }

    public static void show(Application app, int resId, Object... args) {
        show(String.format(app.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Application app, int resId, int duration, Object... args) {
        show(String.format(app.getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    public static void showInCenter(Application app, int resId) {
        showInCenter(app,app.getResources().getString(resId));
    }

    public static void showInCenter(Application app, final String string) {
        AppUtils.getMainHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(AppUtils.getAppication(), string, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
    }

    public static void DebugToast(int resId) {
        if (DebugUtil.isDebuggable()) {
            DebugToast(AppUtils.getAppication(), AppUtils.getAppication().getString(resId));
        }
    }

    public static void DebugToast(Application app, String text) {
        if (DebugUtil.isDebuggable()) {
            show("debug模式: \r\n " + text);
        }
    }

    /**
     * 自定义Toast
     */
    public static void showCustomToast(final Application app, final int layoutId, final int textViewId, final String content) {
        AppUtils.getMainHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = createCustomToast(layoutId, textViewId, content, 0, null);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }


    public static void showCenterCustomToast(final int layoutId, final int textViewId,
                                             final String content) {
        AppUtils.getMainHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = createCustomToast(layoutId, textViewId, content, 0, null);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
    }

    public static void showCenterCustomToastWithTilte(final int layoutId, final int textViewId,
                                                      final String content, final int TitleTvId, final String title) {
        AppUtils.getMainHandler().post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = createCustomToast(layoutId, textViewId, content, TitleTvId, title);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
    }

    private static Toast createCustomToast(int layoutId, int contentTvId, String content, int titleTvId, String title) {
        Toast toast = new Toast(AppUtils.getAppication());
        View layout = View.inflate(AppUtils.getAppication(), layoutId, null);
        if (title != null) {
            TextView titleTV = (TextView) layout.findViewById(titleTvId);
            titleTV.setText(title);
        }
        TextView contentTv = (TextView) layout.findViewById(contentTvId);
        contentTv.setText(content);
        toast.setView(layout);
        return toast;
    }

    /**
     * 样式不变,toast的位置展示在正中心
     */
    public static void showCenterToast(Application app, String content) {
        Toast toast = Toast.makeText(app, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
