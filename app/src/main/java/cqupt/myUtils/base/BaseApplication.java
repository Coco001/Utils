package cqupt.myUtils.base;

import android.app.Application;

public abstract class BaseApplication extends Application {

    public abstract void initConfigs();

    @Override
    public void onCreate() {
        super.onCreate();
        initConfigs();
    }

}
