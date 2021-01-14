package com.example.mediademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioMetadata;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ly.MainActivity";

    // 权限请求码
    private static final int REQUEST_CODE = 100;

    // 权限
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ArrayList<String> failPermissonList = new ArrayList<>();



    private TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTvInfo = findViewById(R.id.tv_info);
    }

    /**
     * 分离音视频
     */
    public void extracMedia(View view){
        checkPermission(permissions);

        MyExtractor extractor = new MyExtractor(Constant.PATH1);

        mTvInfo.setText(extractor.getBuilder());
    }

    public void checkPermission(String[] pers){
        failPermissonList.clear();

        int grant = PackageManager.PERMISSION_GRANTED;
        for (String perission : pers){
           if (grant != ActivityCompat.checkSelfPermission(this,perission)){
               failPermissonList.add(perission);
           }
        }

        if (failPermissonList.isEmpty()){
            Toast.makeText(this,"权限获取成功",Toast.LENGTH_SHORT).show();
        }else {
            // 获取多权限
            ActivityCompat.requestPermissions(this,
                    failPermissonList.toArray(new String[failPermissonList.size()]),REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,permissions[i]+"授权成功",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // todo
        // mMediaExtractor.release();
    }
}