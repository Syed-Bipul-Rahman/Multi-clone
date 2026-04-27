package com.dong.multirun.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import dalvik.system.DexClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractC3600lG1 {

    private static final String TAG = "CloneLoader";

    public static int a;
    public static Method b;  // Set externally by AbstractC6070zc or equivalent init class
    public static ClassLoader c;

    // -------------------------------------------------------------------------
    // Bundle bridge — used to communicate with the virtual engine
    // -------------------------------------------------------------------------

    public static Bundle b(String str, String str2, Bundle bundle) {
        // FIX: Guard against null Method before invoking
        if (b == null) {
            Log.e(TAG, "b(Method) is null — reflection bridge not initialised yet!");
            Log.e(TAG, "AbstractC6070zc.init() or equivalent must be called first.");
            Log.e(TAG, "Called for key=" + str);
            // Return a failure bundle instead of crashing with NPE
            Bundle failure = new Bundle();
            failure.putBoolean("success", false);
            failure.putString("error", "Bridge method not initialised");
            return failure;
        }
        try {
            Bundle result = (Bundle) b.invoke(null, 10402, new Object[]{str, str2, bundle});
            if (result == null) {
                Log.w(TAG, "Bridge returned null bundle for key=" + str);
                return Bundle.EMPTY;
            }
            return result;
        } catch (Throwable th) {
            Log.e(TAG, "Bridge bundle call failed for key=" + str, th);
            return Bundle.EMPTY;
        }
    }

    // -------------------------------------------------------------------------
    // ClassLoader builder
    // -------------------------------------------------------------------------

    public static ClassLoader c(Context context) {
        if (context == null) {
            Log.e(TAG, "context is null — cannot build ClassLoader");
            return AbstractC3600lG1.class.getClassLoader();
        }

        String strB = Gw1.b("saNSoOJU7A==\n", "xNYmjoMkhz0=\n");
        if (TextUtils.isEmpty(strB)) {
            Log.e(TAG, "String decoding failed for base path key — aborting clone setup");
            return context.getClassLoader();
        }

        int i = a;

        if (AbstractC1672a7.g(context)) {
            try {
                Class.forName(
                        Gw1.b("S1LXJQckR1AGUM9nFyJbQkYT410x\n", "KD26C2NLKTc=\n"),
                        true,
                        AbstractC1672a7.class.getClassLoader()
                );
                Log.d(TAG, "Clone class found — reusing existing ClassLoader");
                return AbstractC1672a7.class.getClassLoader();
            } catch (Throwable ignored) {
            }
        }

        File baseDir      = new File(context.getFilesDir().getParentFile(),
                Gw1.b("HMdsGA==\n", "dL0OYQAob6k=\n"));
        File cacheJsonFile = new File(baseDir,
                strB.concat(Gw1.b("tCPNLLo=\n", "mkm+Q9QTvd8=\n")));
        File extractDir   = new File(baseDir,
                Gw1.b("5WM5BQ==\n", "lgdSKqsrp+M=\n") + i);

        JSONObject cacheJson   = loadCacheJson(cacheJsonFile);
        String     cachedPath  = cacheJson.optString(Gw1.b("63ZizA==\n", "mxcWpDPRv8g=\n"));
        int        cachedVer   = cacheJson.optInt(Gw1.b("wsXv\n", "tKCdwDnGgec=\n"), 0);
        long       cachedMtime = cacheJson.optLong(Gw1.b("4Bl9UQ==\n", "lHAQNI0nRFA=\n"));

        ApplicationInfo appInfo = resolveApplicationInfo(context);
        if (appInfo == null) {
            Log.e(TAG, "Could not resolve ApplicationInfo — aborting");
            return context.getClassLoader();
        }

        File apkFile = new File(appInfo.sourceDir);
        if (!apkFile.exists() || !apkFile.canRead()) {
            Log.e(TAG, "APK not accessible (update in progress?): " + appInfo.sourceDir);
            return context.getClassLoader();
        }

        long apkLastModified = apkFile.lastModified();

        boolean needsExtraction = (cachedVer != i)
                || TextUtils.isEmpty(cachedPath)
                || (cachedMtime != apkLastModified);

        if (needsExtraction) {
            Log.i(TAG, "DEX extraction required (ver=" + i
                    + ", cachedVer=" + cachedVer
                    + ", apkChanged=" + (cachedMtime != apkLastModified) + ")");

            boolean extracted = extractDex(
                    context, appInfo, cacheJson, cacheJsonFile,
                    extractDir, cachedPath, apkLastModified, i
            );

            if (!extracted) {
                Log.e(TAG, "DEX extraction failed — falling back to context ClassLoader");
                return context.getClassLoader();
            }
        }

        return buildDexClassLoader(context, extractDir);
    }

    // -------------------------------------------------------------------------
    // Intent bridge
    // -------------------------------------------------------------------------

    public static void d(Context context, Intent intent, int i) {
        if (b == null) {
            Log.e(TAG, "d(): Method b is null — bridge not initialised, cannot launch clone");
            return;
        }
        try {
            ((Boolean) b.invoke(null, 10001, new Object[]{context, intent, Integer.valueOf(i)}))
                    .getClass();
        } catch (Throwable th) {
            Log.e(TAG, "Bridge intent call failed", th);
        }
    }

    // =========================================================================
    // Private helpers
    // =========================================================================

    private static JSONObject loadCacheJson(File cacheJsonFile) {
        if (!cacheJsonFile.exists()) {
            Log.d(TAG, "No cache JSON found at: " + cacheJsonFile.getPath());
            return new JSONObject();
        }
        try (FileInputStream fis = new FileInputStream(cacheJsonFile)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AbstractC1672a7.b(fis, baos);
            String json = baos.toString(StandardCharsets.UTF_8.name());
            if (TextUtils.isEmpty(json)) {
                Log.w(TAG, "Cache JSON is empty");
                return new JSONObject();
            }
            return new JSONObject(json);
        } catch (IOException e) {
            Log.w(TAG, "Failed to read cache JSON: " + e.getMessage());
        } catch (JSONException e) {
            Log.w(TAG, "Malformed cache JSON: " + e.getMessage());
        }
        return new JSONObject();
    }

    private static ApplicationInfo resolveApplicationInfo(Context context) {
        if (AbstractC1672a7.g(context)) {
            try {
                String hostPkg = Gw1.b("WoCYN1zf+NIXgoB1TNnkwFc=\n", "Oe/1GTiwlrU=\n");
                if (!TextUtils.isEmpty(hostPkg)) {
                    return context.getPackageManager().getApplicationInfo(hostPkg, 0);
                }
            } catch (Exception e) {
                Log.w(TAG, "Could not get host ApplicationInfo, falling back: " + e.getMessage());
            }
        }
        return context.getApplicationInfo();
    }

    private static boolean extractDex(
            Context context,
            ApplicationInfo appInfo,
            JSONObject cacheJson,
            File cacheJsonFile,
            File extractDir,
            String oldCachedPath,
            long apkLastModified,
            int version
    ) {
        if (!TextUtils.isEmpty(oldCachedPath)) {
            AbstractC1672a7.e(new File(oldCachedPath));
            Log.d(TAG, "Deleted old extraction: " + oldCachedPath);
        }

        File dexFile = new File(extractDir, Gw1.b("tS4uaFkc118=\n", "109dDXd9pzQ=\n"));

        try {
            if (AbstractC1672a7.g(context)) {
                Log.d(TAG, "Extracting DEX from host APK");
                dexFile.getParentFile().mkdirs();
                AbstractC1672a7.c(appInfo, dexFile);
            } else {
                String assetName = Gw1.b("5l/G8Pi3wg==\n", "kyqy3pnHqcQ=\n");
                if (TextUtils.isEmpty(assetName)) {
                    Log.e(TAG, "Asset name decode failed");
                    return false;
                }
                Log.d(TAG, "Extracting DEX from assets: " + assetName);
                dexFile.getParentFile().mkdirs();
                AbstractC1672a7.d(
                        context.getAssets().open(assetName),
                        new FileOutputStream(dexFile)
                );
            }

            AbstractC1672a7.f(dexFile,
                    Gw1.b("6dTnVQ==\n", "hb2FelWkfKg=\n"),
                    extractDir);

            if (Build.VERSION.SDK_INT >= 34) {
                dexFile.setReadOnly();
            }

            File finalDex = new File(extractDir,
                    Gw1.b("qEEVG0hcZww=\n", "yiBmfmY9F2c=\n"));
            if (!finalDex.exists() || finalDex.length() == 0) {
                Log.e(TAG, "Extracted DEX missing or empty: " + finalDex.getPath());
                return false;
            }

            try {
                cacheJson.putOpt(Gw1.b("GOLpfQ==\n", "aIOdFZlgFtM=\n"), extractDir.getPath());
                cacheJson.putOpt(Gw1.b("hDzKZA==\n", "8FWnAeR3tfs=\n"), apkLastModified);
                cacheJson.putOpt(Gw1.b("rwzx\n", "2WmDjOWkje0=\n"), version);
                writeCacheJson(cacheJsonFile, cacheJson.toString());
            } catch (JSONException e) {
                Log.e(TAG, "Failed to update cache JSON — will re-extract next launch", e);
            }

            Log.i(TAG, "DEX extraction succeeded: " + extractDir.getPath());
            return true;

        } catch (Throwable th) {
            Log.e(TAG, "DEX extraction threw an exception", th);
            return false;
        }
    }

    private static void writeCacheJson(File cacheJsonFile, String json) {
        try {
            cacheJsonFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(cacheJsonFile)) {
                fos.write(json.getBytes(StandardCharsets.UTF_8));
                AbstractC1672a7.a(fos);
            }
            Log.d(TAG, "Cache JSON written: " + cacheJsonFile.getPath());
        } catch (Throwable th) {
            Log.e(TAG, "Failed to write cache JSON — will re-extract on next launch", th);
        }
    }

    private static ClassLoader buildDexClassLoader(Context context, File extractDir) {
        File dexFile = new File(extractDir,
                Gw1.b("qEEVG0hcZww=\n", "yiBmfmY9F2c=\n"));
        File libDir  = new File(extractDir,
                Gw1.b("TFTE\n", "ID2mmvwOAlA=\n"));

        if (!dexFile.exists() || dexFile.length() == 0) {
            Log.e(TAG, "DEX file missing at build time: " + dexFile.getPath()
                    + " — falling back to context ClassLoader");
            return context.getClassLoader();
        }

        ArrayList<String> libPaths = new ArrayList<>();
        String[] abis = Process.is64Bit()
                ? Build.SUPPORTED_64_BIT_ABIS
                : Build.SUPPORTED_32_BIT_ABIS;

        for (String abi : abis) {
            File abiDir = new File(libDir, abi);
            if (abiDir.exists()) {
                libPaths.add(abiDir.getAbsolutePath());
                Log.d(TAG, "Native lib path added: " + abiDir.getAbsolutePath());
            }
        }

        if (libPaths.isEmpty()) {
            Log.w(TAG, "No native library directories found — clone may fail if native libs required");
        }

        String nativeLibPath = TextUtils.join(File.pathSeparator, libPaths);

        Log.i(TAG, "Creating DexClassLoader:"
                + "\n  dex=" + dexFile.getPath()
                + "\n  libs=" + nativeLibPath);

        return new DexClassLoader(
                dexFile.getPath(),
                dexFile.getParent(),
                nativeLibPath.isEmpty() ? null : nativeLibPath,
                context.getClassLoader()  // FIX: app ClassLoader, not boot ClassLoader
        );
    }
}