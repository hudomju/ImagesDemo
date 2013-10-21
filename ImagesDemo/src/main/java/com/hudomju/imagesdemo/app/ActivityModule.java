package com.hudomju.imagesdemo.app;

import android.app.DownloadManager;
import android.content.Context;

import com.hudomju.imagesdemo.GridLayoutAdapter;
import com.hudomju.imagesdemo.MainActivity;
import com.hudomju.imagesdemo.anim.NullableZoomer;
import com.hudomju.imagesdemo.anim.Zoomer;
import com.hudomju.imagesdemo.io.DownloadManagerApi;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                GridLayoutAdapter.class,
                DownloadManagerApi.class
        })
public class ActivityModule {

    protected final ImagesDemoApplication mApplication;

    public ActivityModule(ImagesDemoApplication application) {
        mApplication = application;
    }

    @Provides Context provideContext() {
        return mApplication;
    }

    @Provides DownloadManager providDownloadManager() {
        return (DownloadManager) mApplication.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Provides Zoomer providZoomer() {
        return new NullableZoomer();
    }

}