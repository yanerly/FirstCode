[toc]



# 一、了解

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

##### 什么是path环境变量：

windows在执行命令时要找的路径



##### 为什么要配置环境变量？

为了在任意路径下都可以执行java开发工具(java.exe,javac.exe)



##### 配置

```jav
JAVA_HOME：C:\ProgramFile\Java\jdk-13.0.1

PATH：%JAVA_HOME%\bin

​		     %JAVA_HOME%\jre\bin

CLASS_PATH：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar;



然后cmd,执行命令 javac,  java,  java -version
```







#### 注释（Comment）:

##### 单行注释：//

##### 多行注释：/** */

##### 特点：

1. 单行注释和多行注释不参与编译，编译以后生成的.class文件中不包含注释掉的内容

2. 文档注释可以被JDK提供的java dic所解析，提供一套说明文档

3. 多行注释不可以嵌套使用

   

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

   编译：用javac.exe命令编译源文件生成以 .class结尾的字节码文件，格式：javac  源文件名.java

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

   

#### Scanner

1. 用法：可以从键盘获取不同类型的输入变量

2. 使用

   ```java
   导包： import java.util.Scanner
       
   Scanner实例化：Scanner scan = new Scanner(System.in);
   
   调用Scanner类的相关方法获取指定的类型
    String name = scan.next();
    int age = scan.nextInt();
    double height = scan.nextDouble();
    boolean isMale = scan.nextBoolean();
    
   注意：Scanner没有获取char类型的接口
   ```

3. 需要根据相应的方法，来输入指定类型的数据，如果输入的数据和要求的数据类型不一致，可能会出现异常

   

#### 分支结构

##### if-else结构

###### 结构：

```java
写法1：单选
    if(){
        
    }
写法2：二选一
    if(){
        
    }else{
        
    }
写法3：多选一
    if(){
        
    }else if(){
        
    }else if(){
        
    }else{
        
    }
```



###### 说明：

1. else结构是可选的

2. 如果多条件表达式之间是互斥的（或没有交集的），哪个If在前都没有关系

3. 如果多条件表达式之间有交集关系，就要考虑哪个在上面

4. 如果多条件表达式之间有包含关系，通常范围小的声明放在范围大的上面，否则范围小的没有机会

5. if-else可以嵌套使用

6. 当if-else只有一行的时候，{}可以省略，但是不建议这么写

7. else会和他最近的If进行匹配

   ```java
   int x = 4,y = 2;
   if(x > 2)
       if(y>0)
    else sop("test") ;// 跟着最后一个if
   ```

   

##### switch-case结构

###### 结构：

```java
swich(表达式)
    case 常量：
    	//执行语句 1
    	break;
    case 常量：
        //执行语句 2
        break:
	default:
 		//执行语句
    	// break 
```



###### 说明：

1. 根据switch表达式的值，会一次匹配各个case中的常量，一旦匹配成功，则进入对应的case结构中，调用其执行语句。当调用完执行语句后，没有遇到break，则继续执行下一个case结构中的执行语句，直到遇到break为止或者运行到末尾结束为止。

2. break可以使用在switch-case结构中，表示一旦执行到此关键字，就跳出switch-case结构

3. switch表达式中只能是以下6种数据类型：byte、short、char、int、枚举(JDK5.0新增)、String(JDK 7.0新增)

4. case后只能是**常量**，不能是范围或表达式

5. break关键字是可选的，建议加上

   ```java
   System.out.println("输入月：");
   int month = scan.nextInt();
   
   System.out.println("输入日：");
   int day = scan.nextInt();
   
   int sumDay = 0;
   
   switch(month){
       case 12:
           sumDay += 31;
       case 11:
           sumDay += 30;
       case 10:
           sumDay += 31;
       case 9:
           sumDay += 30;
       case 8:
           sumDay += 31;
       case 7:
           sumDay += 30;
       case 6:
           sumDay += 31;
       case 5:
           sumDay += 30;
       case 4:
           sumDay += 31;
       case 3:
           sumDay += 28;
       case 2:
           // 2月加上1月的天数
           sumDay += 31;
       case 1:
           sumDay += day;
   }
   // 4月3日是第93天
   System.out.println(month+"月"+day+"日是第"+sumDay+"天");
   ```

   

6. default：相当于If-else中的else，default结构也是可选的。且位置是灵活的，可以在开头，中间或结尾

   ```java
   int num = 5;
   switch(num){
       default:
           System.out.println("other");
   
       case 0:
           System.out.println("0");
           break;
       case 1:
           System.out.println("1");
           break;
   		}
   ```

   **会打印 other、0；default放在开头，也会先从case开始找，找不到了才走default，遇到break结束**

   

7. 如果switch-case中的多个语句一样，则可以进行合并，结构如下：

   ```java
   switch(表达式)
       case 1:
   	case 2:
   		//执行语句
   		break;
   	case 3:
   	case4:
   		break;
   ```

   

