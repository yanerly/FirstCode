package com.example.myapplication.isoceles;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.myapplication.frustumM.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class IsoRender implements GLSurfaceView.Renderer {
    private IsoTriangles triangles;
    private final float[] mMVPMatrix = new float[16];//最后起作用的总变换矩阵
    private final float[] mProjectMatrix = new float[16];//4x4矩阵 投影用
    private final float[] mViewMatrix = new float[16];//摄像机位置朝向9参数矩阵

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
        triangles = new IsoTriangles();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置窗口视图
        GLES20.glViewport(0,0,width,height);

        // 投影矩阵
        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        triangles.draw(mMVPMatrix);
    }
}
