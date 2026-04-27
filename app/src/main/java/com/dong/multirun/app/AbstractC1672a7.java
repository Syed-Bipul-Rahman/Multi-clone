package com.dong.multirun.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class AbstractC1672a7 {

    private static final String TAG = "AbstractC1672a7";

    /**
     * Safely close a Closeable, ignoring exceptions.
     */
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close stream: " + e.getMessage());
            }
        }
    }

    /**
     * Copy all bytes from inputStream to outputStream using a 4 KB buffer.
     */
    public static void b(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buf = new byte[4096];
        int read;
        while ((read = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, read);
        }
    }

    /**
     * Extract a specific entry from the APK zip into {@code file}.
     * FIX: Added logging so extraction failures are visible.
     */
    public static void c(ApplicationInfo applicationInfo, File file) {
        String entryName = Gw1.b("xX/kOHq49BPReLk8fqA=\n", "pAyXXQ7L22Y=\n");
        if (entryName == null || entryName.isEmpty()) {
            Log.e(TAG, "c(): decoded entry name is null/empty — cannot extract DEX");
            return;
        }

        Log.d(TAG, "c(): extracting entry='" + entryName
                + "' from APK=" + applicationInfo.sourceDir
                + " → " + file.getAbsolutePath());

        ZipFile zipFile = null;
        InputStream  is  = null;
        FileOutputStream fos = null;
        try {
            file.getParentFile().mkdirs();
            zipFile = new ZipFile(applicationInfo.sourceDir);
            ZipEntry entry = zipFile.getEntry(entryName);
            if (entry == null) {
                Log.e(TAG, "c(): entry '" + entryName + "' not found in APK");
                return;
            }
            is  = zipFile.getInputStream(entry);
            fos = new FileOutputStream(file);
            b(is, fos);
            Log.d(TAG, "c(): extraction complete, size=" + file.length() + " bytes");
        } catch (Throwable th) {
            // FIX: was th.printStackTrace() with no tag — now properly logged
            Log.e(TAG, "c(): extraction failed", th);
        } finally {
            a(fos);
            a(is);
            if (zipFile != null) {
                try { zipFile.close(); } catch (IOException e) { /* ignore */ }
            }
        }
    }

    /**
     * Copy inputStream → fileOutputStream, closing both when done.
     */
    public static void d(InputStream inputStream, FileOutputStream fileOutputStream)
            throws IOException {
        try {
            byte[] buf = new byte[4096];
            int read;
            while ((read = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, read);
            }
        } finally {
            a(inputStream);
            a(fileOutputStream);
        }
    }

    /**
     * Recursively delete a file or directory.
     */
    public static void e(File file) {
        try {
            if (!file.isFile()) {
                File[] children = file.listFiles();
                if (children != null) {
                    for (File child : children) {
                        e(child);
                    }
                }
            }
            boolean deleted = file.delete();
            if (!deleted) {
                Log.w(TAG, "e(): could not delete: " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            Log.w(TAG, "e(): exception while deleting: " + file.getAbsolutePath(), ex);
        }
    }

    /**
     * Unzip all entries whose name starts with {@code str} from {@code file}
     * into directory {@code file2}.
     *
     * FIX: Added logging for each extracted entry and for errors.
     * Retained the path-traversal guard (canonical path check).
     */
    public static void f(File file, String str, File file2) throws Throwable {
        if (str == null || str.isEmpty()) {
            Log.e(TAG, "f(): prefix string is null/empty — aborting unzip");
            return;
        }

        Log.d(TAG, "f(): unzipping entries with prefix='" + str
                + "' from " + file.getAbsolutePath()
                + " → " + file2.getAbsolutePath());

        ZipFile zipFile = null;
        int count = 0;
        try {
            zipFile = new ZipFile(file);
            String canonicalBase = file2.getCanonicalPath();
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.getName().startsWith(str) || entry.isDirectory()) {
                    continue;
                }

                File outFile = new File(file2, entry.getName());

                // Path traversal guard
                if (!outFile.getCanonicalPath().startsWith(canonicalBase)) {
                    throw new IOException("Path traversal detected: " + entry.getName());
                }

                outFile.getParentFile().mkdirs();
                d(zipFile.getInputStream(entry), new FileOutputStream(outFile));
                Log.d(TAG, "f(): extracted → " + outFile.getAbsolutePath()
                        + " (" + outFile.length() + " bytes)");
                count++;
            }
            Log.i(TAG, "f(): unzip complete, " + count + " entries extracted");

        } catch (Throwable th) {
            Log.e(TAG, "f(): unzip failed", th);
            throw th;
        } finally {
            if (zipFile != null) {
                try { zipFile.close(); } catch (IOException e) { /* ignore */ }
            }
        }
    }

    /**
     * FIX #4 (from original analysis): g() returns true when the context package
     * is NOT the host app — i.e. true inside a cloned app, false in the host.
     *
     * The decoded string is the host app package name ("com.dong.multirun").
     * This is correct behaviour — just documented clearly here so it is not
     * accidentally "fixed" by inverting the logic.
     *
     * Returns true  → running inside a cloned app environment
     * Returns false → running as the host/main app
     */
    public static boolean g(Context context) {
        String hostPackage = Gw1.b("UFklQ1dPudAdWz0BR0mlwl0=\n", "MzZIbTMg17c=\n");
        boolean isCloneEnv = !hostPackage.equals(context.getPackageName());
        Log.d(TAG, "g(): hostPackage=" + hostPackage
                + " contextPackage=" + context.getPackageName()
                + " isCloneEnv=" + isCloneEnv);
        return isCloneEnv;
    }
}