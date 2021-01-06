package com.example.myapplication.circle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class CircleSurfaceView extends GLSurfaceView {
    private CircleRender render;

    public CircleSurfaceView(Context context) {
        super(context);

        render = new CircleRender();
        setEGLContextClientVersion(2);
        setRenderer(render);
    }
}
