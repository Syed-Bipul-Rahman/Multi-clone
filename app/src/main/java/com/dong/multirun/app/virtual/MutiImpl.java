package com.dong.multirun.app.virtual;

import android.graphics.Bitmap;
import android.util.Log;
import com.dong.multirun.app.AbstractC3080iF1;
import com.dong.multirun.app.C0473Hg0;
import com.dong.multirun.app.C0627Js;
import com.dong.multirun.app.K8;
import com.dong.multirun.app.VT;
import com.dong.router.api.IMultiApi;
import java.util.ArrayList;
import java.util.List;

public final class MutiImpl implements IMultiApi {
    private static final String TAG = "MutiImpl";

    @Override
    public List<K8> allAppSpaceList() {
        return new ArrayList<>();
    }

    @Override
    public int clearPackageCache(int userId, String packageName) {
        return callEngine(10201, userId, packageName);
    }

    @Override
    public boolean createShortcut(String packageName, String name, Bitmap icon, int userId) {
        return false;
    }

    @Override
    public int deletePackageData(int userId, String packageName) {
        return callEngine(10202, userId, packageName);
    }

    @Override
    public List<C0627Js> deviceAppList() {
        return new ArrayList<>();
    }

    @Override
    public C0473Hg0 getDeviceInfo(String pkg, int space) {
        return new C0473Hg0();
    }

    @Override
    public int installPackageFromHost(int userId, String pkgName) {
        Log.i(TAG, "installPackageFromHost userId=" + userId + " pkg=" + pkgName);
        return AbstractC3080iF1.b(userId, pkgName);
    }

    @Override
    public boolean killApplication(String pkg, int userId, String reason) {
        return false;
    }

    @Override
    public int launch(String packageName, int userId) {
        Log.i(TAG, "launch pkg=" + packageName + " userId=" + userId);
        try {
            return VT.b0.f1(userId, packageName);
        } catch (Exception e) {
            Log.e(TAG, "launch failed", e);
            return -1;
        }
    }

    @Override
    public boolean removeDeviceInfo(String pkg, int space) {
        return false;
    }

    @Override
    public boolean resetDeviceInfo(String pkg, int space) {
        return false;
    }

    @Override
    public int uninstallPackage(int userId, String pkgName) {
        try {
            return VT.b0.o1(userId, pkgName);
        } catch (Exception e) {
            Log.e(TAG, "uninstallPackage failed", e);
            return -1;
        }
    }

    private int callEngine(int code, int userId, String pkgName) {
        try {
            return VT.b0.a1(code, userId, pkgName);
        } catch (Exception e) {
            Log.e(TAG, "callEngine code=" + code + " failed", e);
            return -1;
        }
    }
}
