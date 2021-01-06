package com.example.myapplication.circle;

import java.util.ArrayList;

/**
 * 圆形
 */
public class Circle {
    private int radius = 2;
    private int n = 4;

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


    Circle(){

    }

    private float[]  createPositions(){
        ArrayList<Float> data=new ArrayList<>();
        data.add(0.0f);             //设置圆心坐标
        data.add(0.0f);
        data.add(0.0f);
        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            data.add((float) (radius*Math.sin(i*Math.PI/180f)));
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));
            data.add(0.0f);
        }
        float[] f=new float[data.size()];
        for (int i=0;i<f.length;i++){
            f[i]=data.get(i);
        }
        return f;
    }
}
