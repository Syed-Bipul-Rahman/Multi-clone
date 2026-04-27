package com.dong.multirun.app;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;

public class VT {
    private static final String TAG = "VT";
    private static final String DTAG = "CLONE_DEBUG";

    public static VT b0;
    public static B30 c0;

    public final int X;
    public Object Y;
    public Object Z;

    public VT(String str, int i) {
        this.X = i;
        if (i == 22) {
            this.Z = new C0067Av0(this);
            this.Y = str;
        } else {
            this.Y = str;
        }
    }

    // Called by C0067Av0.transact() to lazily fetch the engine's IBinder via ContentProvider.
    // The decoded strings ("android.intent.extra.INTENT") come from Gw1.b() with AbstractC6070zc.a==0.
    public static IBinder t0(VT vt) throws RemoteException {
        vt.getClass(); // null-check
        B30 cp = c0;
        if (cp == null) {
            Log.e(DTAG, "t0: VT.c0 is null — ContentProvider client never initialised");
            throw new RemoteException("VT.c0 (ContentProvider client) not initialised");
        }
        Log.d(DTAG, "t0: calling ContentProvider authority=" + cp.b);
        try {
            Bundle result = cp.a("android.intent.extra.INTENT", null);
            Log.d(DTAG, "t0: ContentProvider returned bundle=" + result);
            if (result != null) {
                IBinder binder = result.getBinder("android.intent.extra.INTENT");
                Log.d(DTAG, "t0: IBinder from bundle=" + binder);
                if (binder != null) return binder;
                Log.e(DTAG, "t0: bundle keys=" + result.keySet());
            } else {
                Log.e(DTAG, "t0: ContentProvider returned null bundle");
            }
        } catch (Exception e) {
            Log.e(DTAG, "t0: ContentProvider call threw exception", e);
        }
        throw new RemoteException("connect error");
    }

    // Install package from host into virtual user (transaction 6).
    // a1(type=2, userId, pkgName) -> int result (0=success)
    public int a1(int i, int i2, String str) {
        Log.d(DTAG, "a1: type=" + i + " userId=" + i2 + " pkg=" + str + " token=" + this.Y);
        Parcel p = Parcel.obtain();
        Parcel r = Parcel.obtain();
        try {
            p.writeInterfaceToken((String) this.Y);
            p.writeString(str);
            p.writeInt(i);
            p.writeInt(i2);
            boolean ok = ((C0067Av0) this.Z).transact(6, p, r, 0);
            Log.d(DTAG, "a1: transact returned=" + ok);
            r.readException();
            int result = r.readInt();
            Log.d(DTAG, "a1: result=" + result);
            return result;
        } catch (RemoteException e) {
            Log.e(DTAG, "a1: transact RemoteException", e);
            return -1;
        } catch (Exception e) {
            Log.e(DTAG, "a1: unexpected exception", e);
            return -1;
        } finally {
            r.recycle();
            p.recycle();
        }
    }

    // Launch cloned package (transaction 12).
    // f1(userId, pkgName) -> int
    public int f1(int i, String str) {
        Log.d(DTAG, "f1: userId=" + i + " pkg=" + str);
        Parcel p = Parcel.obtain();
        Parcel r = Parcel.obtain();
        try {
            p.writeInterfaceToken((String) this.Y);
            p.writeString(str);
            p.writeInt(0);
            p.writeInt(i);
            boolean ok = ((C0067Av0) this.Z).transact(12, p, r, 0);
            Log.d(DTAG, "f1: transact returned=" + ok);
            r.readException();
            int result = r.readInt();
            Log.d(DTAG, "f1: result=" + result);
            return result;
        } catch (RemoteException e) {
            Log.e(DTAG, "f1: RemoteException", e);
            return -1;
        } catch (Exception e) {
            Log.e(DTAG, "f1: unexpected exception", e);
            return -1;
        } finally {
            r.recycle();
            p.recycle();
        }
    }

    // Uninstall cloned package (transaction 7).
    // o1(userId, pkgName) -> int
    public int o1(int i, String str) {
        Parcel p = Parcel.obtain();
        Parcel r = Parcel.obtain();
        try {
            p.writeInterfaceToken((String) this.Y);
            p.writeString(str);
            p.writeInt(0);
            p.writeInt(i);
            ((C0067Av0) this.Z).transact(7, p, r, 0);
            r.readException();
            return r.readInt();
        } catch (RemoteException e) {
            Log.e(TAG, "o1: transact failed", e);
            return -1;
        } finally {
            r.recycle();
            p.recycle();
        }
    }

    // List installed packages in virtual user (transaction 3).
    // S0(userId) -> ArrayList<String>
    public ArrayList<String> S0(int i) {
        Parcel p = Parcel.obtain();
        Parcel r = Parcel.obtain();
        try {
            p.writeInterfaceToken((String) this.Y);
            p.writeInt(8192);
            p.writeInt(i);
            ((C0067Av0) this.Z).transact(3, p, r, 0);
            r.readException();
            return r.createStringArrayList();
        } catch (RemoteException e) {
            Log.e(TAG, "S0: transact failed", e);
            return new ArrayList<>();
        } finally {
            r.recycle();
            p.recycle();
        }
    }
}
