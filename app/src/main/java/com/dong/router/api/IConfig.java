package com.dong.router.api;

public interface IConfig {
    int getNightMode();
    void setNightMode(int mode);
    boolean shouldHideSystemApps();
    void setHideSystemApps(boolean hide);
    boolean showAD();
    boolean subscriptionEnabled();
}
