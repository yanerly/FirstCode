package com.example.myapplication.square;

import android.opengl.GLES20;

import com.example.myapplication.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 正方形
 */
public class Square {
    // 顶点着色器
    /*private final String vertexShaderCode = "" +
            "attribute vec4 vPosition;" +
            " void main() {" +
            "     gl_Position = vPosition;" +
            " }";*/
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" + //attribute一般用于每个顶点都各不相同的量
                    "uniform mat4 vMatrix;" +     //uniform一般用于对同一组顶点组成的3D物体中各个顶点都相同的量
                    "varying  vec4 vColor;" +  // 新增,varying一般用于从顶点着色器传入到片元着色器的量
                    "attribute vec4 aColor;" + // 新增
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "  vColor=aColor;" +  // 新增：aColor（顶点的颜色）作为输入量，传递给了vColor
                    "}";

    // 片段着色器
    private final String fragmentShaderCode = "" +
            "precision mediump float;" +
            " uniform vec4 vColor;" +
            " void main() {" +
            "     gl_FragColor = vColor;" +
            " }";

    // 普通白色
    float color[] = {255, 0, 0, 1.0f};

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private int mProgram;//自定义渲染管线程序id;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;

    // 坐标顶点
    float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f
    };

    // 每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;

    private int vertexCount = squareCoords.length / COORDS_PER_VERTEX;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    Square(){
        // 1. 位置:
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        vertexBuffer.put(squareCoords);
        // 设置缓冲区来读取第一个坐标
        vertexBuffer.position(0);

        // 2.加载着色器
        int vertexShader = Utils.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        // 链接着色器
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    void draw(float[] mMVPMatrix){
        // 激活program
        GLES20.glUseProgram(mProgram);

        // 矩阵
        mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);

        // 顶点
        mPositionHandle = GLES20.glGetAttribLocation(mProgram,"vPosition");
        // 启用顶点着色器
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 颜色
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle,1,color,0);

          // 画图形
         //GLES20.glDrawArrays(GLES20.GL_POINTS,0,vertexCount);
         // GLES20.glDrawArrays(GLES20.GL_LINES,0,vertexCount); // 2条竖线
        //GLES20.glDrawArrays(GLES20.GL_LINE_STRIP,0,vertexCount); //折线
        //GLES20.glDrawArrays(GLES20.GL_LINE_LOOP,0,vertexCount); //首尾相连
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);// 2个三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,vertexCount);//
        // GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,vertexCount);



        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
}
