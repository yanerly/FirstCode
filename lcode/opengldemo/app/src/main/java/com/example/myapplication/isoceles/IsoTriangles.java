package com.example.myapplication.isoceles;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.example.myapplication.Utils.loadShader;

/**
 * 等腰三角形
 */
public class IsoTriangles {
    // 顶点着色器,gl_Position和gl_FragColor都是Shader的内置变量，分别为定点位置和片元颜色。
    /*private final String vertexShaderCode = "" +
            "attribute vec4 vPosition;\n" +
            " void main() {\n" +
            "     gl_Position = vPosition;\n" +
            " }";*/

    /**
     * 等腰顶点着色器
     */
    /*private final String vertexShaderCode =
            "attribute vec4 vPosition;\n" +
            "uniform mat4 vMatrix;\n" + // 新增
            "void main() {\n" +
            "    gl_Position = vMatrix*vPosition;\n" +
            "}";*/

    /**
     * 等腰颜色变换的顶点着色器
     */
    private final String vertexShaderCode =
            "attribute vec4 vPosition;\n" + //attribute一般用于每个顶点都各不相同的量
            "uniform mat4 vMatrix;\n" +     //uniform一般用于对同一组顶点组成的3D物体中各个顶点都相同的量
            "varying  vec4 vColor;\n" +  // 新增,varying一般用于从顶点着色器传入到片元着色器的量
            "attribute vec4 aColor;\n" + // 新增
            "void main() {\n" +
            "  gl_Position = vMatrix*vPosition;\n" +
            "  vColor=aColor;\n" +  // 新增：aColor（顶点的颜色）作为输入量，传递给了vColor
            "}";

    // 片段着色器
    /*private final String fragmentShaderCode = "" +
            "precision mediump float;\n" +
            " uniform vec4 vColor;\n" +
            " void main() {\n" +
            "     gl_FragColor = vColor;\n" +
            " }";*/

    // 多色的片段着色器
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";
    // 坐标顶点
    float triangleCoords[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    // 白色
    //float color[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    //升级版：设置颜色（五颜六色）
    float color[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f ,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private int mProgram;//自定义渲染管线程序id;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;

    // 每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;

    private int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // 构造器
    IsoTriangles(){
        //申请底层空间
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        //将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 颜色
        ByteBuffer dd = ByteBuffer.allocateDirect(
                color.length * 4);
        dd.order(ByteOrder.nativeOrder());
        colorBuffer = dd.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }

    void draw(float[] mMVPMatrix){
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        //0.矩阵 获取变换矩阵vMatrix成员句柄
        mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);

        //1.获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        //2.获取片元着色器的vColor成员的句柄
       /* mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);*/

        //2.1 获取片元着色器的vColor成员的句柄
       /* ByteBuffer dd = ByteBuffer.allocateDirect(
                color.length * 4);
        dd.order(ByteOrder.nativeOrder());
        FloatBuffer colorBuffer = dd.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);*/
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,
                GLES20.GL_FLOAT,false,
                0,colorBuffer);

        //3. 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
