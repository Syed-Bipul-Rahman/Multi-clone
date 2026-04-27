package com.dong.multirun.app;

public class BL {
    public final Class<?> clazz;

    public BL(Class<?> c) {
        this.clazz = c;
    }

    public String b() {
        return clazz.getName();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof BL) && clazz.equals(((BL) o).clazz);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }
}
