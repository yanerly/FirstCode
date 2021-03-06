package com.example.myapplication.frustumM;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * 1. 自定义显示器
 */
public class FrustumGLSurfaceView extends GLSurfaceView {
    private FrustumRender frustumRender;

    public FrustumGLSurfaceView(Context context) {
        super(context);

        // 设置OpenGL ES 版本，可以暂时不设置
         setEGLContextClientVersion(2);

        frustumRender = new FrustumRender();

        // 给view设置渲染器
         setRenderer(frustumRender);
    }
}
