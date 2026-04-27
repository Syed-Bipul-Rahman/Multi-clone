package com.tencent.mmkv;

import android.content.Context;
import android.util.Log;

public class MMKV {
    private static final String TAG = "MMKV";

    public static String i(Context context) {
        Log.d(TAG, "MMKV.i() stub called");
        return context.getFilesDir().getAbsolutePath();
    }

    public static MMKV defaultMMKV() {
        return new MMKV();
    }

    public boolean encode(String key, String value) { return true; }
    public boolean encode(String key, int value) { return true; }
    public boolean encode(String key, long value) { return true; }
    public boolean encode(String key, boolean value) { return true; }

    public String decodeString(String key, String defaultValue) { return defaultValue; }
    public int decodeInt(String key, int defaultValue) { return defaultValue; }
    public long decodeLong(String key, long defaultValue) { return defaultValue; }
    public boolean decodeBool(String key, boolean defaultValue) { return defaultValue; }

    public boolean containsKey(String key) { return false; }
    public void removeValueForKey(String key) {}
    public void clearAll() {}
}
