package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapplication.base.NormalTriangleGLSurfaceView;
import com.example.myapplication.frustumM.FrustumGLSurfaceView;
import com.example.myapplication.rotate.OneGlSurfaceView;
import com.example.myapplication.rotate.RotateGLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 方法1：
        /*GLSurfaceView glSurfaceView = new GLSurfaceView(this);

        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                // 设置背景色
                // onDrawFrame中不写glClear，这里无论设置什么值，背景都是黑色的
                 GLES20.glClearColor(1.0f,1.0f,0.0f,1.0f);
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES20.glViewport(0, 0, width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                // 重绘制背景色，要和glClearColor搭配使用
                 GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            }
        });

        setContentView(glSurfaceView);*/

        // 方法2： 在Activity中调用
       // MyGLSurfaceView glSurfaceView = new MyGLSurfaceView(this);

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        frameLayout = findViewById(R.id.framelayout);
    }

    public void show(View view){
        switch (view.getId()){
            case R.id.triangle_btn:
                // 显示普通三角形
                frameLayout.addView(new NormalTriangleGLSurfaceView(this));
                break;
            case R.id.cast_triangle_btn:
                // 优化上面的三角形--->透视投影
                frameLayout.addView(new FrustumGLSurfaceView(this));
                break;
            case R.id.rotate_triangle_btn:
                // 可旋转的三角形
                frameLayout.addView(new OneGlSurfaceView(this));
                break;

        }
    }
}