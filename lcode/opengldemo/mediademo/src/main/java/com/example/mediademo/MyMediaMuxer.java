package com.example.mediademo;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;

/**
 * 合并音视频
 */
public class MyMediaMuxer implements Runnable{
    private MediaMuxer mediaMuxer;
    private String path1;
    private String path2;

    private MyExtractor videoExtractor;
    private MyExtractor audioExtractor;

    private int videoTackId;
    private int audioTrackId;
    private MediaFormat videoFormat;
    private MediaFormat audioFormat;

    private String muxerPath;

    private MuxerListener listener;


    MyMediaMuxer(MuxerListener listener){
        this.listener = listener;

        // 分裂两个文件的音视频，然后合并成一个文件
        extracMedia();
    }

    private void extracMedia() {
        videoExtractor = new MyExtractor(Constant.PATH1);
        audioExtractor = new MyExtractor(Constant.PATH2);

        
    }

    @Override
    public void run() {
        listener.onStart();// 开始合成

    }
}
