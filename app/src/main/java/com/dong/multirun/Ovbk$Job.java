package com.dong.multirun;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.dong.multirun.app.AbstractC3600lG1;

/* JADX INFO: loaded from: classes.dex */
public class Ovbk$Job extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        try {
            return (IBinder) AbstractC3600lG1.b.invoke(null, 10103, this, intent);
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        try {
            AbstractC3600lG1.b.invoke(null, 10101, this);
        } catch (Throwable unused) {
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        try {
            AbstractC3600lG1.b.invoke(null, 10105, this);
        } catch (Throwable unused) {
        }
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        try {
            AbstractC3600lG1.b.invoke(null, 10106, this, intent);
        } catch (Throwable unused) {
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        try {
            return ((Integer) AbstractC3600lG1.b.invoke(null, 10102, this, intent, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
        } catch (Throwable unused) {
            return 2;
        }
    }

    @Override // android.app.Service
    public final void onTaskRemoved(Intent intent) {
        try {
            AbstractC3600lG1.b.invoke(null, 10107, this, intent);
        } catch (Throwable unused) {
        }
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        try {
            return ((Boolean) AbstractC3600lG1.b.invoke(null, 10104, this, intent)).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }
}
