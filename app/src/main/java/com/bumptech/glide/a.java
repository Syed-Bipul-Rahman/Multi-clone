package com.bumptech.glide;

import android.content.Context;
import com.dong.multirun.app.C4785s80;
import java.util.concurrent.atomic.AtomicReference;

public class a {
    private static final a INSTANCE = new a();
    public final ZField Z = new ZField();

    private a() {}

    public static a a(Context context) {
        return INSTANCE;
    }

    public static class ZField {
        public final AtomicReference<C4785s80> a = new AtomicReference<>(new C4785s80());
    }
}
