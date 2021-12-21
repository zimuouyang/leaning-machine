package com.leaning_machine.utils;

public interface JsDownloadListener {
    void onStartDownload();
    void onProgress(int progress);
    void onFinishDownload();
    void onFail(String errorInfo);
}
