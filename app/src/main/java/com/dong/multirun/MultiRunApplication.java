package com.dong.multirun;

import android.app.Application;
import android.content.Context;
import com.dong.multirun.app.AbstractC3600lG1;
import com.dong.multirun.app.AbstractC6070zc;

//public class MultiRunApplication extends Application {
//
//    @Override
//    public void attachBaseContext(Context context) {
//        super.attachBaseContext(context);
//        AbstractC3600lG1.a = 36;
//        AbstractC6070zc.a = 0;
//    }
//}


import android.content.pm.ApplicationInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.a;
import com.dong.multirun.app.AG1;
import com.dong.multirun.app.AbstractC0019Ac;
import com.dong.multirun.app.AbstractC0188Ct;
import com.dong.multirun.app.AbstractC0495Hp;
import com.dong.multirun.app.AbstractC2021c80;
import com.dong.multirun.app.AbstractC3419kE;
import com.dong.multirun.app.AbstractC3533ku1;
import com.dong.multirun.app.AbstractC3946nG1;
import com.dong.multirun.app.AbstractC4752rx1;
import com.dong.multirun.app.AbstractC5444vx1;
import com.dong.multirun.app.AbstractC5942yq1;
import com.dong.multirun.app.B30;
import com.dong.multirun.app.C0074Az;
import com.dong.multirun.app.C1015Px0;
import com.dong.multirun.app.C1325Uv;
import com.dong.multirun.app.C1622Zm;
import com.dong.multirun.app.C1845b7;
import com.dong.multirun.app.C2017c7;
import com.dong.multirun.app.C2190d7;
import com.dong.multirun.app.C2277de1;
import com.dong.multirun.app.C2291dj0;
import com.dong.multirun.app.C3329jk0;
import com.dong.multirun.app.C3459kU;
import com.dong.multirun.app.C4770s30;
import com.dong.multirun.app.C4785s80;
import com.dong.multirun.app.C4896so;
import com.dong.multirun.app.C5102tz;
import com.dong.multirun.app.C6128zv0;
import com.dong.multirun.app.C6139zz;
import com.dong.multirun.app.CallableC1181Sm;
import com.dong.multirun.app.Gw1;
import com.dong.multirun.app.H8;
import com.dong.multirun.app.M8;
import com.dong.multirun.app.NI;
import com.dong.multirun.app.Rs1;
import com.dong.multirun.app.SR;
import com.dong.multirun.app.TQ;
import com.dong.multirun.app.VT;
import com.dong.multirun.app.VU;
import com.dong.multirun.app.XZ;
import com.dong.multirun.app.virtual.MutiImpl;
import com.dong.multiui.subscription.SubscriptionManagerProvider;
import com.dong.multiui.util.ConfigImpl;
import com.dong.multiui.util.PointDataManager;
import com.dong.router.api.IConfig;
import com.dong.router.api.IMultiApi;
import com.dong.router.api.IPointData;
import com.dong.router.api.ISubscriptionManagerProvider;
import com.tencent.mmkv.MMKV;

import java.io.InputStream;
import java.util.LinkedHashMap;

import kotlin.Metadata;

public final class MultiRunApplication extends Application {

    private static final String TAG = "MultiRunApplication";

    public static final /* synthetic */ int Y = 0;
    public final C4896so X = AG1.a(AbstractC3946nG1.c(new C2291dj0(null), AbstractC0188Ct.b));

    // FIX: Expose the clone ClassLoader so CloneEngine can access it
    private static ClassLoader sCloneClassLoader = null;

    public static ClassLoader getCloneClassLoader() {
        return sCloneClassLoader;
    }

