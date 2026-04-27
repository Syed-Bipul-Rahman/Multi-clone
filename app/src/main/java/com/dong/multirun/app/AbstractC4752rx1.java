package com.dong.multirun.app;

import java.util.concurrent.Callable;

public abstract class AbstractC4752rx1 {
    public static void c(Object executor, Callable<?> callable) {
        if (callable != null) {
            try {
                callable.call();
            } catch (Exception ignored) {}
        }
    }
}
