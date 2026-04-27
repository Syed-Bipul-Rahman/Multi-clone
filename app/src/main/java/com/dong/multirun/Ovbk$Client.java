package com.dong.multirun;

import android.os.Bundle;
import android.text.TextUtils;
import com.dong.multirun.app.AbstractC0834Nb;
import com.dong.multirun.app.C1077Qx;
import com.dong.multirun.app.Gw1;

/* JADX INFO: loaded from: classes.dex */
public class Ovbk$Client extends AbstractC0834Nb {
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        if (!TextUtils.equals(str, Gw1.b("+Jv9wA==\n", "jvaFt5aPbls=\n")) || bundle == null) {
            return Bundle.EMPTY;
        }
        int i = bundle.getInt(Gw1.b("wQNS\n", "pnI2ecepQbQ=\n"), -1);
        bundle.setClassLoader(Ovbk$Client.class.getClassLoader());
        return C1077Qx.b.a(getContext(), i, bundle);
    }
}
