[toc]



## 一、了解

#### 比特（bit）和字节（byte）

计算机是一系列的电路开关，每个开关都有两个状态：关和开，如果电路是开的，值为1；电路是关，值是0；

#### 字节：

是最**基本**的存储单元，每个字节=8bit



#### 比特bit:

一个0或者1存储为一个比特，是计算机**最小**存储单元。



#### 单位换算：

千字节  KB = 1024B

兆字节 MB = 1024KB

千兆字节 GB = 1024MB

万亿字节 TB = 1024GB



#### 运行速度：

CPU > 内存 >硬盘

比如要显示一张图片，会把图片加载到内存中，CPU在内存中运行显示图片



#### 像素密度：

像素密度ppi = 平方根 (长²+宽²)/ 屏幕尺寸



#### 鼻祖：

图灵，冯诺依曼



#### 操作系统：

用户<---->应用程序<---->操作系统<----->硬件



#### 万维网：

互联网包括因特网，因特网包括万维网





#### 架构方式：

B/S: 通过浏览器访问

C/S: 通过客户端访问



# 二、基础

#### dos命令：

```java
dir : 列出目录下的所有文件
md  : 创建目录
rd  : 删除目录（目录必须为空）
cd  : 进入指定目录
cd..: 返回上一级目录
cd\ : 返回根目录
del : 删除文件或目录
exit: 退出dos命令行
echo hello > 1.doc:创建1.doc,并输入文本
```



#### java简史：

2004：发布里程碑式版本，JDK 1.5,后更名为5.0

2014：发布JDK8.0，是继5.0



#### JDK、JRE、JVM关系

JDK （编译）= JRE + 开发工具集（javac.exe, java.exe, javadoc.exe）

JRE（运行） = JVM + java se核心类库



#### 配置java环境变量

JAVA_HOME：C:\ProgramFile\Java\jdk-13.0.1

PATH：%JAVA_HOME%\bin

​		     %JAVA_HOME%\jre\bin

CLASS_PATH：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar;



然后cmd,执行命令 javac,  java,  java -version





#### 注释（Comment）:

##### 单行注释：//

##### 多行注释：/** */

##### 特点：

1. 单行注释和多行注释不参与编译，编译以后生成的.class文件中不包含注释掉的内容

2. 文档注释可以被JDK提供的java dic所解析，提供一套说明文档

   

##### 文档注释：java特有

/** 

@author

*/

1. 生成javadoc,  javadoc -d 存放文档的路径   java的源文件 
2. 如果一个类需要使用javadoc工具生成一个软件的开发者文档，那么该类必须使用public修饰。
3. 文档注释注释的内容一般都是位于类或者方法的上面的。



#### 写一个Java文件的过程

1. 过程 编写-编译-运行

   编写：将java代码保存在一个以 “.java” 结尾的源文件中

   编译：用javac.exe命令编译源文件以 .class结尾的字节码文件，格式：javac  源文件名.java

   运行：用Java.exe命令解释运行字节码文件，格式：java  类名

   

2. 在一个java源文件中可以声明多个class，但是最多有一个类声明为Public。而且声明为Public的类名要与源文件名一致；

3. main() 是程序的入口，固定写法是

   ```
   public static void main(String[] args){}
   或者
   public static void main(String a[]){}
   ```

   

4. 输出语句：

   ```java
   System.out.print(); // 只输出数据，不换行
   System.out.println();//输出数据，然后换行
   ```

   