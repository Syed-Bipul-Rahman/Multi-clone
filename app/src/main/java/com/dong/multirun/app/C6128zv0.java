package com.dong.multirun.app;

import android.util.Log;

public final class C6128zv0 implements Thread.UncaughtExceptionHandler {
    private final String tag;

    public C6128zv0(String tag) {
        this.tag = tag;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable th) {
        Log.e(tag, "Uncaught exception in " + thread.getName(), th);
    }
}