8. 用switch-case判断60分以上及格，60以下不及格

   ```java
   switch(num / 60){
       case 0:
           System.out.println("fail");
           break;
       case 1:
           System.out.println("pass");
           break;
   		}
   ```

   

##### if-else和switch-case的比较

1. 凡是可以用switch-case表达的，能能用if-else，反之不行

2. 既可以用if-else又可以用switch-case的情况，优先使用**switch-case**，效率更高

   

#### 循环结构

循环：

在满足某些条件下，反复执行某个操作



##### 循环结构4要素：

①初始化条件：是boolean类型，只执行一次

②循环条件

③循环体

④迭代条件



##### 1. for循环

```java
for(①;②;④){
    ③
}

--------------------
下面这种写法也是对的：
int num = 1;
for(System.out.print('a');num < 5;System.out.print('c'),num++){
    System.out.print('b');
}
// a bc bc bc bc
```



##### 2. while循环

```java
①
while(②){
    ③
    ④
}
```



##### 3. do-while循环

```java
①
do{
    ③
    ④
}while(②)；
执行过程：①-③④-②，会先执行一次循环体
```



###### 对比：

1. 两个while和for可以互相转换，只是初始化条件部分的作用范围不同
2. while不能丢了④循环条件，否则会出现死循环
3. do-while至少会执行一次循环体



###### 总结

1. 不限制循环次数的结构：for(;;)或while(true);

2. 结束循环的两种方式：

- 在循环条件部分返回false;
- 在循环体中，执行break;



##### 4. l嵌套循环（一般不会超过三层）

1. 将一个循环A声明在另一个循环B的循环体中
2. 分类：
   - 外层循环：B
   - 内层循环：A
3. 说明：
   - 内层循环遍历一遍，只相当于外层循环执行一次
   - 假设外层循环执行m次，内层循环执行n次，相当于一共执行了m * n次
   - 外层控制循环行数，内层控制循环列数



打印0~100内的所有质数：

```java
// 质数：只能被1和它本身整除
long start = System.currentTimeMillis();
for(int i =2;i <= 100;i++){
    boolean isFlag = true;

    for(int j= 2;j <= Math.sqrt(i);j++){
        if(i % j == 0){
            // 能被整除
            isFlag = false;
            break;
        }
    }

    if(isFlag){
        System.out.println(i);
    }
}
long end = System.currentTimeMillis();
```



##### break & continue:

- break: 可以在switch-case和循环结构中使用，用于结束**当前循环体，后面不能声明执行语句**

- continue: 可以在循环结构中使用**当次循环体，后面不能声明执行语句**

- 如果嵌套多层，break和continue都**默认跳出包裹此关键字的那一层循环**

  

```java
for(int i =1;i<= 10;i++){
    if(i % 4 == 0){
        //break; // 1,2,3
        continue; // 1,2,3,5,6,7,9,10
        //System.out.println(i); // 编译报错
    }
    System.out.println(i);
}
```

- break lable ：结束指定标识的一层循环结构

  continue lable：结束指定标识的一层循环结构当次循环

```java
lable: for(int j=0;j<4;j++){
    for(int i =1;i<= 10;i++){
        if(i % 4 == 0){
            break lable; //123
            //continue lable; //123123123123
        }
        System.out.print(i);
    }
    System.out.println();
}
```



#### 数组

##### 1.概念：

   ```java
   将相同数据类型的数据，按照一定的顺序排列的集合，并使用一个名字命名，使用下标对这些数据进行统一的管理
   ```

   

##### 2.特点：

   - 数组是有序排列的

   - 数组是**引用数据类型**，而数组的元素**可以使基本数据类型，也可以是引用数据类型**

   - 创建数组对象会在内存中创建一块**连续的空间，而数组名中引用的是这块连续空间的首地址**

   - **数组的长度一旦确定，就不能修改了。**

     

##### 3. 分类：

   ###### 维度：一维数组，二维数组...

   ###### 按元素的类型：基本数据类型的数组，引用数据类型的数组



##### 4.一维数组的使用：

###### 初始化

```java
int[] array; // 声明数组
array = new int[]{1,2,3};//静态初始化：数组初始化和数组元素赋值同时进行
array = new int[5];		 //动态初始化：数组初始化和数组元素赋值分开进行
```



###### 数组长度

```java
int chang = array.length;// 不是方法
```



###### 数组元素的初始化值

基本数组类型：

- int：0
- 浮点型：0.0
- char型：数字0或者'\u0000'，不是'0'
- boolean型：false

引用数据类型：null



###### 内存解析

```java
int[] arr = new int[]{1,2,3};
String[] arr1 = new String[4];
arr1[0] = "AA";
arr1[1] = "BB";
arr1 = new String[3];
// arr1[0] 是null;
```

![解析](E:\FirstCode\笔记\内存解析.png "解析")

##### 5.二维数组

###### 定义：

相当于给每一个元素都传一个一维数组进去



###### 声明和初始化

