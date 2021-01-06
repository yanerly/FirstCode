package com.example.myapplication.isoceles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.SurfaceView;

public class IsoSurfaceView extends GLSurfaceView {
    private IsoRender mRender;

    public IsoSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRender = new IsoRender();
        setRenderer(mRender);
    }
}
