package com.dong.multirun.app;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.FileNotFoundException;

/* JADX INFO: renamed from: com.dong.multirun.app.Nb */
public abstract class AbstractC0834Nb extends ContentProvider {
    public static Uri b(Uri uri) {
        try {
            return (Uri) AbstractC3600lG1.b.invoke(null, 10401, new Object[]{uri});
        } catch (Throwable unused) {
            return uri;
        }
    }

    public final ContentProviderClient a(Uri uri) {
        if (uri != null) {
            return getContext().getContentResolver().acquireContentProviderClient(uri.getAuthority());
        }
        return null;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        AbstractC6070zc.a = 0;
        super.attachInfo(context, providerInfo);
    }

    @Override
    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return 0;
        }
        try {
            return contentProviderClientA.bulkInsert(uriB, contentValuesArr);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override
    public final int delete(Uri uri, String str, String[] strArr) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return 0;
        }
        try {
            return contentProviderClientA.delete(uriB, str, strArr);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override
    public final String getType(Uri uri) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return null;
        }
        try {
            return contentProviderClientA.getType(uriB);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override
    public final Uri insert(Uri uri, ContentValues contentValues) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return null;
        }
        try {
            return contentProviderClientA.insert(uriB, contentValues);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public final AssetFileDescriptor openAssetFile(Uri uri, String str) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                return contentProviderClientA.openAssetFile(uriB, str);
            } catch (RemoteException unused) {
            }
        }
        return super.openAssetFile(uri, str);
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                return contentProviderClientA.openFile(uriB, str);
            } catch (RemoteException unused) {
            }
        }
        return super.openFile(uri, str);
    }

    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                return contentProviderClientA.openTypedAssetFileDescriptor(uriB, str, bundle);
            } catch (RemoteException unused) {
            }
        }
        return super.openTypedAssetFile(uri, str, bundle);
    }

    @Override
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return null;
        }
        try {
            return contentProviderClientA.query(uriB, strArr, str, strArr2, str2);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA == null) {
            return 0;
        }
        try {
            return contentProviderClientA.update(uriB, contentValues, str, strArr);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override
    public final AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                return contentProviderClientA.openAssetFile(uriB, str, cancellationSignal);
            } catch (RemoteException unused) {
            }
        }
        return super.openAssetFile(uri, str, cancellationSignal);
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                return contentProviderClientA.openFile(uriB, str, cancellationSignal);
            } catch (RemoteException unused) {
            }
        }
        return super.openFile(uri, str, cancellationSignal);
    }

    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        Uri uriB = b(uri);
        ContentProviderClient contentProviderClientA = a(uriB);
        if (contentProviderClientA != null) {
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    return contentProviderClientA.openTypedAssetFile(uriB, str, bundle, cancellationSignal);
                }
                return contentProviderClientA.openTypedAssetFileDescriptor(uriB, str, bundle, cancellationSignal);
            } catch (RemoteException unused) {
            }
        }
        return super.openTypedAssetFile(uri, str, bundle, cancellationSignal);
    }
}
