package com.example.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * 1. 自定义显示器
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private MyRender myRender;

    public MyGLSurfaceView(Context context) {
        super(context);

        // 设置OpenGL ES 版本，可以暂时不设置
        // setEGLContextClientVersion(2);

        myRender = new MyRender();

        // 给view设置渲染器
        setRenderer(myRender);
    }
}
