package com.dong.multirun.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public final class H8 {
    private static final String TAG = "H8";
    public static H8 h;
    public final boolean c; // true = we are in the main app process
    private final Application app;
    public volatile PackageInfo e;

    public H8(Application application) {
        this.app = application;
        String processName = getProcessName();
        String appProcess = application.getApplicationInfo().processName;
        if (appProcess == null || processName == null) {
            this.c = true;
        } else {
            this.c = appProcess.equals(processName);
        }
        Log.d(TAG, "H8 init: appProcess=" + appProcess + " thisProcess=" + processName + " isMain=" + this.c);
    }

    private static String getProcessName() {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        try {
            String path = "/proc/" + android.os.Process.myPid() + "/cmdline";
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.InputStreamReader(new java.io.FileInputStream(path),
                            java.nio.charset.StandardCharsets.ISO_8859_1));
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = br.read()) > 0) {
                sb.append((char) ch);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            Log.w(TAG, "getProcessName failed: " + e.getMessage());
            return null;
        }
    }

    public static PackageInfo a() {
        H8 h8 = h;
        if (h8 == null || h8.app == null) return null;
        if (h8.e == null) {
            synchronized (H8.class) {
                if (h8.e != null) return h8.e;
                try {
                    h8.e = h8.app.getPackageManager()
                            .getPackageInfo(h8.app.getPackageName(), 128);
                } catch (PackageManager.NameNotFoundException ignored) {}
            }
        }
        return h8.e;
    }

    public final void b() {
        // Stub — lifecycle registration not needed for engine bootstrap
    }
}
