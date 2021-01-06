[toc]

#  openGl 和 openGl ES

## 1. openGLES:

### 概念：

> OpenGl一般用于在图形工作站，PC端使用，由于性能各方面原因，在移动端使用OpenGl基本带不动。为此，Khronos公司就为OpenGl提供了一个子集，OpenGl ES
>
> 
>
> OpenGl ES是免费的跨平台的功能完善的2D/3D图形库接口的API,是OpenGL的一个子集。
>
> 
>
> 在OpenGL中，任何事物都在3D空间中，而屏幕和窗口却是2D像素数组，这导致OpenGL的大部分工作都是关于把3D坐标转变为适应你屏幕的2D像素。

### 双缓冲(Double Buffer)

> 应用程序使用单缓冲绘图时可能会存在图像闪烁的问题。 这是因为生成的图像不是一下子被绘制出来的，而是按照从左到右，由上而下逐像素地绘制而成的。最终图像不是在瞬间显示给用户，而是通过一步一步生成的，这会导致渲染的结果很不真实。为了规避这些问题，我们应用双缓冲渲染窗口应用程序。**前**缓冲保存着最终输出的图像，它会在屏幕上显示；而所有的的渲染指令都会在**后**缓冲上绘制。当所有的渲染指令执行完毕后，我们**交换**(Swap)前缓冲和后缓冲，这样图像就立即呈显出来，之前提到的不真实感就消除了。

## 2. GlSurfaceView

> SurfaceView
>
> ​		|---------GlSurfaceView-----Renderer(用于渲染)



