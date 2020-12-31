package com.example.myapplication.frustumM;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 三角形
 */
public class Triangle {
    private int mProgram;//自定义渲染管线程序id
    private int mColorHandle;//顶点颜色属性引用id
    private int mPositionHandle;//顶点位置属性引用id
    private int mMVPMatrixHandle;//总变换矩阵引用id

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // 顶点投影着色器
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    // 片段着色器
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";






    private FloatBuffer vertexBuffer;

    // 每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;

    // 顶点坐标
    private static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    // 颜色
    float color[] = { 255, 0, 0, 1.0f };

    public Triangle() {
        // 1. 数据转换
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        // TODO 为什么要进行数据转换
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        vertexBuffer.put(triangleCoords);
        // 设置缓冲区来读取第一个坐标
        vertexBuffer.position(0);

        // 2.加载着色器
        int vertexShader = FrustumRender.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = FrustumRender.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 3.创建空的OpenGL ES程序
        mProgram = GLES20.glCreateProgram();

        // 4.1添加顶点着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader);

        // 4.2添加片段着色器到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);

        // 5.创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram);


    }

    // 投影矩阵绘制
    public void draw(float[] mvpMatrix) {
        // 1.将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // 2.1获取顶点着色器的位置的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 3.2启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //3.3准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 3.1获取片段着色器的颜色的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 3.2设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        //*************转换矩阵**********************
        // 4.1得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // 4.2 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        //***************转换矩阵********************

        // 5.绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 6.禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

