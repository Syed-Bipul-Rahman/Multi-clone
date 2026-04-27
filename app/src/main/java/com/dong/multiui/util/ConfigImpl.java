package com.dong.multiui.util;

import androidx.appcompat.app.AppCompatDelegate;
import com.dong.router.api.IConfig;

public final class ConfigImpl implements IConfig {
    private int nightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    private boolean hideSystemApps = false;

    @Override public int getNightMode() { return nightMode; }
    @Override public void setNightMode(int mode) { this.nightMode = mode; }
    @Override public boolean shouldHideSystemApps() { return hideSystemApps; }
    @Override public void setHideSystemApps(boolean hide) { this.hideSystemApps = hide; }
    @Override public boolean showAD() { return false; }
    @Override public boolean subscriptionEnabled() { return false; }
}
