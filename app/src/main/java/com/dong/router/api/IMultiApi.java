package com.dong.router.api;

import android.graphics.Bitmap;
import com.dong.multirun.app.C0473Hg0;
import com.dong.multirun.app.C0627Js;
import com.dong.multirun.app.K8;
import java.util.List;

public interface IMultiApi {
    List<K8> allAppSpaceList();
    int clearPackageCache(int userId, String packageName);
    boolean createShortcut(String packageName, String name, Bitmap icon, int userId);
    int deletePackageData(int userId, String packageName);
    List<C0627Js> deviceAppList();
    C0473Hg0 getDeviceInfo(String pkg, int space);
    int installPackageFromHost(int userId, String pkgName);
    boolean killApplication(String pkg, int userId, String reason);
    int launch(String packageName, int userId);
    boolean removeDeviceInfo(String pkg, int space);
    boolean resetDeviceInfo(String pkg, int space);
    int uninstallPackage(int userId, String pkgName);
}
