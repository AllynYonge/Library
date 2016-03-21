package com.example.libaray.utils;

import android.os.Handler;

/**
 * @author MaTianyu
 * @date 2015-03-12
 */
public class HandlerUtil {
    public static final Handler HANDLER = AppUtils.getMainHandler();

    public static void runOnUiThread(Runnable runnable){
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis){
        HANDLER.postDelayed(runnable,delayMillis);
    }

    public static void removeRunable(Runnable runnable){
        HANDLER.removeCallbacks(runnable);
    }
}
