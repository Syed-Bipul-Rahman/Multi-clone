package com.dong.multirun;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.dong.multirun.app.AbstractC3600lG1;

/* JADX INFO: loaded from: classes.dex */
public class Ovbk$PS extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        AbstractC3600lG1.d(this, intent, 4);
        stopSelf();
        return 2;
    }
}
