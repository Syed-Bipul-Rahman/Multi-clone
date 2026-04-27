package com.dong.multirun.app;

import java.nio.charset.StandardCharsets;

public abstract class Gw1 {
    public static final boolean a(int i, int i2) {
        return i == i2;
    }

    public static String b(String str, String str2) {
        byte[] bArrA = Dw1.a(str);
        byte[] bArrA2 = Dw1.a(str2);
        int i = AbstractC6070zc.a;
        int length = bArrA.length;
        int length2 = bArrA2.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            if (i3 >= length2) {
                i3 = 0;
            }
            bArrA[i2] = (byte) ((bArrA[i2] ^ bArrA2[i3]) | AbstractC6070zc.a);
            i2++;
            i3++;
        }
        return new String(bArrA, StandardCharsets.UTF_8);
    }
}
