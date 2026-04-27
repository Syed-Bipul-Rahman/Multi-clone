package com.dong.multirun.app;

public abstract class NI {
    public static void b(Object obj) {
        if (obj == null) throw new NullPointerException();
    }

    public static void c(Object obj, String str) {
        if (obj == null) throw new NullPointerException(str);
    }

    public static void d(Object obj, String str) {
        if (obj == null) throw new NullPointerException(str + " must not be null");
    }

    public static void e(Object obj, String str) {
        if (obj == null) throw new NullPointerException("Parameter '" + str + "' must not be null");
    }
}
