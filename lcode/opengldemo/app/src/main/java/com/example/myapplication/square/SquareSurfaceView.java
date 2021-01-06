package com.example.myapplication.square;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SquareSurfaceView extends GLSurfaceView {
    private SquareRender render;

    public SquareSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        render = new SquareRender();
        setRenderer(render);
    }
}
