package com.dong.multirun.app;

import java.lang.Thread;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public final class TQ extends ConcurrentHashMap implements InvocationHandler, Thread.UncaughtExceptionHandler {
    public static final TQ Y = new TQ();
    public final ConcurrentHashMap X = new ConcurrentHashMap();

    @Override
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        if (((M8) this.X.get(obj)) != null) {
            ((Integer) obj).getClass();
            switch (((Integer) objArr[0]).intValue()) {
                case 100:
                    Object obj2 = objArr[1];
                    break;
                case 101:
                    break;
                case 102:
                    break;
                case 103:
                    break;
            }
        }
        return null;
    }

    @Override
    public final void uncaughtException(Thread thread, Throwable th) {
    }
}
