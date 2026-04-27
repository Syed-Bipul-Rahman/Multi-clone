package com.dong.multirun.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

public final class C0627Js implements Comparable {
    public PackageInfo X;
    public String Y;
    public String Z;
    public ApplicationInfo applicationInfo;

    @Override
    public int compareTo(Object obj) {
        C0627Js other = (C0627Js) obj;
        if (this.Y == null || other.Y == null) return 0;
        return this.Y.compareTo(other.Y);
    }
}