```java
// 1. 静态初始化
// 三行三列
// 元素1：1,2,3
// 元素2：1,2
// 元素3：1,2,3
int[][] arr = new int[][]{{1,2,3},{1,2},{1,2,3}};
// 等价于
//int[][] arr = {{1,2,3},{1,2},{1,2,3}}; // 类型推断

// 动态初始化：
int[][] arr2 = new int[3][2]; // 3行2列
int[][] arr3 = new int[3][];

//这种写法也是正确的
int[] arr2[] = new int[3][2]; // 3行2列
```



调用数组指定位置的元素：

```java
第2行第二列元素 arr2[1][1]
```



###### 获取数组的长度

```
System.out.println(arr2.length+"");
System.out.println(arr2[0].length+"");//第一个元素的长度
```



###### 遍历

```java
for (int i=0;i<arr.length;i++){
    for (int j =0;j <arr[i].length;j++){
        System.out.print(""+arr[i][j]);
    }

    /**
     * 123
     * 12
     * 123
     */
    System.out.println();
    
}
```



###### 初始化值

二维数组分为外层数组元素，和内层数组元素

```
int[][] arr = new int[4][3];
外层元素：arr[0]、arr[1]、arr[2]、arr[3]等

内层元素：arr[0][1]、arr[0][2]、arr[0][3]等
```

针对初始化方式1：

```java
int[][] arr = new int[4][3];
外层元素的初始化值为：地址，如：arr[0]为[I@地址值，arr为[[@地址值
内层元素的初始化值为：与一位数组一样                               
```

针对初始化方式2：

```java
int[][] arr = new int[4][];
外层元素的初始化值为：null
内层元素的初始化值为：不能调用，否则报错 
```



###### 内存解析

![二维数组](E:\FirstCode\笔记\二维数组内存解析)



###### 复制

```java
int[] arr1,arr2;
arr1 = new int[]{1,2,3};
// 不是赋值，只是将arr1的地址值给了arr2，操作的是同一个数组
arr2 = arr1;
```



##### 6.数组的工具类Array

```
Arrays.equals(int[] a,int[] b); // 判断两个数组是否相等
Arrays.toString(arr); // 输出数组信息
Arrays.fill(arr,10);//将指定的数填充到数组中
Arrays.sort(arr);// 对数组进行排序
Arrays.binarySearch(arr,20);// 对排序后的数进行二分查找
```



##### 7.数组中的常见异常：

- 数组越界

- ##### 空指针异常

  [TOC]

  



##### 数据结构

###### 定义

1. 数据和数据之间的逻辑关系：集合、一对一、一对多、多对多

2. 存储结构：
   线性表：顺序表（数组）、链表、栈、队列

   树形结构：二叉树

   图形结构



###### 算法的5个特性：

- 输入
- 输出
- 有穷性
- 确定性
- 可行性





###### 数组复制

```java
String[] arr = new String[]{"AA","BB","CC","DD","EE","FF"};
// 复制
for (int i=0;i<arr.length;i++){
    System.out.print(arr[i]+"\t");
}

System.out.println();

// 和arr长度一样
String[] arr2 = new String[arr.length];
for (int i=0;i<arr2.length;i++){
    arr2[i] = arr[i];
    System.out.print(arr2[i] +"\t");
}
```



###### 数组的反转(定义一个中间变量)

```java
// 方法1：定义一个中间遍历，交换值 0 1 2 3 4
for (int i=0;i<arr.length / 2 ;i++){
    String temp = arr[i];
    arr[i] = arr[arr.length-1-i];
    arr[arr.length-1-i] = temp;
}

for (int i=0;i<arr.length;i++){
    System.out.print(arr[i]+"\t");
}

// 写法2：两头开始比较
for (int i = 0,j = arr.length - 1;i<j;i++,j--){
    String temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```



###### 线性查找：for循环依次比较



###### 二分查找：前提是数据要有序

```java
 nt[] arr = new int[]{11,22,33,44,55,66,77,88};
int target = -33;
int head = 0;// 头指针位置
int end = arr.length - 1;//尾指针位置

boolean isFlag = true;
while (head < end){
    int middle = end - head; // 中间位置
    if (target == arr[middle]){
        isFlag = false;
        System.out.println(target+"下标是"+middle);
        break;
    }else if (target < arr[middle]){
        end = middle - 1;
    }else {
        head = middle +1;
    }
}

if (isFlag){
    System.out.println("没有找到");
}
```



###### 十大内部排序算法

- 选择排序：直接选择排序、堆排序

- 交换排序：

  - 冒泡排序

    

  - 快速排序

- 插入排序：直接插入排序、折半插入排序、Shell排序

- 归并排序

- 桶式排序

- 基数排序



# 面向对象：

一、概念：

1. 面向过程和面向对象的对比(把大象装进冰箱):

- 面向过程，强调的是行为，以函数为最小单位，考虑怎么做

  先打开们，再放大象

- 

2. 