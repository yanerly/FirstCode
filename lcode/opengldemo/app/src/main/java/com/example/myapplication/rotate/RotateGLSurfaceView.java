package com.example.myapplication.rotate;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * 1. 自定义显示器
 */
public class RotateGLSurfaceView extends GLSurfaceView {
    private RotateRender rotateRender;

    public RotateGLSurfaceView(Context context) {
        super(context);

        // 设置OpenGL ES 版本，可以暂时不设置
         setEGLContextClientVersion(2);

        rotateRender = new RotateRender();

        // 给view设置渲染器
         setRenderer(rotateRender);

        //只有在绘制数据改变时才绘制view，可以防止GLSurfaceView帧重绘
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
