package com.dong.multirun.model

import androidx.annotation.DrawableRes

data class AppAction(
    val id: String,
    val label: String,
    @DrawableRes val iconRes: Int
)
