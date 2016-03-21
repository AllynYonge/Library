package com.example.libaray.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.example.libaray.log.Timber;
import com.example.libaray.log.TimberReleaseTree;

import java.util.List;


/**
 * Created by AllynYonge on 3/18/16.
 */
public class AppUtils {
    private static Application mAppication;
    private static Handler mHandler;

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]<BR>
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e,e.toString());
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]<BR>
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e,e.toString());
        }
        return 0;
    }

    /**
     * need < uses-permission android:name =“android.permission.GET_TASKS” />
     * 判断是否前台运行
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     *
     * @param context
     * @return if application is in background return true, otherwise return false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether this process is named with processName
     * 检查当前运行的进程是否与你传入的进程名一致
     *
     * @param context
     * @param processName
     * @return <ul>
     * return whether this process is named with processName
     * <li>if context is null, return false</li>
     * <li>if {@link android.app.ActivityManager#getRunningAppProcesses()} is null, return false</li>
     * <li>if one process of {@link android.app.ActivityManager#getRunningAppProcesses()} is equal to processName, return
     * true, otherwise return false</li>
     * </ul>
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null) {
            return true;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid && processName.equals(processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 记住要在Application的onCreate()回调函数里面调用，初始化后，可以让其他的Utils不依赖于Context
     * @param application
     * @return Application实例
     */
    public static void setApplication(Application application){
        mAppication = application;
        mHandler = new Handler(mAppication.getMainLooper());

        //初始化Timber日志
        if (DebugUtil.isDebuggable()) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            Timber.plant(new TimberReleaseTree());
        }
    }

    /**
     * 得到Application
     * @return
     */
    public static Application getAppication(){
        if (mAppication == null){
            throw new RuntimeException("请在Application的onCreate()回调函数里初始化ApplicationUtils中的Application对象");
        }
        return mAppication;
    }

    /**
     * 得到主线程的Handler
     * @return Handler
     */
    public static Handler getMainHandler(){
        if (mHandler == null){
            throw new RuntimeException("请在Application的onCreate()回调函数里初始化ApplicationUtils中的Application对象");
        }
        return mHandler;
    }
}
