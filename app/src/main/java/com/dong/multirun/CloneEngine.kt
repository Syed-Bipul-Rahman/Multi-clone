package com.dong.multirun.clone

import android.content.Context
import android.util.Log
import com.dong.multirun.MultiRunApplication
import com.dong.multirun.app.AbstractC3080iF1
import com.dong.multirun.app.VT
import com.dong.multirun.model.AppInfo

object CloneEngine {

    private const val TAG = "CloneEngine"
    private const val USER_ID = 1

    fun install(context: Context, app: AppInfo): Boolean {
        Log.i(TAG, "install() called for: ${app.packageName}")

        if (MultiRunApplication.getCloneClassLoader() == null) {
            Log.e(TAG, "Clone ClassLoader is null — engine bootstrap failed")
            return false
        }

        return try {
            val result = AbstractC3080iF1.b(USER_ID, app.packageName)
            Log.i(TAG, "install result=$result for ${app.packageName}")
            result == 1
        } catch (th: Throwable) {
            Log.e(TAG, "Exception during install of ${app.packageName}", th)
            false
        }
    }

    fun launch(context: Context, packageName: String, requestCode: Int) {
        Log.i(TAG, "launch() called for: $packageName")
        try {
            val result = VT.b0?.f1(USER_ID, packageName) ?: -1
            Log.i(TAG, "launch result=$result for $packageName")
        } catch (th: Throwable) {
            Log.e(TAG, "Failed to launch clone for: $packageName", th)
        }
    }

    fun isCloned(context: Context, packageName: String): Boolean {
        return try {
            val installed = VT.b0?.S0(USER_ID)
            val cloned = installed?.contains(packageName) == true
            Log.d(TAG, "isCloned($packageName) = $cloned")
            cloned
        } catch (e: Exception) {
            Log.e(TAG, "isCloned() failed for $packageName", e)
            false
        }
    }

    fun uninstall(context: Context, packageName: String): Boolean {
        Log.i(TAG, "uninstall() called for: $packageName")
        return try {
            val result = VT.b0?.o1(USER_ID, packageName) ?: -1
            Log.i(TAG, "uninstall result=$result for $packageName")
            result == 0
        } catch (e: Exception) {
            Log.e(TAG, "Failed to uninstall $packageName", e)
            false
        }
    }
}
