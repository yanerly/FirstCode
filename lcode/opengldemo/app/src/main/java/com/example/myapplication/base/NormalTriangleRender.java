package com.example.myapplication.base;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.myapplication.Square;
import com.example.myapplication.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 自定义渲染器
 */
public class NormalTriangleRender implements GLSurfaceView.Renderer {
    private NormalTriangle triangle;
    private Square square;

    // 定义投影
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    // 旋转投影矩阵
    private float[] mRotationMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置背景色，绿色
        GLES20.glClearColor(0.0f,1.0f,0.0f,1.0f);

        // 初始化triangle
        triangle = new NormalTriangle();

        // 初始化 square
        //square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        float ratio = (float) width / height;

        // 这个透视投影矩阵被应用于对象坐标在onDrawFrame（）方法中
        // Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 2. 定义一个相机视图
        // Set the camera position (View matrix)
        // Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // 旋转矩阵
//        float[] scratch = new float[16];
//        // 创建一个旋转矩阵
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        // 3.将旋转矩阵与投影和相机视图组合在一起
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // 3. 计算转换矩阵
         //Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // 普通绘制
       // square.draw();
        triangle.draw();

        // 透视投影+相机视图
        //triangle.draw(mMVPMatrix);

        // 旋转+透视投影+相机视图
       // triangle.draw(scratch);

    }

    /**
     * 加载着色器
     * @param type
     * @param shaderCode
     * @return
     */
    public static int loadShader(int type, String shaderCode){

        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // 添加上面编写的着色器代码并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
