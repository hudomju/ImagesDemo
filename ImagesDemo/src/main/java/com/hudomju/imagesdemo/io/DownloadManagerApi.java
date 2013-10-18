package com.hudomju.imagesdemo.io;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class DownloadManagerApi extends BroadcastReceiver {

    @Inject Context mContext;
    @Inject DownloadManager mDownloadManager;
    private final Map<Long, String> mEnqueuedQueries = new HashMap<Long, String>();
    private final List<DownloadManagerApiCallbacks> mCallbacks
            = new ArrayList<DownloadManagerApiCallbacks>();

    public void init() {
        mContext.registerReceiver(this, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void stop() {
        mContext.unregisterReceiver(this);
    }

    @Override public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
            onComplete(intent);
    }

    public void onComplete(Intent intent) {
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        String url = mEnqueuedQueries.remove(downloadId);
        for (DownloadManagerApiCallbacks callback: mCallbacks)
            callback.onCompleteDownload(url);
    }

    public void startDownload(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        mEnqueuedQueries.put(mDownloadManager.enqueue(request), url);
    }

    public boolean isDownloading(String url) {
        return mEnqueuedQueries.containsValue(url);
    }

    public void stopDownload(String url) {
        if (mEnqueuedQueries.containsValue(url))
            mDownloadManager.remove(removeEntryFromValue(url));
    }

    private long removeEntryFromValue(String url) {
        Iterator<Map.Entry<Long, String>> iterator = mEnqueuedQueries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, String> entry = iterator.next();
            if (entry.getValue().equals(url)) {
                iterator.remove();
                return entry.getKey();
            }
        }
        return -1;
    }

    public void showAllDownloads() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public void registerCallbacks(DownloadManagerApiCallbacks callbacks) {
        if (callbacks != null) mCallbacks.add(callbacks);
    }

    public void unregisterCallbacks(DownloadManagerApiCallbacks callbacks) {
        if (callbacks != null) mCallbacks.remove(callbacks);
    }

    public interface DownloadManagerApiCallbacks {

        public void onCompleteDownload(String url);

    }


}