    @Override
    public final void attachBaseContext(Context context) {
        // Pre-populate subscription prefs so the engine's C0856Ni0 (reads
        // "multirun_subscription" / "is_premium") sees premium=true before the
        // :service process constructs it, bypassing the update-nag gate.
        context.getSharedPreferences("multirun_subscription", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("is_premium", true)
                .putLong("temporary_end_time", Long.MAX_VALUE / 2)
                .commit();

        // Step 1: NI.e() MUST run first — this is the real engine bootstrap.
        // It loads the virtual engine DEX and sets AbstractC3600lG1.b (the Method).
        // ORDER IS CRITICAL — do not move anything above this call.
        NI.e(context, AbstractC0019Ac.a(
                new byte[]{-82, -92, 93, 96},
                new byte[]{-52, -59, 46, 5, -23, 43, -53, -3}
        ));

        super.attachBaseContext(context);

        // Step 2: Set static fields AFTER super.attachBaseContext
        AbstractC0019Ac.a = 0;
        AbstractC3600lG1.a = 36;
        AbstractC6070zc.a = 0;

        // Step 3: Build the clone ClassLoader and store it
        // FIX: This call was missing from the original MultiRunApplication stub.
        // AbstractC3600lG1.b should now be set by NI.e() above, so c(context)
        // can complete successfully.
        try {
            sCloneClassLoader = AbstractC3600lG1.c(context);
            // Also store in the static field AbstractC3600lG1.c (the field, not the method)
            AbstractC3600lG1.c = sCloneClassLoader;
            if (sCloneClassLoader != null) {
                Log.i(TAG, "Clone ClassLoader ready: "
                        + sCloneClassLoader.getClass().getSimpleName());
            } else {
                Log.e(TAG, "AbstractC3600lG1.c() returned null — cloning will not work");
            }
        } catch (Throwable th) {
            Log.e(TAG, "Failed to build clone ClassLoader", th);
        }

        // Step 4: Virtual engine service and version bridge setup (original code)
        VT.c0 = new B30(context, "com.dong.multirun.com.dong.multirun.Ovbk$Service");
        VT.b0 = new VT(
                Gw1.b("HLaX7fWNiCZRtI+v5YuUNBH3ibLykIQvUamPrb+6qTEMo7ex4g==\n",
                        "f9n6w5Hi5kE=\n"),
                22
        );

        // Step 5: Verify Method b was actually set by NI.e()
        if (AbstractC3600lG1.b == null) {
            Log.e(TAG, "AbstractC3600lG1.b is STILL null after NI.e() — "
                    + "engine bootstrap failed. NI.e() did not set the bridge Method.");
        } else {
            Log.i(TAG, "AbstractC3600lG1.b is set: "
                    + AbstractC3600lG1.b.getDeclaringClass().getName()
                    + "#" + AbstractC3600lG1.b.getName());
        }
    }

    @Override
    public final void onCreate() {
        int i  = 0;
        int i2 = 21;
        super.onCreate();

        AbstractC3419kE.a = this;

        synchronized (H8.class) {
            H8 h8 = new H8(this);
            H8.h = h8;
            if (h8.c) {
                h8.b();
            }
        }

        if (!H8.h.c) {
            getSharedPreferences(AbstractC0019Ac.a(
                    new byte[]{29, 92, 103, 105},
                    new byte[]{112, 61, 23, 26, -66, 91, -56, 72}
            ), 0);
            M8 m8 = new M8();
            TQ tq = TQ.Y;
            tq.X.put(100, m8);
            String strA = AbstractC0019Ac.a(
                    new byte[]{63, 57, -55},
                    new byte[]{126, 105, -103, -65, -84, -39, 52, -104}
            );
            Object obj = tq.get(Gw1.b("mWBEpS8yLJ20SHGHAEM5hr1I\n", "+xgrykccaM4=\n"));
            if (((Integer) (obj != null ? obj : 0)).intValue() == 0) {
                Thread.setDefaultUncaughtExceptionHandler(new C6128zv0(strA));
            }
            return;
        }

        MMKV.i(this);

        LinkedHashMap linkedHashMap = SR.a;
        SR.b(AbstractC0495Hp.f(IMultiApi.class),               AbstractC0495Hp.f(MutiImpl.class));
        SR.b(AbstractC0495Hp.f(IPointData.class),              AbstractC0495Hp.f(PointDataManager.class));
        SR.b(AbstractC0495Hp.f(IConfig.class),                 AbstractC0495Hp.f(ConfigImpl.class));
        SR.b(AbstractC0495Hp.f(ISubscriptionManagerProvider.class),
                AbstractC0495Hp.f(SubscriptionManagerProvider.class));

        AppCompatDelegate.setDefaultNightMode(
                ((IConfig) SR.a(AbstractC2021c80.a.b(IConfig.class))).getNightMode()
        );

        AbstractC5444vx1.b(this.X, null, 0, new C2017c7(this, null), 3);

        C4785s80 c4785s80 = (C4785s80) a.a(this).Z.a.get();
        c4785s80.c(ApplicationInfo.class, Drawable.class,          new VU(i2, this));
        c4785s80.c(ApplicationInfo.class, InputStream.class,       new C1015Px0(i2, this));
        c4785s80.c(ApplicationInfo.class, AssetFileDescriptor.class, new C3459kU(this));
        c4785s80.f(Drawable.class, new C2277de1(i2));
        c4785s80.f(Bitmap.class,   new C1325Uv(i2));

        C3329jk0 c3329jk0 = C4770s30.b;
        AbstractC5942yq1.a().f(new C2190d7());
        AbstractC3533ku1.b(this);

        XZ.c.b = new C3459kU();

        C5102tz c5102tzA = Rs1.a();
        C1845b7 c1845b7 = new C1845b7(i);
        C6139zz c6139zz = new C6139zz();
        c6139zz.a = C1622Zm.i;
        c1845b7.k(c6139zz);
        C0074Az c0074Az = new C0074Az();
        c0074Az.a = c6139zz.a;
        AbstractC4752rx1.c(c5102tzA.b, new CallableC1181Sm(1, c5102tzA, c0074Az));
    }
}