package com.dong.multirun.clone

import android.content.Context
import android.util.Log
import com.dong.multirun.MultiRunApplication
import com.dong.multirun.app.AbstractC3080iF1
import com.dong.multirun.app.VT
import com.dong.multirun.model.AppInfo

object CloneEngine {

    private const val TAG = "CloneEngine"
    private const val MAX_USERS = 50

    // Returns all (userId, packageName) pairs present in the sandbox.
    fun listAllSandboxApps(): List<Pair<Int, String>> {
        val result = mutableListOf<Pair<Int, String>>()
        var consecutiveEmpty = 0
        for (userId in 1..MAX_USERS) {
            val pkgs = try { VT.b0?.S0(userId) } catch (e: Exception) { null }
            if (pkgs.isNullOrEmpty()) {
                consecutiveEmpty++
                if (consecutiveEmpty >= 3) break // no more spaces allocated
            } else {
                consecutiveEmpty = 0
                pkgs.forEach { pkg -> result.add(userId to pkg) }
            }
        }
        return result
    }

    // Returns the lowest userId that does not yet have packageName installed.
    fun getNextUserIdForPackage(packageName: String): Int {
        for (userId in 1..MAX_USERS) {
            val pkgs = try { VT.b0?.S0(userId) } catch (e: Exception) { null }
            if (pkgs == null || !pkgs.contains(packageName)) return userId
        }
        return 1
    }

    fun install(context: Context, app: AppInfo, userId: Int): Boolean {
        Log.i(TAG, "install() userId=$userId pkg=${app.packageName}")

        if (MultiRunApplication.getCloneClassLoader() == null) {
            Log.e(TAG, "Clone ClassLoader is null — engine bootstrap failed")
            return false
        }

        return try {
            val result = AbstractC3080iF1.b(userId, app.packageName)
            Log.i(TAG, "install result=$result userId=$userId pkg=${app.packageName}")
            result == 1
        } catch (th: Throwable) {
            Log.e(TAG, "Exception during install of ${app.packageName}", th)
            false
        }
    }

    fun launch(context: Context, packageName: String, userId: Int) {
        Log.i(TAG, "launch() userId=$userId pkg=$packageName")
        try {
            val result = VT.b0?.f1(userId, packageName) ?: -1
            Log.i(TAG, "launch result=$result userId=$userId pkg=$packageName")
        } catch (th: Throwable) {
            Log.e(TAG, "Failed to launch clone userId=$userId pkg=$packageName", th)
        }
    }

    fun uninstall(context: Context, packageName: String, userId: Int): Boolean {
        Log.i(TAG, "uninstall() userId=$userId pkg=$packageName")
        return try {
            val result = VT.b0?.o1(userId, packageName) ?: -1
            Log.i(TAG, "uninstall result=$result userId=$userId pkg=$packageName")
            result == 0
        } catch (e: Exception) {
            Log.e(TAG, "Failed to uninstall $packageName", e)
            false
        }
    }
}
