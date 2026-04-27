package com.dong.multirun.app;

import java.util.LinkedHashMap;

public abstract class SR {
    public static final LinkedHashMap a = new LinkedHashMap();

    @SuppressWarnings("unchecked")
    public static Object a(BL bl) {
        NI.e(bl, "interfaceClazz");
        Class<?> impl = (Class<?>) a.get(bl);
        if (impl == null) {
            throw new IllegalArgumentException("Not registered: " + bl.b());
        }
        try {
            return impl.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate: " + impl.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void b(BL iface, BL impl) {
        NI.e(iface, "interfaceClazz");
        NI.e(impl, "implementationClazz");
        a.put(iface, impl.clazz);
    }
}
