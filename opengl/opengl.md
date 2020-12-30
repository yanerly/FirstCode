[toc]

#  openGl 和 openGl ES

## 1. openGLES:

> OpenGl一般用于在图形工作站，PC端使用，由于性能各方面原因，在移动端使用OpenGl基本带不动。为此，Khronos公司就为OpenGl提供了一个子集，OpenGl ES
>
> OpenGl ES是免费的跨平台的功能完善的2D/3D图形库接口的API,是OpenGL的一个子集。



## 2. GlSurfaceView

> SurfaceView
>
> ​		|---------GlSurfaceView-----Renderer(用于渲染)



## 3.GlSurfaceView.Renderer

> 该接口定义了绘制图形所需的方法, 需要创建它的实例对象，并通过 GLSurfaceView.setRenderer() 链接

- onSurfaceCreated()：GLSurfaceView第一次创建时调用，可以用于设置OpenGL的环境参数或初始化的OpenGL图形对象
- onDrawFrame()：用于绘制（和重新绘制）图形对象。



### 改变背景色

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    GLSurfaceView glSurfaceView = new GLSurfaceView(this);

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

    setContentView(glSurfaceView);
}
```



或者，自定义一个类实现Render接口，自定义一个类 extends GLSurfaceView

```java
// 在Activity中调用
MyGLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
setContentView(glSurfaceView);
```



```java
/**
 * 1. 自定义显示器
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private MyRender myRender;

    public MyGLSurfaceView(Context context) {
        super(context);

        // 设置OpenGL ES 版本，可以暂时不设置
        // setEGLContextClientVersion(2);

        myRender = new MyRender();

        // 给view设置渲染器
        setRenderer(myRender);
    }
}
```

```java
/**
 * 2. 自定义渲染器
 */
public class MyRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置背景色，绿色
        GLES20.glClearColor(0.0f,1.0f,0.0f,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
```

<img src="E:\FirstCode\images\Screenshot_20201230_200425_com.example.myapplication.jpg" alt="Screenshot_20201230_200425_com.example.myapplication" style="zoom:25%;" />







## 4.绘制一个三角形

在AndroidManifest.xml中设置OpenGl的版本

```java
<uses-feature android:glEsVersion="0x00020000" android:required="true" />
```



### 步骤：

- 创建 GLSufaceView（见上面步骤）
- GlSurfaceView.Renderer中的绘制步骤：
  - 设置视图展示窗口(viewport) :在onSurfaceChanged中调用GLES20.glViewport(0, 0, width, height);
  - 创建图形类，确定好顶点位置和图形颜色，将顶点和颜色数据转换为OpenGl使用的数据格式
  - 加载顶点着色器 和 片段着色器用来修改图形的颜色，纹理，坐标等属性
  - 创建投影和相机视图来显示视图的显示状态，并将投影和相机视图的转换传递给着色器。
  - 创建项目(Program),连接顶点着色器片段着色器。
  - 将坐标数据传入到OpenGl ES程序中：



# 参考文档：

https://blog.csdn.net/qq_32175491/article/details/79091647