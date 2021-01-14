package com.example.mediademo;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

/**
 * 分裂器
 */
public class MyExtractor {
    private static final String TAG = "ly.MyExtractor";

    private String mPath;

    private MediaExtractor mMediaExtractor;
    // 视频轨道id
    int mVideoTrackId;
    // 视频格式
    MediaFormat mVideoFormat;

    // 音频轨道id
    int mAudioTrackId;
    // 音频格式
    MediaFormat mAudioFormat;

    private StringBuilder builder = new StringBuilder();

    MyExtractor(String path){
        this.mPath = path;
        initExtractor();
    }

    private void initExtractor(){
        builder.append("*****开始分离video1音视频*****\n");
        // 1. 设置数据源
        mMediaExtractor = new MediaExtractor();

        // 2. 设置数据源
        try {
            mMediaExtractor.setDataSource(Constant.PATH1);
        } catch (IOException e) {
            Log.e(TAG,"设置数据源出错："+e.getMessage());
        }
        builder.append("视频路径："+(Constant.PATH1+"\n"));

        // 3. 获取该视频包含多少个轨道，一般视频都有 视频轨和音频轨
        int numTack = mMediaExtractor.getTrackCount();
        builder.append("轨道数 ："+numTack+"\n");

        /*******************Format相关**********************/
        for (int i=0;i<numTack;i++){
            MediaFormat format = mMediaExtractor.getTrackFormat(i);

            // 4.获取mime 类型
            String mime = format.getString(MediaFormat.KEY_MIME);
            builder.append("轨道"+i+",mime:"+mime+"\n");

            if (mime.startsWith("video")){
                mVideoTrackId = i;
                mVideoFormat = format;
            }else if (mime.startsWith("audio")){
                mAudioTrackId = i;
                mAudioFormat = format;
            }
        }

        Log.d(TAG,"VIDEO Format:"+mVideoFormat);
        Log.d(TAG,"AUDIO Format:"+mAudioFormat);

        // 5.获取视频宽高
        int width = mVideoFormat.getInteger(MediaFormat.KEY_WIDTH);
        int height = mVideoFormat.getInteger(MediaFormat.KEY_HEIGHT);
        builder.append("视频宽高：w:"+width+",h:"+height+"\n");

        // 6.播放时长
        long duration = mVideoFormat.getLong(MediaFormat.KEY_DURATION);
        builder.append("视频时长："+duration+"(微妙)\n");

        // 7.比特率(??空指针异常)
        // int sampleRate = mVideoFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        // builder.append("视频sampleRate："+sampleRate+"(微妙)\n");

        // 8.声道数(??空指针异常)
        // int chanelCount = mVideoFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        // builder.append("视频声道数："+chanelCount+"\n");

        /*******************Format相关 end**********************/

        // 返回当前的时间戳
        long time = mMediaExtractor.getSampleTime();
        builder.append("当前时间戳："+time+"\n");

        builder.append("*****video1音视频分离完成*****\n");
    }

    public StringBuilder getBuilder(){
        return builder;
    }
}
