package com.dong.multirun.app;

import android.util.Log;

public abstract class AbstractC3080iF1 {
    private static final String TAG = "CLONE_DEBUG";

    public static final boolean a(int i, int i2) {
        return i == i2;
    }

    public static int b(int i, String str) {
        Log.d(TAG, "b() called: userId=" + i + " pkg=" + str);
        if (VT.b0 == null) {
            Log.e(TAG, "b() FAIL: VT.b0 is null");
            return -1;
        }
        try {
            int result = VT.b0.a1(2, i, str);
            Log.d(TAG, "b() result=" + result + " for pkg=" + str);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "b() EXCEPTION for pkg=" + str, e);
            return -1;
        }
    }
}
