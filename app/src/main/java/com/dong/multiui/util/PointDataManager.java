package com.dong.multiui.util;

import android.os.Bundle;
import android.util.Log;
import com.dong.router.api.IPointData;

public final class PointDataManager implements IPointData {
    @Override
    public void addEvent(String key, Bundle data) {
        Log.d("PointDataManager", "addEvent: " + key);
    }
}