## 3. GlSurfaceView.Renderer

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
            // 设置清空屏幕所用的颜色
            // onDrawFrame中不写glClear，这里无论设置什么值，背景都是黑色的
            GLES20.glClearColor(1.0f,1.0f,0.0f,1.0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            // 在每个渲染开始的时候，都希望清屏，否则，仍能看到上一次迭代的渲染结果
            // glClear接收的是一个缓冲位：GL_xxx_BUFFER_BIT
            // 当调用GLClear函数，清空颜色缓冲区后，整个颜色缓冲会被填充为glClearColor里面设置的颜色
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



## 4.着色器

> OpenGL着色器是用OpenGL着色器语言(OpenGL Shading Language, GLSL)写成的

![pipeline](E:\FirstCode\images\pipeline.png)

### 1）顶点着色器（Vertex Shader）

> 把一个单独的顶点作为输入。顶点着色器主要的目的是把3D坐标转为另一种3D坐标，同时顶点着色器顶点着色器可用来修改图形的位置，颜色，纹理坐标，不过不能用来创建新的顶点坐标。



### 2）图元(Primitive)

> OpenGL需要你去指定这些数据所表示的渲染类型。图元会提示把这些数据渲染成一系列的点，一系列的三角形，还是仅仅是一个长长的线，任何一个绘制指令的调用都将把图元传递给OpenGL。这是其中的几个：GL_POINTS、GL_TRIANGLES、GL_LINE_STRIP。
>
> 图元装配：将顶点着色器输出的所有顶点作为输入（如果是GL_POINTS，那么就是一个顶点），把所有的点装配成指定图元的形状；本节是一个三角形。



### 3）几何着色器(Geometry Shader)

> 图元装配阶段的输出会传递给几何着色器。几何着色器把图元形式的一系列顶点的集合作为输入，它可以通过产生新顶点构造出新的（或是其它的）图元来生成其他形状。例子中，它生成了另一个三角形。



### 4)光栅化阶段(Rasterization Stage)

> 几何着色器的输出会被传入光栅化阶段，这里它会把图元映射为最终屏幕上相应的像素，生成供片段着色器(Fragment Shader)使用的片段(Fragment)。在片段着色器运行之前会执行裁切(Clipping)。裁切会丢弃超出你的视图以外的所有像素，用来提升执行效率。



### 5)片段着色器（Fragment Shader ) 

> 片段着色器的主要目的是计算一个像素的最终颜色，这也是所有OpenGL高级效果产生的地方。通常，片段着色器包含3D场景的数据（比如光照、阴影、光的颜色等等），这些数据可以被用来计算最终像素的颜色。



### 着色器程序对象/项目（Program）

> 它是多个着色器合并之后并最终链接完成的版本。如果要使用刚才编译的着色器我们必须把它们链接为一个着色器程序对象（Program），然后在渲染对象的时候激活这个着色器程序（Program）。已激活的着色器将在我们发送渲染调用的时候被使用

```java
// 4.1添加顶点着色器到程序中
GLES20.glAttachShader(mProgram, vertexShader);

// 4.2添加片段着色器到程序中
GLES20.glAttachShader(mProgram, fragmentShader);

// 5.创建OpenGL ES程序可执行文件
GLES20.glLinkProgram(mProgram);
```

```java
// 5.激活Program,在glUseProgram函数调用之后，每个着色器调用和渲染调用都会使用这个Program
GLES20.glUseProgram(mProgram);
```

把着色器对象链接到程序对象以后，记得删除着色器对象

```java
GLES20.glDeleteShader(vertexShader);
GLES20.glDeleteShader(fragmentShader);
```

解析顶点缓冲数据

![vertex_attribute_pointer](E:\FirstCode\images\vertex_attribute_pointer.png)

- 位置数据被储存为32-bit（4字节）浮点值。
- 每个位置包含3个这样的值。
- 在这3个值之间没有空隙（或其他值）。这几个值在数组中紧密排列。
- 数据中第一个值在缓冲开始的位置。



如何解析顶点数据（应用到逐个顶点属性上）

```java
int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

// 以顶点属性位置值作为参数，启用顶点属性
GLES20.glEnableVertexAttribArray(mPositionHandle);

// 第一个参数指定我们要配置的顶点属性
// 第二个参数指定顶点属性的大小。顶点属性是一个vec3，它由3个值组成，所以大小是3。
// 第三个参数指定数据的类型，这里是GL_FLOAT(vec*都是由浮点数值组成的)。
// 第四个参数定义我们是否希望数据被标准化(Normalize)。如果我们设置为true，所有数据都会被映射到0（对于有符号型signed数据是-1）到1之间,我们把它设置为false。
// 第五个参数叫做步长(Stride)，指在连续的顶点属性组之间的间隔。由于下个组位置数据在3个GLfloat之后，我们把步长设置为3 * sizeof(GLfloat)。
// 第六个参数类型是 Buffer，所以需要我们进行强制类型转换。它表示位置数据在缓冲中起始位置的偏移量(Offset)。由于位置数据在数组的开头，所以这里是0。
GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
```





### 注意:

> 因为着色器的代码执行是很昂贵滴，所以避免多次执行，需要我们一般将执行代码的逻辑写带图形类的构造方法中



## 坐标系

### 标准化坐标系

> 一旦你的顶点坐标已经在顶点着色器中处理过，它们就应该是**标准化设备坐标**了，标准化设备坐标是一个x、y和z值在-1.0到1.0的一小段空间。任何落在范围外的坐标都会被丢弃/裁剪，不会显示在你的屏幕上。
>
> OpenGL ES 采用的是**右手坐标**，即向右为X正轴方向，向左为X负轴方向，向上为Y轴正轴方向，向下为Y轴负轴方向，屏幕面垂直向上为Z轴正轴方向，垂直向下为Z轴负轴方向

![ndc](E:\FirstCode\images\ndc.png)

![2018041008572254](E:\FirstCode\images\2018041008572254.jpg)

### 屏幕空间坐标

> 通过glViewport函数提供的数据，进行视口变换(Viewport Transform)后，准化设备坐标会变换为屏幕空间坐标，所得的屏幕空间坐标又会被变换为片段输入到片段着色器中



## 绘制一个三角形

在AndroidManifest.xml中设置OpenGl的版本

```java
<uses-feature android:glEsVersion="0x00020000" android:required="true" />
```



### 步骤：

- 创建 GLSufaceView（见上面步骤）

- GlSurfaceView.Renderer中的绘制步骤：
  
  - 创建图形类: 确定好顶点位置和图形颜色，将顶点和颜色数据转换为OpenGl使用的数据格式
  
  - 设置视图展示窗口(viewport) :在onSurfaceChanged中调用GLES20.glViewport(0, 0, width, height);
  - 加载顶点着色器 和 片段着色器用来修改图形的颜色，纹理，坐标等属性
  - 创建投影和相机视图来显示视图的显示状态，并将投影和相机视图的转换传递给着色器。
  - 创建项目(Program),连接顶点着色器片段着色器。
  - 将坐标数据传入到OpenGl ES程序中：



#### 1） 创建图形类

```java
/**
 * 三角形
 */
public class Triangle {
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
    }
}
```



```java
/**
 * 正方形
 */
public class Square {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // 每个顶点的坐标数（x,y,z）
    private static final int COORDS_PER_VERTEX = 3;

    // 顶点坐标
    static float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f }; // top right

    // 绘制顺序
    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public Square() {
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        // TODO 为什么要进行数据转换
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        // 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        vertexBuffer.put(squareCoords);
        // 设置缓冲区来读取第一个坐标
        vertexBuffer.position(0);

        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

}

```

##### 为什么要进行数据转换？

> 因为Java的缓冲区数据存储结构为大端字节序(BigEdian)，而OpenGl的数据为小端字节序（LittleEdian）,因为数据存储结构的差异，所以，在Android中使用OpenGl的时候必须要进行下转换.
>
> 占几个字节就初始化ByteBuffer长度的时候*几



#### 2）在GlSurfaceView.Renderer中初始化需要渲染的几何图形

##### 需要设置设置OpenGL ES 版本

```java
setEGLContextClientVersion(2);

或者 

<uses-feature android:glEsVersion="0x00020000" android:required="true" />
```





```java
private Triangle triangle;
    private Square square;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置背景色，绿色
        GLES20.glClearColor(0.0f,1.0f,0.0f,1.0f);

        // 初始化triangle
        triangle = new Triangle();

        // 初始化 square
        square = new Square();
    }
```



#### 3) 绘制图形

##### a. 定义一个基本的着色器代码

```java
public class Triangle {
    // 顶点着色器
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    // 片段着色器
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
....
}
```



##### b.加载着色器

```java
public static int loadShader(int type, String shaderCode){

        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // 添加上面编写的着色器代码并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

public Triangle() {
        // 1. 数据转换
        ....

        // 2.加载着色器
        int vertexShader = MyRender.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRender.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
}
```



##### c.创建OpenGL ES程序可执行文件

```java
private int mProgram;

public Triangle() {
        // 1. 数据转换
        ....

        // 2.加载着色器
        ....

        // 3.创建空的OpenGL ES程序
        mProgram = GLES20.glCreateProgram();

        // 4.1添加顶点着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader);

        // 4.2添加片段着色器到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);

        // 5.创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram);

    }
```



##### d.绘制图形的方法

```java
public class Triangle {
    private int mProgram;

    private int mColorHandle;
    private int mPositionHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    ....
        
    // 绘制图形
    public void draw() {
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

        // 4.绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 5.禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
```



在GlSurfaceView.Renderer的onDrawFrame()方法中调用图形类的绘制方法

```java
@Override
public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    // 调用图形类的绘制方法
    triangle.draw();
}
```

<img src="E:\FirstCode\images\Screenshot_20201231_105123_com.example.myapplication.jpg" alt="Screenshot_20201231_105123_com.example.myapplication" style="zoom:20%;" />



##### 小结：

普通三角形：不带投影

```java
private final String vertexShaderCode = "" +
    "attribute vec4 vPosition;\n" +
    " void main() {\n" +
    "     gl_Position = vPosition;\n" +
    " }";
```



带投影的三角形

```java
private final String vertexShaderCode =
    "attribute vec4 vPosition;\n" +
    "uniform mat4 vMatrix;\n" + // 新增
    "void main() {\n" +
    "    gl_Position = vMatrix*vPosition;\n" +
    "}";
```



```java
private final float[] mMVPMatrix = new float[16];//最后起作用的总变换矩阵
private final float[] mProjectMatrix = new float[16];//4x4矩阵 投影用
private final float[] mViewMatrix = new float[16];//摄像机位置朝向9参数矩阵

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
```



##### 绘制方法glDrawArrays：

```java
// 第一个参数表示绘制方式，
// 第二个参数表示偏移量，
// 第三个参数表示顶点个数
GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, shapePos.length/3);

// 坐标顶点
float squareCoords[] = {
    -0.5f,  0.5f, 0.0f,   // top left
    -0.5f, -0.5f, 0.0f,   // bottom left
    0.5f, -0.5f, 0.0f,   // bottom right
    0.5f,  0.5f, 0.0f,
    0.75f, 0.5f,0.0f,
    0.75f, -0.5f, 0.0f,
};
```

###### int GL_POINTS       

> 将传入的顶点坐标作为单独的点绘制



###### int GL_LINES      

> 将传入的坐标作为单独线条绘制，ABCDEFG六个顶点，绘制AB、CD、EF三条线

![](..\images\line.png)



###### int GL_LINE_STRIP 

>   将传入的顶点作为折线绘制，ABCD四个顶点，绘制AB、BC、CD三条线

![LINE_TRIP](..\images\LINE_TRIP.png)

###### int GL_LINE_LOOP  

> 将传入的顶点作为闭合折线绘制，ABCD四个顶点，绘制AB、BC、CD、DA四条线。

![line_loop](..\images\line_loop.png)

###### int GL_TRIANGLES 

> 将传入的顶点作为单独的三角形绘制，ABCDEF绘制ABC,DEF两个三角形

![line_triangle](..\images\line_triangle.png)

###### int GL_TRIANGLE_FAN    

> 将传入的顶点作为扇面绘制，ABCDEF绘制ABC、ACD、ADE、AEF四个三角形

![LINE_FAN](..\images\LINE_FAN.png)

###### int GL_TRIANGLE_STRIP   

> 将传入的顶点作为三角条带绘制，ABCDEF绘制ABC,BCD,CDE,DEF四个三角形

![triangle_line](..\images\triangle_line.png)

## 6.投影和相机视图

<img src="E:\FirstCode\images\投影和相机视图.png" alt="投影和相机视图" style="zoom:80%;" />



### 投影：

> 使用OpenGl绘制的3D图形，需要展示在移动端2D设备上，这就是投影。

#### 正交投影

> 投影物体不会随观察点的远近而发生变化

```java
Matrix.orthoM (float[] m,           //接收正交投影的变换矩阵
                int mOffset,        //变换矩阵的起始位置（偏移量）
                float left,         //相对观察点近面的左边距
                float right,        //相对观察点近面的右边距
                float bottom,       //相对观察点近面的下边距
                float top,          //相对观察点近面的上边距
                float near,         //相对观察点近面距离
                float far)          //相对观察点远面距离
```

#### 透视投影

> 随观察点的距离变化而变化，观察点越远，视图越小，反之越大

```java
Matrix.frustumM (float[] m,         //接收透视投影的变换矩阵
                int mOffset,        //变换矩阵的起始位置（偏移量）
                float left,         //相对观察点近面的左边距
                float right,        //相对观察点近面的右边距
                float bottom,       //相对观察点近面的下边距
                float top,          //相对观察点近面的上边距
                float near,         //相对观察点近面距离
                float far)          //相对观察点远面距离
```

### 相机视图

> 你站的高度，拿相机的位置，姿势不同，拍出来的照片也就不一样，相机视图就是来修改相机位置，观察方式以及相机的倾斜角度等属性
>
> - **相机位置**：相机的位置是比较好理解的，就是相机在3D空间里面的坐标点。
> - **相机观察方向**：相机的观察方向，表示的是相机镜头的朝向，你可以朝前拍、朝后拍、也可以朝左朝右，或者其他的方向。
> - **相机UP方向**：相机的UP方向，可以理解为相机顶端指向的方向。比如你把相机斜着拿着，拍出来的照片就是斜着的，你倒着拿着，拍出来的就是倒着的。

```java
Matrix.setLookAtM (float[] rm,      //接收相机变换矩阵
                int rmOffset,       //变换矩阵的起始位置（偏移量）
                float eyeX,float eyeY, float eyeZ,   //相机位置
                float centerX,float centerY,float centerZ,  //观察点位置
                float upX,float upY,float upZ)  //up向量在xyz上的分量
```

### 转换矩阵（变换矩阵）

> 用来将数据转为OpenGl ES可用的数据字节(也就是上面的数据转换)，转换矩阵 = 相机视图 * 投影设置的数据相乘，然后将此矩阵传给顶点着色器。
>
> 在顶点着色器中用传入的矩阵乘以坐标的向量，得到实际展示的坐标向量

```java
Matrix.multiplyMM (float[] result, //接收相乘结果
                int resultOffset,  //接收矩阵的起始位置（偏移量）
                float[] lhs,       //左矩阵
                int lhsOffset,     //左矩阵的起始位置（偏移量）
                float[] rhs,       //右矩阵
                int rhsOffset)     //右矩阵的起始位置（偏移量）
```



### 使用：

#### a. 定义透视投影矩阵

```java
GLSurfaceView.Renderer中：
     // 定义投影
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

@Override
public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES20.glViewport(0,0,width,height);

    float ratio = (float) width / height;

    // 这个透视投影矩阵被应用于对象坐标在onDrawFrame（）方法中
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
}
```



#### b.定义相机视图

```java
@Override
public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    // 2. 定义一个相机视图
    // Set the camera position (View matrix)
    Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    // 3. 计算转换矩阵
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

    // 调用图形类的绘制方法
    triangle.draw(mMVPMatrix);
}
```



#### c. 绘制投影矩阵

```java
// Use to access and set the view transformation
private int mMVPMatrixHandle;

// 顶点投影着色器
private final String vertexShaderCode =
    // This matrix member variable provides a hook to manipulate
    // the coordinates of the objects that use this vertex shader
    "uniform mat4 uMVPMatrix;" +
    "attribute vec4 vPosition;" +
    "void main() {" +
    // the matrix must be included as a modifier of gl_Position
    // Note that the uMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    "  gl_Position = uMVPMatrix * vPosition;" +
    "}";

public void draw(float[] mvpMatrix) {
    // 1.激活program
    GLES20.glUseProgram(mProgram);

    // 2.1获取顶点着色器的位置的句柄
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

    // 3.2 启用三角形顶点位置的句柄
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
    //***********************************

    // 5.绘制三角形
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    // 6.禁用顶点数组
    GLES20.glDisableVertexAttribArray(mPositionHandle);
}
```

<img src="E:\FirstCode\images\Screenshot_20201231_111742_com.example.myapplication.jpg" alt="Screenshot_20201231_111742_com.example.myapplication" style="zoom:20%;" />

## 7.添加动作



## 方法

| 方法名                                             | 解释                                                         |
| -------------------------------------------------- | ------------------------------------------------------------ |
| glViewport(x,y,w,h)                                | 前两个参数控制窗口左下角的位置,后两个参数控制渲染窗口的宽度和高度（像素） |
| glClearColor(0.2f, 0.3f, 0.3f, 1.0f);              | 设置清空屏幕所用的颜色                                       |
| glClear(GL_COLOR_BUFFER_BIT);                      | 每个新的渲染迭代开始的时候我们希望清屏，否则我们仍能看见上一次迭代的渲染结果,可以通过调用glClear函数来清空屏幕的颜色缓冲，它接受一个缓冲位(Buffer Bit)来指定要清空的缓冲，可能的缓冲位有GL_COLOR_BUFFER_BIT，GL_DEPTH_BUFFER_BIT和GL_STENCIL_BUFFER_BIT。                                                                      当调用glClear函数，清除颜色缓冲之后，整个颜色缓冲都会被填充为glClearColor里所设置的颜色 |
| GLES20.glEnableVertexAttribArray(mPositionHandle); | 以顶点属性位置值作为参数，启用顶点属性,顶点属性默认是禁用的  |
|                                                    |                                                              |
|                                                    |                                                              |
|                                                    |                                                              |
|                                                    |                                                              |
|                                                    |                                                              |



## 名词解释

| 名词                | 解释                                                         |
| ------------------- | ------------------------------------------------------------ |
| 向量(Vector)        | 一个向量有最多4个分量，每个分量值都代表空间中的一个坐标，它们可以通过`vec.x`、`vec.y`、`vec.z`和`vec.w`来获取。注意`vec.w`分量不是用作表达空间中的位置的（我们处理的是3D不是4D），而是用在所谓透视划分(Perspective Division)上。 |
| 预定义的gl_Position | 为了设置顶点着色器的输出，我们必须把位置数据赋值给预定义的gl_Position变量，它是`vec4`类型的 |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |



### 索引缓冲对象(Element Buffer Object，EBO,也叫Index Buffer Object，IBO)

要绘制一个矩形，就会生成如下的一个顶点集合

```java
GLfloat vertices[] = {
    // 第一个三角形
    0.5f, 0.5f, 0.0f,   // 右上角
    0.5f, -0.5f, 0.0f,  // 右下角
    -0.5f, 0.5f, 0.0f,  // 左上角
    // 第二个三角形
    0.5f, -0.5f, 0.0f,  // 右下角
    -0.5f, -0.5f, 0.0f, // 左下角
    -0.5f, 0.5f, 0.0f   // 左上角
};

可以看到，有几个顶点叠加了。我们指定了右下角和左上角两次！一个矩形只有4个而不是6个顶点，这样就产生50%的额外开销。
```

> EBO也是一个缓冲，它专门储存索引，OpenGL调用这些顶点的索引来决定该绘制哪个顶点

```java
GLfloat vertices[] = {
    0.5f, 0.5f, 0.0f,   // 右上角
    0.5f, -0.5f, 0.0f,  // 右下角
    -0.5f, -0.5f, 0.0f, // 左下角
    -0.5f, 0.5f, 0.0f   // 左上角
};

GLuint indices[] = { // 注意索引从0开始! 
    0, 1, 3, // 第一个三角形
    1, 2, 3  // 第二个三角形
};
```



# 着色器结构

一般结构

```java
in type in_variable_name;
in type in_variable_name;

out type out_variable_name;

uniform type uniform_name;

int main()
{
  // 处理输入并进行一些图形操作
  ...
  // 输出处理过的结果到输出变量
  out_variable_name = weird_stuff_we_processed;
}
```





# 参考文档：

https://blog.csdn.net/qq_32175491/article/details/79091647

https://learnopengl-cn.readthedocs.io/zh/latest/

https://blog.csdn.net/xk7298?t=1

https://blog.csdn.net/junzia/article/details/52817978

https://www.e-learn.cn/content/qita/2810770