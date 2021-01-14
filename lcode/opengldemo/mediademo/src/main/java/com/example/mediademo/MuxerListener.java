package com.example.mediademo;

/**
 * 合并音视频的监听接口
 */
public interface MuxerListener {
    // 开始合成
    void onStart();

    // 合成成功
    void onSuccess(String path);

    // 合成失败
    void onFail(String erroeMsg);
}
