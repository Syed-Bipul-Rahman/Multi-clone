package com.dong.multirun.app;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

public final class C0067Av0 implements IBinder {
    public IBinder X;
    public final VT Y;

    public C0067Av0(VT vt) {
        this.Y = vt;
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        if (this.X != null) this.X.dump(fileDescriptor, strArr);
    }

    @Override
    public void dumpAsync(FileDescriptor fileDescriptor, String[] strArr) throws RemoteException {
        if (this.X != null) this.X.dumpAsync(fileDescriptor, strArr);
    }

    @Override
    public String getInterfaceDescriptor() {
        if (this.X == null) return null;
        try { return this.X.getInterfaceDescriptor(); } catch (RemoteException e) { return null; }
    }

    @Override
    public boolean isBinderAlive() {
        return this.X != null && this.X.isBinderAlive();
    }

    @Override
    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException {
        if (this.X != null) this.X.linkToDeath(deathRecipient, i);
    }

    @Override
    public boolean pingBinder() {
        return this.X != null && this.X.pingBinder();
    }

    @Override
    public IInterface queryLocalInterface(String str) {
        if (this.X == null) return null;
        return this.X.queryLocalInterface(str);
    }

    @Override
    public boolean transact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        VT vt = this.Y;
        try {
            if (this.X == null) {
                this.X = VT.t0(vt);
            }
            return this.X.transact(i, parcel, parcel2, i2);
        } catch (RemoteException unused) {
            IBinder fresh = VT.t0(vt);
            this.X = fresh;
            return fresh.transact(i, parcel, parcel2, i2);
        }
    }

    @Override
    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i) {
        if (this.X == null) return false;
        return this.X.unlinkToDeath(deathRecipient, i);
    }
}
