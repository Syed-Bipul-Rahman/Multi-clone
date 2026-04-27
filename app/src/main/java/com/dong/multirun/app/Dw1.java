package com.dong.multirun.app;

import android.util.Base64;

public abstract class Dw1 {
    public static byte[] a(String str) {
        return Base64.decode(str.trim(), Base64.DEFAULT);
    }

    public static final boolean b(int i, int i2) {
        return i == i2;
    }
}
