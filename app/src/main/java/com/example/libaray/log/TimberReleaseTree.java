package com.example.libaray.log;

import android.util.Log;

public class TimberReleaseTree extends Timber.Tree {

    private static final int MAX_LOG_LENGTH = 4000;

    @Override
    protected boolean isLoggable(int priority) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return false;
        }

        //only log WARN, ERROR, WTF
        return true;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (isLoggable(priority)) {

            if (priority == Log.ERROR && t != null) {
                //TODO 上报？
            }

            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }
        }

        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part);
                } else {
                    Log.println(priority, tag, part);
                }
                i = end;
            } while (i < newline);
        }
    }
}
