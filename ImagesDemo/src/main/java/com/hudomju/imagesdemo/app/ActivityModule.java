package com.hudomju.imagesdemo.app;

import android.app.DownloadManager;
import android.content.Context;

import com.hudomju.imagesdemo.io.DownloadManagerApi;
import com.hudomju.imagesdemo.GridLayoutAdapter;
import com.hudomju.imagesdemo.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                GridLayoutAdapter.class,
                DownloadManagerApi.class
        })
public class ActivityModule {

    protected final ServiceDemoApplication mApplication;

    public ActivityModule(ServiceDemoApplication application) {
        mApplication = application;
    }

    @Provides Context provideContext() {
        return mApplication;
    }

    @Provides DownloadManager providDownloadManager() {
        return (DownloadManager) mApplication.getSystemService(Context.DOWNLOAD_SERVICE);
    }

}