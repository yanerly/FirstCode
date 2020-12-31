package com.example.myapplication.base;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.myapplication.MyRender;

/**
 * 1. 普通三角形
 */
public class NormalTriangleGLSurfaceView extends GLSurfaceView {
    private NormalTriangleRender myRender;

    public NormalTriangleGLSurfaceView(Context context) {
        super(context);

        // 设置OpenGL ES 版本，可以暂时不设置
         setEGLContextClientVersion(2);

        myRender = new NormalTriangleRender();

        // 给view设置渲染器
         setRenderer(myRender);
    }
}
