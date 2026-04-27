package com.dong.multirun.app;

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedHashMap;

public final class B30 {
    private static final String TAG = "B30";
    public static final LinkedHashMap d = new LinkedHashMap();
    public final Context a;
    public final String b;
    public ContentProviderClient c = null;

    public B30(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final synchronized Bundle a(String str, Bundle bundle) {
        for (int retries = 5; retries > 0; retries--) {
            try {
                if (this.c == null) {
                    this.c = this.a.getContentResolver()
                            .acquireUnstableContentProviderClient(this.b);
                }
                if (this.c == null) {
                    if (retries > 1) {
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                        continue;
                    }
                    return null;
                }
                return this.c.call(str, null, bundle);
            } catch (Throwable th) {
                Log.e(TAG, "call failed for: " + this.b, th);
                if (this.c != null) {
                    this.c.release();
                    this.c = null;
                }
                if (retries <= 1) return null;
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }
        return null;
    }
}
