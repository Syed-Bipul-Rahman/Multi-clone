package com.dong.multirun.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    var customName: String = appName,
    var spaceIndex: Int = 1,
    var isHidden: Boolean = false
)
