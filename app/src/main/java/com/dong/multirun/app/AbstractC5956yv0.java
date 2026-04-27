package com.dong.multirun.app;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: com.dong.multirun.app.yv0 */
public abstract class AbstractC5956yv0 extends AbstractC0834Nb {
    @Override
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        try {
            AbstractC3600lG1.c = AbstractC3600lG1.c(context);
            Method declaredMethod = Class.forName(Gw1.b("FnpnDAQV9ExbeH9OFBPoXhs7U3Qy\n", "dRUKImB6mis=\n"), true, AbstractC3600lG1.c).getDeclaredMethod(Gw1.b("2bs+EA==\n", "uM5aZhgSmTo=\n"), Integer.TYPE, Object[].class);
            AbstractC3600lG1.b = declaredMethod;
            declaredMethod.invoke(null, 1000, new Object[]{context, providerInfo, -1, TQ.Y, (byte) 0});
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override
    public final Bundle call(String str, String str2, Bundle bundle) {
        try {
            return AbstractC3600lG1.b(str, str2, bundle);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th);
        }
    }

    @Override
    public final boolean onCreate() {
        return true;
    }

    @Override
    public final Bundle call(String str, String str2, String str3, Bundle bundle) {
        try {
            return AbstractC3600lG1.b(str2, str3, bundle);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th);
        }
    }
}
