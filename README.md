# 项目中已完成的需求

## 基本需求

+ 界面做好看一点，界面做得好看也是技术活(已完成)

+ **必须使用** MVP 模式构造 APP(已完成)
+ APP 的主页包含 **首页文章**、**知识体系**、**项目** 三个模块(已完成)
+ 首页文章模块从上到下依次为 banner 轮播图、置顶文章和普通文章(已完成)
+ banner 轮播图可以 **自动轮播** ，并且可以手动滚动，手动滚动时应该停止自动轮播(已完成)
+ 知识体系模仿玩安卓 APP 制作(已完成)
+ 项目界面需要展示每个项目的图片(已完成)
+ 三个模块中的文章能点击进入查看详情，用 WebView 显示文章的具体内容，并在加载时 **显示进度条**(已完成)
+ 实现搜索功能(已完成)
+ 搜索界面展示搜索热词(已完成)
+ 展示搜索结果 **不要跳转** 到新的 activity ，而是直接在当前 activity 展示(已完成)
+ 展示列表时，实现 **分页加载** ，滑动到底部加载更多(已完成)
+ 实现注册和登录功能
+ 封装网络请求的代码（只传入请求信息即可，比如url，用户名和密码，查找的字段）(已完成)

## 进阶需求

+ 使用 **线程池** 实现线程复用，不要每一次都新建线程来使用(已完成)
+ **适配深色模式**(已完成)
+ 实现 **自动登录** 功能，不是每次打开就调用登录接口登录，而是保存 cookies 来复用(已完成)
+ 实现收藏和取消收藏文章功能，收藏本站文章即可(已完成)
+ 展示收藏列表，查看自己已收藏的文章，点击可以查看文章详情(已完成)
+ 实现 **文章列表的本地缓存** 功能(已完成)
+ 增加 **最近浏览的文章记录** ，浏览记录保存在本地即可(已完成)

## 升华需求

+ 搜索热词使用 **流式布局** 展示，不可使用依赖，请学习如何自定义 View(已完成)
+ 每天定时推送一篇最新的文章(已完成)

---

# 开发笔记

## 1 使用adb获取设备上的Log日志(Android调试桥) 

当用真机测试app崩溃了，且没有连接电脑，而这个bug很难复现的时候，可以使用该方法

* 1.下载，安装adb
* 2.针对这种情况，可使用命令：
* `adb logcat | find "XXX" >D:\myLog2.txt（示例）`
* 因为打印的信息一般很多，可以将`XXX`写成包名来找到刚才崩溃的app的信息 `>D:\myLog2.txt` , 打印到指定文件 
* 3.更多adb命令可以查看官网，博客，以及`adb logcat –help`命令

## 2 Android Studio中的调试功能

### 2.1 一些按钮

* F8 --> `step over` 一步一步走
* F7 --> `step into，force step into` 看到方法直接往里走
* `step out` 如果当前所在的方法里还有新点，则直接跳到下一个断点:如果没有，则跳出该方法
* `drop frame` 跳回到当前方法的开头
* `run to cursor` 跳到下一个断点

### 2.2 断点设置

[19.专题9：条件断点与异常断点_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1CK411d7aA/?p=19&vd_source=f31d3c4106b3bfcd0e119cf9ef0aab5b)

#### 2.2.1 条件断点

`Java Line Breakpoints`

在condition里面可以设置对应的变量值

#### 2.2.2 异常断点

`Java Exception Breakpoints`

若调试时出现的异常，可以"保护现场"

---

## 3 视频学习笔记

[【Android进阶】MVP模式的高复用低耦合你了解多少？_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1JQ4y1X7QE/?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click)

### 3.1 MVP架构思想和优势

1.为什么要使用架构?

* 设计方面:模块化功能
* 编码方面:提供开发效率、复用、规范
* 测试方面:提高测试效率
* 维护方面:便于维护升级

2.MVP的特征是从设计层面就直接避免了M和V的直接通信

* MVC
  * M: Model
  * V: Layout等Resource
  * C: Activity
* MVP
  * M: Model
  * V: Layout等Resource、Activity
  * P：

Activity通过接口去请求P层（需要设计一系列的接口）

P层将M层的数据通过接口反馈给V层（需要设计一系列的接口）

![picture/MVP架构.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVP架构.png)

---

## 4 解绑服务后再次解绑会抛出异常

但是如果解除绑定后再点击给解除绑定的指令（项目中就是点击按钮解除绑定），会报这个错误：

`java.lang.IllegalArgumentException: Service not registered`

错误提示Service没有注册，事实上在Manifest.xml中已经注册过这个Service了。

unbindService()方法传入的connection参数不能为null，也就是必须有绑定存在，才能解绑，项目中绑定成功后第一次点击解绑不会报错，解绑后这个参数就是null了，再次点击解绑就会报错，那么我们为解绑加一个判断就可以了。

```java
//Activity的成员变量
private boolean bound=false;

case R.id.bind_service:
    Intent bindIntent=new Intent(this,MyService.class);
    bound=bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务
    break;
case R.id.unbind_service:
    if (bound){
        unbindService(connection);//解绑服务
    }
    break;
```

---

## 5 startForeground需要android.permission.FOREGROUND_SERVICE权限

Android 9 及以上的版本：

想要使用前台服务的应用必须首先请求FOREGROUND_SERVICE权限。这是普通权限，因此系统会自动将其授予请求的应用程序。未经许可启动前台服务会引发SecurityException。

**解决的方法是在中添加以下内容`AndroidManifest.xml`：**

```xml
<manifest ...>
     ...
     <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
     ...
     <application ...>
     ...
</manifest>
```

---

## 6 Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified异常

`PendingIntent.getActivity()`方法中抛出该异常

解决方法：

* 将项目的targetSdkVersion由31改为30

* 如果不想改targetSdkVersion，那就在创建PendingIntent的时候判断当前系统版本，根据不同系统版本创建带有不同flag的PendingIntent，具体代码实现如下：**（最好的解决办法）**

  ```java
  PendingIntent pendingIntent;
  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
      pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
  } else {
      pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_ONE_SHOT);
  }
  ```

* 直接在项目中依赖下面的库

  ```
  dependencies {
    // For Java
    implementation 'androidx.work:work-runtime:2.7.1' 
   
    // For Kotlin
    implementation 'androidx.work:work-runtime-ktx:2.7.1'
  }
  ```

---

## 7 通知弹出异常

`android.app.RemoteServiceException: Bad notification for startForeground`

或

Toast弹出报错：`Developer warning for package xxx，Failed to post notification on channel "null"`

在Android8.0及以上的版本使用Notificatio通知要先设置通知渠道id，否则就无法显示。

我的解决方法：

```java
@Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService","onCreate executed");
        String id="com.hongyongfeng.servicetest";
        String name="channel one";
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        NotificationCompat.Builder notification; //创建服务对象
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("id", "123", NotificationManager. IMPORTANCE_HIGH);
            manager.createNotificationChannel(mChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(MyService.this,"id").setChannelId(id);
        } else {
            notification = new NotificationCompat.Builder(MyService.this,"id");
        }

//        Notification notification=new NotificationCompat.Builder(this, "id")
        notification
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
            .build();
        Notification notification1 = notification.build();
        startForeground(1,notification1);
        //换成manager.notify(1,notification1);也同样可以
    }
```

官方文档：

[创建通知  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/training/notify-user/build-notification?hl=zh-cn#java)

必须先通过向 `createNotificationChannel()` 传递 `NotificationChannel` 的实例在系统中注册应用的[通知渠道](https://developer.android.google.cn/training/notify-user/channels?hl=zh-cn)，然后才能在 Android 8.0 及更高版本上提供通知。因此以下代码会被 `SDK_INT` 版本上的条件阻止：

```java
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
```

由于您必须先创建通知渠道，然后才能在 Android 8.0 及更高版本上发布任何通知，因此应在应用启动时立即执行这段代码。反复调用这段代码是安全的，因为创建现有通知渠道不会执行任何操作。

请注意，`NotificationChannel` 构造函数需要一个 `importance`，它会使用 `NotificationManager` 类中的一个常量。此参数确定出现任何属于此渠道的通知时如何打断用户，但您还必须使用 `setPriority()` 设置优先级，才能支持 Android 7.1 和更低版本（如上所示）。

虽然必须按本文所示设置通知重要性/优先级，但系统不能保证您会获得提醒行为。在某些情况下，系统可能会根据其他因素更改重要性级别，并且用户始终可以重新定义指定渠道适用的重要性级别。

如需详细了解不同级别的含义，请参阅[通知重要性级别](https://developer.android.google.cn/training/notify-user/channels?hl=zh-cn#importance)。

### 注意：

在manifest.xml文件中要加入以下权限：

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<!-- 适用于startForeground(1,notification1);-->

<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<!-- 适用于manager.notify(1,notification1);-->
```

### 另外：

在模拟器上默认允许了通知这一项权限，但在真机上默认是禁止通知这个权限的，要手动在设置里面打开。

---

## 8 安卓11以上写入文件到sd卡出现异常

`W/System.err: java.io.FileNotFoundException: /storage/emulated/0/Download/xxx: open failed: EPERM (Operation not permitted)`

待解决

下载小的文件比如照片就可以成功，下载大的文件比如安装包就不行。



---

## 9 解决循环引用，导致不能正确回收内存，引起内存泄漏的问题

一些大的内容或者一些频繁使用的内容可以加上弱引用

例如Activity中引用了Presenter，而Presenter通过构造函数又引用了Activity，

执行Activity的onDestroy方法的时候，先回收Presenter，发现需要回收Presenter中的属性view，该属性又是Activity，然后又要回收Presenter....进入了死循环，导致释放内存的时候出现问题。

注意：Activity和Fragment之间也存在相互引用，进行内存回收的时候，也是类似的情况。但他没有引起内存泄漏，完美的解决了这个问题。

Fragment加载到Activity中会用到两个函数：

`onAttach()`Fragment的绑定

`onDetach()`Fragment的解绑

将Fragment的生命周期和Activity的生命周期进行同步。



解决方法：

执行Activity的onDestroy方法的时候，顺便执行解绑和置空操作。

广播的绑定（注册）与解绑也可以用这种方法



---

## 10 泛型

### 10.1 概念

泛型:是一种未知的数据类型,当我们不知道使用什么数据类型的时候，可以使用泛型。

泛型也可以看成是一个变量,用来接收数据类型。

- E e：Element 元素
- T t ：Type 类型

ArrayList集合在定义的时候，不知道集合中都会存储什么类型的数据，所以类型使用泛型。

E:未知的数据类型。

```java
public class ArraylList<E>{
    public boolean add(E e){}

    public E get(int index) {}
}
```



创建集合对象的时候,就会确定泛型的数据类型，例如：

`ArrayList<String> list = new ArrayList<String>();`

会把数据类型作为参数传递，把String赋值给泛型E。

然后就变成了：

```java
public class ArraylList<String>{
    public boolean add(String e){}
	public String get(int index) {}
｝
```

String类型也可以换成任意的自定义类型，如Student类

```java
ArrayList<Student> list =new ArrayList<Student>();
public class ArraylList<Student>{
    public boolean add(Student e){}
	public Student get(int index) {}
｝
```

------

### 10.2 使用泛型的好处

创建集合对象，不使用泛型。

好处：

集合不使用泛型，默认的类型就是Object类型,可以存储任意类型的数据。

弊端:

不安全，会引发异常。

```java
private static void method() {
    ArrayList<Object> list=new ArrayList<>();
    //即使这里不写Object，留空，也默认为Object
    list.add("abc");
    list.add(1);
    System.out.println(list);
    Iterator<Object> it= list.iterator();
    //即使这里不写Object，留空，也默认为Object
    while (it.hasNext()){
    //取出的元素也是Object类型
        Object obj=it.next();
        System.out.println(obj);
        //想要使用string类特有的方法,length获取字符串的长度，但事实上不能使用，因为需要进行向下转型。
        String str=(String)obj;
        System.out.println(str.length());
        //在输出数字1的长度时会抛出CLassCastException类型转换异常,不能把Integer类型转换为String类型。但前面是正常运行的
    }
}
```

------

创建集合对象，使用泛型

好处:

1.避免了类型转换的麻烦,存储的是什么类型,取出的就是什么类型

2.把运行期异常(代码运行之后会抛出的异常),提升到了编译期(写代码的时候会报错)

弊端:

泛型是什么类型，只能存储什么类型的数据。

```java
private static void method05() {
        ArrayList<String> list=new ArrayList<>();
        list.add("abc");
        list.add("bhg");
        Iterator<String> it=list.iterator();
        while (it.hasNext()){
            String s=it.next();
            System.out.println(s);
            System.out.println(s.length());
        }
    }
```

------

### 10.3 定义和使用含有泛型的类

定义一个含有泛型的类,模拟ArrayList集合

泛型是一个未知的数据类型,当我们不确定用什么数据类型的时候,可以使用泛型。

泛型可以接收任意的数据类型,可以使用Integer,String,Student.等

创建对象的时候确定泛型的数据类型。

```java
public class Demo03<E> {
    private E name;
    public E getName() {
        return name;
    }
    public E setName(E name) {
        this.name = name;
        return this.name;
    }
}
```

```java
public static void main(String[] args) {
    Demo03<String> obj=new Demo03<>();
    obj.setName("jim");
    String name = obj.getName();
    System.out.println(name);
    Demo03<Integer> obj1=new Demo03<>();
    obj1.setName(1);
    System.out.println(obj1.getName());
}
```

------

### 10.4 定义和使用含有泛型的方法

定义含有泛型的方法:

泛型定义在方法的修饰符和返回值类型之间

格式:

修饰符<泛型>返回值类型 方法名(参数列表(使用泛型)){方法体;}

含有泛型的方法，在调用方法的时候确定泛型的数据类型

传递什么类型的参数，泛型就是什么类型。

```java
public <E> void method01(E e){
    System.out.println(e);
}
public <E> void method01(E e){
    System.out.println(e);
}
```

```java
public class Generic {
    public static void main(String[] args) {
        //创建GenericMethod对象
        GenericMethod g=new GenericMethod();
        //调用含有泛型的方法method01
        //传递什么类型,泛型就是什么类型
        g.method01(1);
        GenericMethod.method("jdh");
        //静态方法的使用不用new一个对象来调用。
    }
}
```

------

### 10.5 定义和使用含有泛型的接口

两种接口都要写上泛型。

#### 第一种使用方式:

定义接口的实现类，实现接口,指定接口的泛型。

格式：

```java
public interface Iterator<E> {
	E next();
}
```

Scanner类实现了Iterator接口,并指定接口的泛型为String,所以重写的next方法泛型默认就是String

```java
public final class Scanner implements Iterator<String>{
	public string next() {}
}
```

------

#### 第二种使用方式：

接口使用什么泛型,实现类就使用什么泛型，类跟着接口走。

这就相当于定义了一个含有泛型的类,创建对象的时候确定泛型的类型。

```java
public interface List<E>{
	boolean add(E e);
	E get(int index);
}
//例：
//注意：实现类也要记得写上泛型
public class Arraylist<E> implements List<E>{
    public boolean add(E e) {}
    public E get(int index){}
}
```

------

```java
public interface GenericInterface<E> {//定义含有泛型的接口
    public abstract void method(E e);
}
```

```java
public class GenericInterfaceImplements implements GenericInterface<String>{//第一种定义方式
    @Override
    public void method(String s) {
        System.out.println(s);
    }
}
```

```java
public class Demo02GenericInterface<I> implements GenericInterface<I>{//第二种定义方式
    @Override
    public void method(I e){
        System.out.println(e);
    }
}
```

```java
public class Demo01GenericInterface {
    public static void main(String[] args) {
        GenericInterfaceImplements g=new GenericInterfaceImplements();
        g.method("sdf");//第一种使用方式
        Demo02GenericInterface<String> str=new Demo02GenericInterface<>();
        str.method("jsdf");//第二种使用方式
        Demo02GenericInterface<Integer> str1=new Demo02GenericInterface<>();
        str1.method(1);
    }
}
```

------

### 10.6 泛型通配符

定义一个方法，能遍历所有类型的ArrayList集合。

这时候我们不知道ArrayList集合使用什么数据类型，可以用泛型的通配符**?**来接收数据类型。

注意:

泛型是没有继承概念的。泛型是不能进行自动提升的。

```java
public static void main(String[] args) {
    ArrayList<String> list=new ArrayList<>();
    list.add("sf");
    list.add("sdffsd");
    print(list);
    ArrayList<Integer> list1=new ArrayList<>();
    list1.add(12);
    list1.add(134);
    print(list1);
}
//下面的？号换成Object也不行，因为泛型是没有继承概念的。
private static void print(ArrayList<?> list) {
    Iterator<?> it= list.iterator();
    while (it.hasNext()) System.out.println(it.next());
}
```

------

泛型的上限限定: 

`? extends E`

代表使用的泛型只能是E类型的子类/本身

泛型的下限限定:

`? super E`

代表使用的泛型只能是E类型的父类/本身

类与类之间的继承关系

Integer extends Number extends Object

String extends Object

Number 是一个抽象类，也是一个超类（即父类）。Number 类属于 java.lang 包，所有的包装类（如 Double、Float、Byte、Short、Integer 以及 Long）都是抽象类 Number 的子类。 

```java
public static void main(String[] args) {
    ArrayList<Integer> list1=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    ArrayList<Number> list3=new ArrayList<>();
    ArrayList<Object> list4=new ArrayList<>();
    getList(list1);
    getList(list2);//报错
    getList(list3);
    getList(list4);//报错

    getList1(list1);//报错
    getList1(list2);//报错
    getList1(list3);
    getList1(list4);

}
// 泛型的上限: 此时的泛型?，必须是Number类型或者Number类型的子类
private static void getList(ArrayList<?extends Number> list4) {
}
// 泛型的下限: 此时的泛型?，必须是Number类型或者Number类型的父类
private static void getList1(ArrayList<?super Number> list4) {
}
```



---

## 11 反射

```java
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c1=Class.forName("winter_holiday_training_camp.day36.User");
        Class c2=Class.forName("winter_holiday_training_camp.day36.User");
        Class c3=Class.forName("winter_holiday_training_camp.day36.User");
        System.out.println(c3.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c1.hashCode());

        Person person=new Student("学生");
        System.out.println(person.name);
        Person person1=new Person();
        System.out.println(person1.name);
        //方式一：通过对象获得
        Class c4=person.getClass();
        System.out.println(c4.hashCode());

        //方式二：for name获得
        Class c5=Class.forName("winter_holiday_training_camp.day36.Student");
        System.out.println(c5.hashCode());

        //方式三：通过类名.class获得
        Class c6=Student.class;
        System.out.println(c6.hashCode());

        //方式四：基本内置类型的包装类都有一个Type属性
        Class c7=Integer.TYPE;
        System.out.println(c7.hashCode());
        System.out.println(c7);
        Class c8=person1.getClass();
        Class c9=c4.getSuperclass();
        System.out.println(c8);
        System.out.println(c9);
        System.out.println(c8.getSuperclass());
    }
}
class User{
    private String name;
    private int age;
}
class Person{
    String name="人";

    public Person() {
    }

    public Person(String name) {
        this.name=name;
    }
}

class Student extends Person{
    public Student(String name) {
        this.name=name;
    }
}
```

```java
Class c1=Object.class;
Class c2=Comparable.class;
Class c3=String[].class;
Class c4=int[][].class;
Class c5=Override.class;
Class c6= ElementType.class;
Class c7= Integer.class;
Class c8= void.class;
Class c9= Class.class;

System.out.println(c1);
System.out.println(c2);
System.out.println(c3);
System.out.println(c4);
System.out.println(c5);
System.out.println(c6);
System.out.println(c7);
System.out.println(c8);
System.out.println(c9);

//只要元素类型与维度一样，就是同一个Class
int[] a=new int[10];
int[] b=new int[100];
System.out.println(a.getClass().hashCode());
System.out.println(b.getClass().hashCode());
```

获取field的类型

```java
Class<?> type = fields[j].getType();
```

---

## 12 可变参数

可变参数:是JDK1.5之后出现的新特性

使用前提:当方法的参数列表数据类型已经确定，但是参数的个数不确定,就可以使用可变参数。

使用格式:（定义方法时使用）

- 修饰符 返回值类型 方法名(数据类型...变量名){}

可变参数的原理:

- 可变参数底层就是一个**数组**，根据传递参数个数不同,会创建不同长度的数组,来存储这些参数传递的参数个数,可以是0个(不传递),1,2...多个.

直接输出形参变量名，得到的是一个地址，这个变量名其实就是数组名。

有n个参数，就会new一个有n个数组单元的数组（数组长度为n），每个数组单元按顺序存放参数值。

```java
public static void main(String[] args) {
    int result=method(1,2,3,4,5);
    System.out.println(result);
}
public static int method(int...num){
    int sum=0;
    for (int i:num){
        sum+=i;
    }
    return sum;
}
```

可变参数的注意事项：

- 1.一个方法的参数列表，只能有一个可变参数
  - 错误写法：`public static void method(int...a,string..b)`
- 2.如果方法的参数有多个，那么可变参数必须写在参数列表的末尾。
  - 正确写法：`public static void method(string b,double c, int d, int...a)`
  - 错误写法：`public static void method(int...a,string b,double c, int d)`
- 3.可变参数的特殊(终极)写法
  - `public static void method(Object...obj)`

---

## 13 四种权限修饰符

java中.java文件不是同一个文件夹就不算是同一个包。

如果一个文件夹中还有一个文件夹，最里层文件夹有一个.java文件，最外层文件夹有另一个.java文件，这个.java文件和里层文件夹是并列的，因此两个.java文件不属于同一个包。

java中有四种权限修饰符

| 从大到小     | public | protected | (default) | private |
| ------------ | ------ | --------- | --------- | ------- |
| 同一个类     | YES    | YES       | YES       | YES     |
| 同一个包     | YES    | YES       | YES       | NO      |
| 不同包子类   | YES    | YES       | NO        | NO      |
| 不同包非子类 | YES    | NO        | NO        | NO      |

注意：(default)并不是关键字default，而是什么东西都不写。

YESorNO代表能否访问对应位置的变量或方法。

---

## 14 方法覆盖重写的注意事项:

- 1.必须保证父子类之间方法的**名称**相同，**参数列表**也相同。

  @Override：写在方法前面，用来检测是不是有效的正确覆盖重写。这个注解（@:annotation）就算不写，只要满足要求，也是正确的方法覆盖重写。

- 2.子类方法的返回值必须**小于等于**父类方法的返回值范围。

  小扩展提示: java.lang.0bject类是所有类的公共最高父类(祖宗类)，java.lang.String就是Object的子类。

- 3.子类方法的权限必须**大于等于**父类方法的权限修饰符。

  小扩展提示: public >protected > (default) > private

  备注：**(default)**不是关键宇default，而是什么都不写，留空。

- 绝大多数情况下，我们都是将子类和父类的返回值范围和权限都设置为相等的。

---

## 15 MVC 和 MVP 在Android中的体现

通常情况下，不可避免会吧Model层的职责交给了Activity

通常情况下，Activity不由自主的要去承担View的职责

![picture/MVC.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVC.png)



![picture/MVC在android中的体现.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVC在android中的体现.png)



![picture/MVC在android中存在的问题.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVC在android中存在的问题.png)



### 15.1 MVP解决了 MVC 在安卓中的问题

1.把Activity 和 layout 变成了 View V层 

2.把Model变成了，业务数据逻辑层 M层

3.P层去管理控制 V 层 与 M 层的交互处理，完全的隔离了之前代码耦合性过高的问题。

* Model：业务数据逻辑层
* View：Activity、layout（比如点击事件）、自定义View
* Presenter：负责以上两层V层和M层的交互处理

![picture/MVP架构的优点讲解.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVP架构的优点讲解.png)

![picture/再次理解MVP架构.png · 洪永峰/Picture - 码云 - 开源中国 (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/再次理解MVP架构.png)

![MVP Base基类的由来.png (821×491) (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVP Base基类的由来.png)

![MVP三者具体关系.png (700×227) (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVP三者具体关系.png)

![MVP三者职责.png (789×139) (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/master/picture/MVP三者职责.png)

### 15.2 契约合同

1. 什么是契约合同?

   答:就是约束具体业务需求的标准行为

   如: V层请求登录 --> P层，P层校验后，让M层执行业务 --> M层返回结果给P层 --> P层将结果信息处理后，给V层

2. 为什么要用契约合同?

   答: 规定好，MVP，必须遵循的规则

3. 以OOP思想（封装，继承，多态）考虑契约合同，能够带来什么好处?

   答: 降低耦合度，接口的统一管理业务逻辑清晰，易于后期维护，提高可扩展性。

**注意：M层要回调P层来回传数据。CallBack**

![契约合同与MVP登录需求的关系.png (729×497) (gitee.com)](https://gitee.com/hongyongfeng/picture/raw/de1d6ea9d0c9f16165a1c782797210758af42577/picture/契约合同与MVP登录需求的关系.png)

---

### 15.3 契约合同的第一种使用方式

使用者给LoginActivity 实现 VP接口

使用者给LoginPresneter 实现 VP接口

使用者给LoginModel 实现 M 接口

当使用者自己去实现接口，意味着使用者是主动去实现接口的。

**优点**:使用者自己去实现接口，自由性强

---

### 15.4 契约合同的第二种使用方式

使用者被动的传入 ILoginContract.VP 不传入接口编译都不通过

`LoginActivity extends BaseActivity<LoginPresenter, ILoginContract.VP> {}`

1.不再是使用者自己去**实现**ILoginContract.VP。而是MVP架构，强制使用者传入，这样的好处是：使用者必须传入

2.当MVP架构强制要求使用者传入接口,意味着:使用者是被动地传入接口

**优点**: MVP架构强制要求传入接口，给使用者指引使用流程

* 1.什么是面向接口编程?
  * 只面向抽象事物标准规范，而不去关注具体抽象事物的实现。
* 2.面向接口编程的好处是什么?
  * 方便程序使用多态特征
  * 代码扩展性更加强
  * 降低了代码的耦合度

**注意**:并不是所有项目都可以适合使用MVP架构，如果大型项目需要考虑可维护性，可扩展型可以考虑使用MVP。所以具体是否使用MVP架构，取决于公司的需要。

Fragment和Activity的写法差不多。

---

## 16 设置app的icon

icon的png文件要放在drawable文件夹中，如果放在mipmap文件夹中则无法正确显示图标。

---

## 17 判断是否处于深色模式

```java
//深色模式的值为:0x21
//浅色模式的值为:0x11
if(this.getApplicationContext().getResources().getConfiguration().uiMode == 0x21){
    ToastUtil.showLongToast("深色模式");
}
```

[深色主题背景  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/guide/topics/ui/look-and-feel/darktheme?hl=zh-cn)





---

## 18 改变图标的颜色

使用`ImageView`或其他控件的`setColorFilter()`方法更改图标（不是背景色）的颜色。

**注意**：传进去的参数不能是十进制，而是**十六进制**，不能引用R.color...或者R.id...

```java
imgAccount.setColorFilter(0xff838383);
```

开头的0xff表示透明度为100%，即不透明。

后面六位数字则是RGB。

```xml
app:tint="@color/white"
```

ImageView的属性当中：tint也 表示图片的颜色，效果和setColorFilter()方法一样，但是直接引用color的id即可



---

## 19 如何把软键盘的回车按键变成搜索按键

```bash
在EditText控件下加入以下属性：
android:imeOptions="actionSearch"
android:singleLine="true"
android:maxLines="1"
```

只使用 `android:imeOptions="actionSearch" `是不足够的。

而 `android:maxLines="1"` 是为了防止点击回车键换行.

通过修改 android:imeOptions 来改变默认的键盘显示文本。常用的常量值如下：

1. actionUnspecified 未指定，对应常量`EditorInfo.IME_ACTION_UNSPECIFIED`
2. actionNone 没有动作,对应常量`EditorInfo.IME_ACTION_NONE`
3. actionGo 去往，对应常量`EditorInfo.IME_ACTION_GO`
4. actionSearch 搜索，对应常量`EditorInfo.IME_ACTION_SEARCH`
5. actionSend 发送，对应常量`EditorInfo.IME_ACTION_SEND`
6. actionNext 下一个，对应常量`EditorInfo.IME_ACTION_NEXT`
7. actionDone 完成，对应常量`EditorInfo.IME_ACTION_DONE`

### 19.1 监听软键盘的搜索功能

```java
EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(EditText);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    return true;//不隐藏软键盘
                }
 
                return false;//隐藏软键盘
            }
        });
```

### 19.2 隐藏软键盘

```java
        /**
     * 隐藏软键盘
     * @param context :上下文
     * @param view    :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
```

思考：

为什么不添加 EditText 的 `OnEditorActionListener` 时，点击软键盘的搜索按钮后，就能自动隐藏软键盘。

但是添加 `OnEditorActionListener` 后，点击软键盘的搜索按钮后，不能自动隐藏软键盘，而是要添加一个 `hideKeyboard()方法` 来隐藏软键盘。

**原因**：

`onEditorAction()方法` 返回false表示点击后，隐藏软键盘。返回true表示保留软键盘。

如果不添加`OnEditorActionListener` 的话，就默认返回false。

---

### 19.3 点击空白位置时隐藏软键盘

点击使用EditText组件的时候，会弹出软键盘。然后我们输入操作，但我们输入完成之后点击空白区域，键盘并没有收起，我们必须点击软键盘自带收起才能关闭键盘，这对用户来说，可能体验不是太好，因此，在输入时需要软键盘，弹出软键盘，当不需要时，点击空白区域，就让软键盘收起。

首先封装一个工具类：`KeyboardUtils`

```java
public class KeyboardUtils {
    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     * @return 是否隐藏
     */
    public static void hideKeyboard(MotionEvent event, View view, Activity activity) {
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // （判断是不是EditText获得焦点）判断焦点位置坐标是否在控件所在区域内，如果位置在控件区域外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}    
```

然后在使用的 Activity 或者是 Fragment 中重写 dispatchTouchEvent()方法，根据焦点获取当前获得焦点的View，调用即可:

```java
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取当前获得焦点的View
                View view = getCurrentFocus();
                //调用方法判断是否需要隐藏键盘
                KeyboardUtils.hideKeyboard(ev, view, this);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
```

---

## 20 在位于appcompat中的values中引用color

```xml
android:background="?attr/colorPrimary"
```

---

## 21 控件的margin属性

layout_margin和layout_marginStart不能共存，否则位于下方的属性不起作用。

---

## 22 RelativeLayout的对齐

在相对布局中，可以通过以下的属性让的组合让控件处于父容器左上角、右上角、左下角、右下角、上下左右居中，正居中等九个位置。属性如下：

`android:layout_alignParentLeft="true" 父容器左边`

`android:layout_alignParentRight="true" 父容器右边`

`android:layout_alignParentTop="true" 父容器顶部`

`android:layout_alignParentBottom="true" 父容器底部`

`android:layout_centerHorizontal="true" 水平方向居中`

`android:layout_centerVertical="true" 垂直方向居中`

`android:layout_centerInParent="true" 水平垂直都居中`

`android:layout_above` 将组件放在指定ID组件的上方 

`android:layout_below` 将组件放在指定ID组件的下方 

---

## 23 DrawerLayout 全屏手势滑动

默认侧滑手势必须在屏幕边缘才可以触发滑动，可定制性不强。

看看能不能用viewpager在第一页中向左的滑动来代替手势滑动然后触发侧边栏。

---

## 24 获取NavigationView中的头布局

考核虽然不能用NavigationView，但还是学习一下。

```java
NavigationView navigationView=findViewById(R.id.nav_view);
View headView=navigationView.getHeaderView(0);
headView.setBackgroundColor(getResources().getColor(R.color.transparent));
```

直接用findViewbyid(R.id.nav_header_root),findViewById(R.id.every_day_title)来获取子控件，返回的值是null。

我们要先获取到 Navigation ,然后通过 `navigation.getHeaderView(0);` 来获取 `headView`

接着通过`headview.findViewById(R.id.nav_header_root);`,`headview.findViewById(R.id.every_day_title);`便可获取子控件。

---

## 25 引用颜色

可以在values的colors文件夹中引用颜色，用法：`android:textColor="@color/gray"`

可以在安卓自带的colors.xml文件中引用颜色，用法：`android:textColor="@android:color/darker_gray"`

---

## 26 WebView

### 26.1 点击返回键回到上一个html

* **1.监听系统返回键，如果有上个html则返回，否则退出这个界面：**

  ```java
  @Override
  public boolean
  onKeyDown(int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
          webView.goBack();//返回上个页面
          return true;
      }
      return super.onKeyDown(keyCode, event);//退出H5界面
  }
  ```

* **2.如果自定义了返回的按钮，也要添加下面代码：**

  ```java
  button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          if (webView.canGoBack()) {
              webView.goBack();//返回上个页面
              return;
          } else {
              finish();
          }
      }
  });
  ```

---

### 26.2 webView无法加载https协议URL

#### 修复方法

```java
webView.setWebViewClient(new WebViewClient(){
  @Override
  public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      webView.getSettings()
      .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
  }
);
```

#### 会被警告的修复方法

重写`WebViewClient`的`onReceivedSslError`

添加`handler.proceed` 方法，但 App 如果上架GooglePlay会被警告

```java
webView.setWebViewClient(new WebViewClient(){
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
      handler.proceed();
    }
  }
);
```

---

## 27 导入或设计矢量图vector asset的方法

在res文件夹下，右键new一个 `vector asset` 

可以导入svg文件，也可以用as中自带的一些矢量图，然后改变其颜色，以及透明度等一些参数。

---

## 28 EditText防止输入空格

使用EditText的时候，很多应用场景下不能输入空格。如何限制不让输入空格呢，这里使用两种方法来实现。

### 28.1 EditText设置监听 

`addTextChangedListener(new TextWatcher(){})`

```java
etNull.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().contains(" ")){
                    String[] str = s.toString().split(" ");
                    StringBuffer content = new StringBuffer();
                    for (int i = 0; i < str.length; i++) {
                        content.append(str[i]);
                    }
                    etNull.setText(content.toString());
                    etNull.setSelection(start);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
```

需要注意的是，如果不设置setSelection()，输入空格，光标会跑到第一个位置。
onTextChanged里面，start参数会传入在这个空格输入之前，光标所在的位置，可以通过EditText.setSelection(start)来改变光标的位置。

### 28.2 使用InputFilter

```java
public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                    Spanned dest, int dstart, int dend) {
                if(" ".equals(source)){
                    return "";
                }else{
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
```

---

## 29 Toast弹出的一定是字符串String

如果传入其他类型的数据，则会报异常。

`E/MessageQueue-JNI: android.content.res.Resources$NotFoundException: String resource ID #0x1`

---

## 30 TabLayout

```xml
<com.google.android.material.tabs.TabLayout
android:id="@+id/home_tab_layout"
android:layout width="match_parent"
android:layout height="wrap_content"
app:tabGravity="start"
app:tabMode="auto" />
```

`app:tabMode` 是设置tab的模式的，有这几个取值

* fixed(默认值): 固定的，也就是标题不可滑动，有多少就展示多少，平均分配长度,如果有很多个tab就会显得很窄。
* scrollable:可滑动的，小于等于5个默认靠左固定，大于五个tab后就可以滑动
* auto:自动选择是否可滑动，小于等于5个默认居中固定（tab在该水平线），大于5个自动滑动

`app:tabGravity` 设置tab的位置（若tabMode设置为auto，则下面三个属性都是一样居中的效果）

* start: 居左
* fill:平均分配，铺满屏幕宽度
* center: 居中

---

## 31 Fragment的生命周期

![](https://img-blog.csdnimg.cn/img_convert/8998b924006e95802ef00a14f98870aa.png)

```kotlin
override fun onAttach(context: Context) {
    super.onAttach(context)
    requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            // 想做啥做点啥
            owner.lifecycle.removeObserver(this)
        }
    })
}
```



---

## 32 ViewPager的加载方式

ViewPager的默认加载方式是缓存当前界面前后相邻的两个界面，即最多共缓存包括当前界面在内的三个界面信息。当滑动切换界面的时候，非相邻界面信息将被释放，该Fragment会执行onDestroy方法。

若界面2是当前界面，界面1和3是缓存界面，当切换到1时，界面2仍缓存(onPause)，界面3被销毁释放，于是便有了onDestroyView和onDestroy的调用。

由1切换到2或3时，界面3又被重新创建，于是执行onCreateView方法。

### 32.1 解决方案

方案一：设置ViewPager的缓存界面数

此方案**适用于界面数较少**的情况，避免缓存界面太多导致内存不足。
`viewPager.setOffscreenPageLimit(2);`
参数：

`int limit`  缓存当前界面每一侧的界面数

以上述为例，当前界面为1，若limit = 2，表示缓存2、3两个界面。因此便避免了界面3被销毁。 

**如果不想预加载，则将参数limit设为0，即点击哪一页page就加载哪一页的数据。**

---

方案二：保存状态并恢复

此方案适用于可用界面信息可由状态保存和恢复实现的情况。
在onDestroyView方法内保存相关信息，在onCreateView方法内恢复信息设置。

---

方案三（推荐）：复用Fragment的RootView
此方案适用通用场景，推荐使用。
步骤1：在onDestroyView方法内把Fragment的RootView从ViewPager中remove。

```java
 @Override
     public void onDestroyView() {
         LogUtils.d(TAG , "-->onDestroyView");
         super.onDestroyView();
         if (null != FragmentView) {
             ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
         }
     }
```

步骤2：在onCreateView方法内复用RootView

```java
 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         if (null == mFragmentView) {
              mFragmentView = inflater.inflate(R.layout.fragment, container, false);
              mListView = (ListView) mFragmentView .findViewById(R.id.mm_listview);
              mListView.setAdapter(mAdapter);
        }
        return mFragmentView ;
    }
```

---

### 32.2 防止频繁的销毁视图的解决方案

1、使用方案一 `setOffscreenPageLimit(2)`

2、或者重写PagerAdaper的destroyItem方法为空即可

```java
public class MyViewPagerAdapter extends FragmentPagerAdapter {

        ...

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //如果注释这行，那么不管怎么切换，page都不会被销毁
            //super.destroyItem(container, position, object);
        }
    }
```

---

## 33 RecyclerView 异常

ViewPager中Fragment切换时候RecyclerView报 `No adapter attached; skipping layout` 错误。

在项目里面，运行起来时候页面可以加载出来，当切换别的Fragment时候再回来时，加载不出页面，报错。

> 解决方法：只要在onCreateView里面再设置一遍setLayoutManager 、setAdapter就可以了。

---

## 34 跳转浏览器打开网址

```java
Uri uri = Uri.parse("https://www.baidu.com/");
Intent intent = new Intent(Intent.ACTION_VIEW,uri);
startActivity(intent);
```

---

## 35 Handler()过时

`new Handler(Looper.myLooper()) 获取当前线程的Looper`

`new Handler(Looper.getMainLooper()) 获取主线程的Looper`

注：如果是主线程创建handler这两没区别，如果是在子线程里创建handler这两个就不一样了。
```java
private Handler mHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_DOWN_FAIl:
           
                break;
        }
    }
};
private Handler mHandler = new Handler(Looper.myLooper()) {
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_DOWN_FAIl:
              
                break;
        }
    }
};
```

---

## 36 抽象类实例化问题

抽象类本身是不能实例化的，例如

```java
public abstract class TestAbs {
    String a="1";

    public interface OnPictureClickListener{
        void onPictureClick(int position);
    }
    private OnPictureClickListener onPictureClickListener;
    public void setOnPictureClickListener(OnPictureClickListener onPictureClickListener){
        this.onPictureClickListener=onPictureClickListener;
    }
    void b() {
        System.out.println(a);
        onPictureClickListener.onPictureClick(1);
    }
}
```

```java
//错误做法:
TestAbs testAbs=new TestAbs();

//正确做法
TestAbs testAbs=new TestAbs() {
    @Override
    void b() {
        super.b();
    }
};
```

按照正确做法之后，可以通过 `TestAbs` 抽象类的匿名子类的 `testAbs` 实例（已经转型为抽象类的对象）去访问抽象父类的成员变量和成员方法。如：

```java
testAbs.setOnPictureClickListener(new TestAbs.OnPictureClickListener() {
    @Override
    public void onPictureClick(int position) {
        System.out.println(position);
    }
});
testAbs.b();
```

注意该testAbs对象不能访问匿名子类中的成员变量、成员方法以及接口

但是可以直接用匿名对象来访问匿名子类中的成员变量、成员方法，但接口仍然不能访问。如：

```java
new TestAbs() {
    interface OnClickListener{
        void onPictureClick(int position);
    }
    int num=0;
    void abc() {
        System.out.println(num);
    }
}.abc();
```

若想在匿名子类中定义一个接口，只能在jdk16以上的版本才能成功，16以下的jdk会报错。

---

## 37 系统自带的加载动画

```java
ProgressDialog dialog=ProgressDialog.show(fragmentActivity,"","正在加载");
dialog.dismiss();//取消动画
```

----

## 38 String类的常用方法

### 38.1 substring()方法

`substring()` 的作用就是截取父字符串的某一部分

`public String substring(int beginIndex, int endIndex)`

第一个参数int为开始的索引，对应String数字中的开始位置，

第二个参数是截止的索引位置，对应String中的结束位置

从beginIndex开始取，到endIndex结束，**从beginIndex开始数，其中包括beginIndex位置的字符，但不包括endIndex位置的字符**。

----

### 38.2 indexOf()方法

 `int indexOf(String str)` ：返回第一次出现的指定子字符串在此字符串中的索引。

 `int indexOf(int char)` :从头开始查找是否存在指定的字符,并返回第一次出现指定字符在此字符串中的索引。这里的char可以用 `' '` 来输入,也可以直接输入ASCII码。

---

### 38.3 更多实用方法

摘取自菜鸟教程：[Java String 类 | 菜鸟教程 (runoob.com)](https://www.runoob.com/java/java-string.html)

| 16   | [int indexOf(int ch)](https://www.runoob.com/java/java-string-indexof.html) 返回指定字符在此字符串中第一次出现处的索引。 |
| ---- | ------------------------------------------------------------ |
| 17   | [int indexOf(int ch, int fromIndex)](https://www.runoob.com/java/java-string-indexof.html) 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。 |
| 18   | [int indexOf(String str)](https://www.runoob.com/java/java-string-indexof.html)  返回指定子字符串在此字符串中第一次出现处的索引。 |
| 19   | [int indexOf(String str, int fromIndex)](https://www.runoob.com/java/java-string-indexof.html) 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 |
| 20   | [String intern()](https://www.runoob.com/java/java-string-intern.html)  返回字符串对象的规范化表示形式。 |
| 21   | [int lastIndexOf(int ch)](https://www.runoob.com/java/java-string-lastindexof.html)  返回指定字符在此字符串中最后一次出现处的索引。 |
| 22   | [int lastIndexOf(int ch, int fromIndex)](https://www.runoob.com/java/java-string-lastindexof.html) 返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索。 |
| 23   | [int lastIndexOf(String str)](https://www.runoob.com/java/java-string-lastindexof.html) 返回指定子字符串在此字符串中最右边出现处的索引。 |
| 24   | [int lastIndexOf(String str, int fromIndex)](https://www.runoob.com/java/java-string-lastindexof.html)  返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。 |

---

**运用上述方法可以提取出json代码中符合格式的json字符串并传入json数组中进行解析。**

---

## 39 JSONArray和JSONObject的用法

### 39.1 JSONArray的用法

#### 1 解析字符串

`JSONArray jsonarr = JSONArray.parseArray(str);`

`JSONArray jsonarr = new JSONArray(str);`	 

#### 2 存值取值

##### (1) 存值

`jsonarr.add(obj);`

##### (2) 取值

```java
for(int i =0; i <= jsonarr.size(); i++){
 	jsonarr[i].get(key);
}    
```

##### (3) 格式： 

本质是数组，必须是数组格式, 用 `[ ]` 包裹数据 

格式：

`[{key:value},{key:value}... ]`

`["str1","str2","str3",...]`

----

### 39.2 JSONObject的用法

#### 1 解析字符串

`JSONObject  obj = JSONArray.parseObject(str);`

#### 2  存值取值

##### (1) 存值

`obj.put("key", key);`

`obj.put("value", value);`

##### (2) 取值

`value = obj.get(key);`

##### (3) 获取键的字符串

```java
JSONObject jsonObject=jsonArray.getJSONObject(0);
Iterator<String> keys = jsonObject.keys();
List<String> jsonFieldList=new ArrayList<>();
while (keys.hasNext()){
    String jsonField=keys.next();
    jsonFieldList.add(jsonField);
}
```

#### 3 格式：

本质是对象， 用 `{}` 表示

格式：

`{key:value}`

---

### 39.3 注意

如果json的只有一组数据，如果使用JSONArray可能会抛出异常，此时应该改用JSONObject。

---

## 40 Fragment的FragmentTransaction问题

每次调用transaction的 `add()/hide()/show()/replace()` 方法时都要重新为transaction对象赋值(获取一个新的实例)，否则上述方法都会失效。

另外add()方法不能重复添加同一个Fragment实例。否则会抛出异常。

```java
FragmentTransaction transaction=fragmentManager.beginTransaction();
```

---

## 41 WebView错误

使用WebView打开网页时，遇到错误 `net::err_cleartext_not_permitted`

### 解决办法：

打开android目录中的`AndroidManifest.xml`文件，并在application标签中添加如下一行代码：

 `android:usesCleartextTraffic="true"`

```xml
<application
    android:usesCleartextTraffic="true"/>
```

一般用webview加载网址手机都会自动跳转到自带的浏览器中，如果为了一些效果想要不去跳转到浏览器，那就要给自己的webview加上客户端

添加客户端代码

```java
webView.setWebViewClient(new WebViewClient() {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(url);
        //禁止跳转外部网页
        //但有些网站如果你禁止跳转外部app的话，他会不断重复刷新网站
        return super.shouldOverrideUrlLoading(view, request);
    }
});
```

跳转外部app的代码：

```java
webView.setWebViewClient(new WebViewClient() {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            //处理intent协议
            if (url.startsWith("intent://")) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                        intent.setSelector(null);
                    }
                    List<ResolveInfo> resolves = getApplicationContext() .getPackageManager(). queryIntentActivities(intent,0);
                    if(resolves.size()>0){
                        startActivityIfNeeded(intent, -1);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            // 处理自定义scheme协议
            if (!url.startsWith("http")) {
                try {
                    // 以下固定写法
                    final Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    // 防止没有安装的情况
                    e.printStackTrace();
                    Toast.makeText(WebViewActivity.this, "您所打开的第三方App未安装！", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
});
```

---

## 42 更新UI一定要在主线程里面进行

虽然有时候不会抛出异常，但是也有可能不能正常更新。

比如我在recyclerview中增加了数据，我想更新adapter，这时没有留意到更新UI的语句还是在子线程中，即使更新成功了UI，也会出现一些奇奇怪怪的bug，比如item中的控件发生错乱，控件中不该出现的字最终出现了...等等。

---

## 43 解决ScrollView和RecyclerView之间的滑动冲突

### 解决方法：

禁止RecyclerView滑动

最直接的方式是**重写**布局管理器中判断可滑动的方法，直接返回false，代码如下：

```java
LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false) {
    @Override
    public boolean canScrollVertically() {
        // 直接禁止垂直滑动
        return false;
    }
};
```

## 44 提高WebView流畅度的方法

### 44.1 强制开启Android Webview GPU 加速

```xml
<application
    android:hardwareAccelerated = "true"/>
```

### 44.2 修改WebSettings

```java
WebSettings webSettings=webView.getSettings();
webSettings.setDomStorageEnabled(true);
webSettings.setDatabaseEnabled(true);
webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
```

---

## 45 View.VISIBLE、INVISIBLE、GONE的区别

android中UI应用的开发中经常会使用 `view.setVisibility()` 来设置控件的可见性，其中该函数有3个可选值，他们有着不同的含义：

* `View.VISIBLE` --->可见
* `View.INVISIBLE` --->不可见，但这个View仍然会占用在xml文件中所分配的布局空间，不重新layout
* `View.GONE` ---->不可见，但这个View在ViewGroup中不保留位置，会重新layout，不再占用空间，那后面的view就会取代他的位置.

---

## 46 onStart和onResume方法的区别

onStart和onResume的区别：onStart实际上表示Activity或者Fragment已经可见了，只是我们用户还看不到，还不能进行交互而已，因为它还处在后台。而onResume表示Activity已经显示到前台可见了，并且可以进行交互。

1、onStart()：通常作用于用户初始化APP，或onStop()方法之后(用户按下home键，activity变为后台)之后用户再切回这个activity就会调用onRestart()然后调用onStart()，在OnStart()方法在被调用的时候，Activity已经准备好被用户看见，**但是此时的Activity尚未出现在前台，不能与用户进行交互，可理解为Activity或者Fragment已经出现了，已经准备好了，但是我们无法与其进行交互;**

2、onResume()：是当该activity与用户能进行交互时被执行，用户可以获得activity的焦点，能够与用户交互。

OnResume()就是使OnStart()方法之后的Activity或者Fragment变为可交互的状态;

例子：在Activity或Fragment中弹出一个AlertDialog时执行onPaused()方法，（或者Activity/Fragment被另一个透明或者Dialog样式等等的Activity/Fragment覆盖了），之后AlertDialog被取消了，Activity/Fragment回到可交互状态，此时调用onResume()。

注意：在ViewPager下，点击相邻的Fragment，该相邻的Fragment执行onResume方法，不会执行onStart方法

---

## 47 Fragment中hide和show方法对生命周期的影响

第一次add一个Fragment后再show该Fragment，则会执行生命周期。

之后如果对该Fragment进行hide方法或者show方法的话，**生命周期则不再执行**。

在有的情况下，数据将无法通过生命周期方法进行刷新。

解决办法：可以使用onHiddenChanged方法来解决这个问题。

```java
@Override
public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden) {   // 不在最前端显示 相当于调用了onPause();
        System.out.println("hide");;
    }else{  // 在最前端显示 相当于调用了onResume();
        System.out.println("show");//网络数据刷新
    }
}
```

---

## 48 getResponseCode()状态码

### http状态返回代码 1xx（临时响应）

表示临时响应并需要请求者继续执行操作的状态代码。

**http状态返回代码** 

代码  说明
100  （继续） 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。 
101  （切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换。

### http状态返回代码 2xx （成功）

表示成功处理了请求的状态代码。

**http状态返回代码** 

代码  说明
200  （成功） 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
201  （已创建） 请求成功并且服务器创建了新的资源。
202  （已接受） 服务器已接受请求，但尚未处理。
203  （非授权信息） 服务器已成功处理了请求，但返回的信息可能来自另一来源。
204  （无内容） 服务器成功处理了请求，但没有返回任何内容。
205  （重置内容） 服务器成功处理了请求，但没有返回任何内容。
206  （部分内容） 服务器成功处理了部分 GET 请求。

### http状态返回代码 3xx（重定向）

表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。

**http状态返回代码** 

代码  说明
300  （多种选择） 针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。
301  （永久移动） 请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。
302  （临时移动） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
303  （查看其他位置） 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。

304  （未修改） 自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。
305  （使用代理） 请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。
307  （临时重定向） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。

### http状态返回代码 4xx（请求错误）

这些状态代码表示请求可能出错，妨碍了服务器的处理。

**http状态返回代码** 

代码  说明
400  （错误请求） 服务器不理解请求的语法。
401  （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
403  （禁止） 服务器拒绝请求。
404  （未找到） 服务器找不到请求的网页。
405  （方法禁用） 禁用请求中指定的方法。
406  （不接受） 无法使用请求的内容特性响应请求的网页。
407  （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
408  （请求超时） 服务器等候请求时发生超时。
409  （冲突） 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
410  （已删除） 如果请求的资源已永久删除，服务器就会返回此响应。
411  （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
412  （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
413  （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
414  （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
415  （不支持的媒体类型） 请求的格式不受请求页面的支持。
416  （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
417  （未满足期望值） 服务器未满足"期望"请求标头字段的要求。

### http状态返回代码 5xx（服务器错误）

这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。

**http状态返回代码** 

代码  说明
500  （服务器内部错误） 服务器遇到错误，无法完成请求。
501  （尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
502  （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
503  （服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
504  （网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
505  （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。 

### 常见的http状态返回代码为：

200 - 服务器成功返回网页
404 - 请求的网页不存在
503 - 服务不可用

---

## 49 强制刷新RecyclerView的Adapter

```java
adapter.notifyDataSetChanged();
```

如果用 `notifyItemChanged()` 的话，则会有更新动画出现，会耗更多的时间。

### 补充：

* （1）notifyItemChanged(position)
  * 只刷新该position的Item，即只是该Item调用onBindViewHolder，因此如果对数据源进行插、移除操作不能改方法只刷新操作的Item，毕竟该Item之后的Item的position都发生了改变
* （2）notifyItemChanged(int position, Object payload)
  * 对position的Item进行局部刷新，在onBindViewHolder(ContentViewHolder viewHolder, int position, List<Object> payloads)中根据payloads.get(position)值只对需要刷新的控件进行操作
* （3）notifyDataSetChanged()
  * 刷新全部Item
* （4）notifyItemRangeChanged(position, itemCount)
  * 刷新position及之后ItemCount个Item
* （5）notifyItemInserted(position)
  * 插入并进行刷新
* （6）notifyItemRangeInserted(int position, int itemCount)
  * 从position开始插入itemCount个Item并进行刷新
* （7）notifyItemRemoved(int position)
  * 移除并进行刷新
* （8）notifyItemRangeRemoved(int position, int itemCount)
  * 从position开始移除itemCount个Item并进行刷新
* （9）notifyItemMoved(int fromPosition, int toPosition)
  * 移动并进行刷新

---

## 50 在TextView中显示Html文本

```java
holder.tvTitle.setText(Html.fromHtml(article.getTitle()));
```

如果字符串中有Html代码，则该代码会被解析，然后显示在原来字符串的位置上，并有一定的格式。

`轻松实现相机预览 | <em class='highlight'>Camera</em> Viewfinder 全新上线` 

该字符串中含有`<em class='highlight'>Camera</em>`标签，说明该标签体内的文字`Camera`应该是斜体的<em class='highlight'>Camera</em>，原来的字符串为：

轻松实现相机预览 | <em class='highlight'>Camera</em> Viewfinder 全新上线

如果字符串中没有Html代码，则返回原来的字符串。

---

## 51 Fragment和Activity序列化传递对象

创建Fragment对象时，一般可以通过 `new Fragment()` 构造方法来实现。如果此时将参数通过Fragment的重载构造方法进行传递，系统会有一个warning，如下：

`Avoid non-default constructors in fragments: use a default constructor plus Fragment#setArguments(Bundle) instead`

这个警告的意思就是，尽量避免使用 不是默认的构造函数（也就是我们重载的构造函数）：通过 使用默认的构造函数 加上 `Fragment.setArguments（Bundle）`来取代。

### 方法二：setArguements

无论是replace()还是show()还是add()方法，都可以用Bundle传递参数并接收。

```java
ArrayList<ArticleBean> articleBeanLists;
LoadingFragment loadingFragment=new LoadingFragment();
ArticleFragment articleFragment=new ArticleFragment();
Bundle bundle=new Bundle();
bundle.putParcelableArrayList("list",  articleBeanLists);
if (!articleFragment.isAdded()){
    articleFragment.setArguments(bundle);
    transaction.hide(loadingFragment).add(R.id.fragment_query,articleFragment).show(articleFragment).commit();
}else {
    articleFragment.setArguments(bundle);
    transaction.hide(loadingFragment).show(articleFragment).commit();
}
```

乍一看这两种方法似乎没有什么本质区别，但是实际上 方法一（重载构造函数）是有一个隐患的。

根据Android文档说明，当一个fragment重新创建的时候，系统会再次调用 Fragment中的默认构造函数。 注意这里：是 默认构造函数。

这句话更直白的意思就是：当你小心翼翼的创建了一个带有重要参数的Fragment的之后，一旦由于什么原因（横竖屏切换）导致你的Fragment重新创建，那么很遗憾的告诉你，你之前传递的参数都不见了，因为recreate你的Fragment的时候，调用的是**默认构造函数**。

### 对比

而使用系统推荐的 `Fragment.setArguments（Bundle）`来传递参数。就可以有效的避免这一个问题，当你的Fragment销毁的时候，其中的Bundle会保存下来，当要重新创建的时候会检查Bundle是否为null，如果不为null，就会使用bundle作为参数来重新创建fragment.

疑问：

当fragment重建的时候，怎么获取之前的参数呢？

以上面的代码为例：我们可以重写 fragment的onAttach()或者onCreate()方法。

```java
@Override
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    fragmentActivity = requireActivity();
    if (getArguments() != null) {
        articleBeanList = getArguments().getParcelableArrayList("list");
    }
}
```

当fragment重新创建，调用onCreate方法的好处是，可以重复获取之前的参数，然后就可以尽情使用了。

### 注意：

setArguments方法的调用必须要在Fragment与Activity关联之前。

这句话可以这样理解，setArgument方法的使用必须要在FragmentTransaction 的commit之前使用。

---

### 使用Parcelable传递参数

声明List集合时候泛型中是你声明的实体类： `List<ArticleBean> gates=new ArrayList<ArticleBean>;`

我们要做的是将这个 `ArticleBean` 传递到要跳转的Activity，用到的方法是 `bundle.putParcelableArrayList("list", articleBeanList）`

如果你的实体类只是声明变量以及添加对应的构造函数和setter和getter方法，直接用以上方法传递集合会报错的，因为 `bundle.putParcelableArrayList("list", articleBeanList）`这个方法要求是集合中的泛型必须实现 `Parcelable` 接口；



```java
public class ArticleBean implements Parcelable {
    public ArticleBean() {
    }

    private int id;
    private String author;
    private String chapterName;
    private String link;
    private String title;
    private String niceDate;
    private String superChapterName;

    protected ArticleBean(Parcel in) {
        id = in.readInt();
        author = in.readString();
        chapterName = in.readString();
        link = in.readString();
        title = in.readString();
        niceDate = in.readString();
        superChapterName = in.readString();
        top = in.readInt();
    }

    public String getSuperChapterName() {
        return superChapterName;
    }
    public int top;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }



    public ArticleBean(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错 
        // 2.序列化对象 
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(chapterName);
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(niceDate);
        dest.writeString(superChapterName);
        dest.writeInt(top);
    }
    
    // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下： 
    // android.os.BadParcelableException: 
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person 
    // 2.这个接口实现了从Percel容器读取ArticleBean数据，并返回ArticleBean对象给逻辑层使用 
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错； 
    // 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错 
    // 5.反序列化对象 
    public static final Parcelable.Creator<ArticleBean> CREATOR  = new Creator<ArticleBean>() {
        //实现从source中创建出类的实例的功能
        
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错 
            ArticleBean article  = new ArticleBean();
            article.id = source.readInt();
            article.author= source.readString();
            article.chapterName = source.readString();
            article.link = source.readString();
            article.title = source.readString();
            article.niceDate = source.readString();
            article.superChapterName = source.readString();
            article.top = source.readInt();
            return article;
        }
        //创建一个类型为T，长度为size的数组
        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
```

以上的重点是实现了Parcelable接口，并且重写了其中的方法。

传值方法(Activity)：

```java
Intent intent = new Intent(LoginActivity.this,PeizhiActivity.class);
Bundle bundle = new Bundle();
bundle.putParcelableArrayList("gates", gates);
intent.putExtra("string",string);
intent.putExtras(bundle);
startActivity(intent);
```

接收方法(Activity)：

```java
Bundle bundle = getIntent().getExtras();
gates = bundle.getParcelableArrayList("gates");
```

而Fragment的传值方法和接收方法在上文已经展示出来了。

----

## 52 RecyclerView滑动到顶部

```java
recyclerView.scrollToPosition(0);
```

---

## 53 RecyclerView滑动到底部的监听方法

这是View自带的方法

![2185296-78f18e9fabbcc7a2.png (414×413) (jianshu.io)](https://upload-images.jianshu.io/upload_images/2185296-78f18e9fabbcc7a2.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

* `computeVerticalScrollExtent()` 是当前屏幕显示的区域高度
* `computeVerticalScrollOffset()` 是当前屏幕之前滑过的距离
* `computeVerticalScrollRange()` 是整个View控件的高度。
  * 注意，该高度不是RecyclerView match_parent 时的高度，而是所有item加起来的高度。
  * 当item没有布满整个RecyclerView时，computeVerticalScrollExtent()等于computeVerticalScrollRange()

```java

recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isSlideToBottom(recyclerView)){
            if (loadMore){
                dialog = ProgressDialog.show(requireActivity(), "", "正在加载", false, false);
                getContract().requestArticleVP(id,page);
                //Toast.makeText(fragmentActivity, "正在加载", Toast.LENGTH_SHORT).show();
                page++;
            }
        }

        if (isSlideToBottom(recyclerView)) {
            loadMore=true;
        }
    }
});
 
protected boolean isSlideToBottom(RecyclerView recyclerView) {
    if (recyclerView == null) {
        return false;
    }
    if (recyclerView.computeVerticalScrollExtent()<recyclerView.computeVerticalScrollRange()){
        loadMore=true;
        //该条件语句用于判断RecyclerView可见高度是否小于控件高度，如果是则第一次上拉可以成功刷新
    }
    //如果item没有布满屏幕，则会导致该方法返回true，使得程序自动请求加载更多，于是就引入了一个布尔变量loadMore，一开始是设为false的。如果第一次该方法返回true，则设置loadMore为true，从而就抵消了一次上拉刷新的请求。
    return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
}
```

---

## 54 Fragment中获取宿主Activity的控件

失败方法：

* 在onCreateView方法中

  ```java
  view = inflater.inflate(R.layout.activity_query, container,false);
  EditText edt=view.findViewById(R.id.edt_keyword);
  ```

  或者：

  ```java
  LayoutInflater factory =LayoutInflater.from(getContext());
  View layout=factory.inflate(R.layout.activity_query,container);
  EditText edtQuery=layout.findViewById(R.id.edt_keyword);
  ```

* 在任意地方

  ```java
  View actionBar = View.inflate(fragmentActivity, R.layout.activity_query, null);
  EditText edt=actionBar.findViewById(R.id.edt_keyword);
  ```

以上方法，最终获得的EditText对象都是 `androidx.appcompat.widget.AppCompatEditText{b4891a9 VFED..CL. ......I. 0,0-0,0 #7f0800b6 app:id/edt_keyword}` ,其中 `0,0-0,0` 说明了无法正常获取该控件，但又不是空对象null.

正确解决方法：

```java
EditText edtQuery=fragmentActivity.findViewById(R.id.edt_keyword);
```

用 `Activity.findViewById()` 方法才能正确获取Activity中的控件。

最终获得的EditText对象是 `androidx.appcompat.widget.AppCompatEditText{fb9409 VFED..CL. .F...... 207,32-895,106 #7f0800b6 app:id/edt_keyword aid=1073741824}`

---

## 55 RecyclerView的刷新

不建议把刷新RecyclerView的代码设置在onStart()方法和onResume()方法中，否则每次Activity的生命状态改变时，都会影响到RecyclerView的刷新。

注意：要考虑Activity或Fragment的生命周期

有时会因为某些需求导致在onResume或onStart方法中重置了recyclerview，但其数据已经丢失或没有重新设置Adapter，就会报错`No adapter attached; skipping layout`

一般是把recyclerview这一整套代码写在onCreate里面就不用担心出问题。如果一定要在onStart里面写的话，每次onStart方法执行就会重置一下recyclerview，这样会极大降低用户友好度。

打个比方：在逛淘宝的时候，往下翻了几十页后，点击一个item查看商品详情，结果返回时执行了onStart()方法让列表重置了，我又得翻好几十页才能接上上次看到的地方。

---

## 56 JSONObject可以返回Object类型的数据

如果获取的字段的值是一个JSONArray数组的话，则自动将Object类型转为JSONArray。

`jsonObject.get("children")`，若 `children` 字段对应的值是JSONArray数组，则默认返回一个JSONArray对象。

注意：

`new JSONArray` 的时候，传入构造函数中的参数一般是String类型，但不能是JSONArray类型的参数，否则会报错。

解决办法：

* 可以将JSONArray转化为字符串，再作为参数传入JSONArray的构造函数中，即 `JSONArray.toString()` ，`jsonObject.get("children").toString()`

* 或者直接不用new JSONArray()，而是直接接收该JSON数组：

  `JSONArray json=jsonObject.get("children");`

---

## 57 Java HashMap size() 方法

size() 方法用于计算 hashMap 中键/值对的数量。

size() 方法的语法为：

```java
hashmap.size()
```

**注：**hashmap 是 HashMap 类的一个对象。

### 返回值

返回 hashMap 中键/值对的数量。

---

## 58 Intent传递Map数据

### 传入数据

```java
//将Map强制转换成Serializable
Map<String, String> message = new HashMap<String, String>()；
getMessage.put("name", userName);
getMessage.put("time", time);
Intent intent = new Intent(getApplicationContext(), DiseaseShowActivity.class);
intent.putExtra("message",(Serializable)message);

Intent intent=new Intent(activity, TabActivity.class);
intent.putExtra("name",name);
Map<String,Object> childrenMap=stringListMap.get(position);
intent.putExtra("name",name);
intent.putExtra("map",(Serializable)childrenMap);
startActivity(intent);
```

### 接收数据

```java
Intent intent = getIntent();
if(intent != null){
    //获取intent中的参数
    Map<String,Object> childrenMap=(Map<String,Object>)intent.getSerializableExtra("map");
    String name=intent.getStringExtra("name");
}
```

---

## 59 Fragment可见性总结

1，  onHiddenChanged(boolean hidden)

（1）只在调用hideFragment/showFragment后才会调用，PagerAdapter方式中不会调用。

（2）对应的isHidden()方法，只对show/hide方式有用。

（3）show/hide触发时只针对当前fragment有用，对其子fragment没有作用，即子fragment不会回调onHiddenChanged方法。



2，  setUserVisibleHint(booleanisVisibleToUser) **已弃用**

（1）只在PagerAdapter方式中回调调用。

（2）Fragment的PagerAdapter包括FragmentStatePagerAdapter和FragmentPagerAdapter两个子抽象类。

（2）该方法在viewPager中失效，无论Fragment是否可见，都**不会**执行该方法。

 

3，  Fragment的isVisible()判断方法

（1）在PagerAdapter方式中不准确，即fragment不是PagerAdapter当前显示的fragment时也会是true。

 

4 getUserVisibleHint(）**已弃用**

（1）该方法用于判断Fragment是否可见，但是在viewPager中失效，无论Fragment是否可见，都**会**执行该方法。



### 总结：

要真正判断fragment是否处于可见显示状态，要综合考虑fragment的添加方式和其生命周期来处理

（1） 生命周期可见状态变化时作出相应变化，如onResume，onPause中。

（2） 同时针对onHiddenChanged和setUserVisibleHint两种情况来监听可见性变化。

（3） 嵌套的fragment，子fragment不会随父fragment可见性变化而主动变化。

---

## 60 post方法请求数据

我们请求的数据:

`String data = "passwd=" + URLEncoder.encode(passwd, "UTF-8") + "&number=" + URLEncoder.encode(number, "UTF-8");`

参数与参数名之间用 `&` 隔开

---

## 61 Fragment传值回Activity

Frament中

```java
public interface CallBackListener{
    public void sendValue(String value);
}

private CallBackListener listener;
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    activity=requireActivity();
    listener=(CallBackListener) activity;
}

//回调处
listener.sendValue(key);
```

Activity中：implements接口，重写方法

```java
public class QueryActivity extends BaseActivity<QueryPresenter, Query.VP> implements HeatedWordsFragment.CallBackListener {
    @Override
    public void sendValue(String value) {
        loadFragment();
        if (!loadingFragment.isAdded()){
            transaction.hide(heatedWordsFragment).add(R.id.fragment_query,loadingFragment).show(loadingFragment).commit();
        }else {
            transaction.hide(heatedWordsFragment).show(loadingFragment).commit();
        }
        getContract().requestQueryVP(value,0);
    }
}    
```

---

## 62 流式布局

### 动态设置TextView

```java
TextView tv = new TextView(activity);
tv.setText((String)heatedWordsMap.get("name"));
tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
tv.setGravity(Gravity.CENTER);
int paddingy = DisplayUtils.dp2px( 7);
int paddingx = DisplayUtils.dp2px( 6);
tv.setPadding(paddingx, paddingy, paddingx, paddingy);
tv.setClickable(false);

int shape = GradientDrawable.RECTANGLE;
int radius = DisplayUtils.dp2px( 14);
int strokeWeight = DisplayUtils.dp2px( 2);
int stokeColor = getResources().getColor(R.color.green);
int stokeColor2 = getResources().getColor(R.color.gray);

GradientDrawable drawableDefault = new GradientDrawable();
drawableDefault.setShape(shape);
drawableDefault.setCornerRadius(radius);
drawableDefault.setStroke(strokeWeight, stokeColor);
drawableDefault.setColor(ContextCompat.getColor(activity,R.color.transparent1));

GradientDrawable drawableChecked = new GradientDrawable();
drawableChecked.setShape(shape);
drawableChecked.setCornerRadius(radius);
//            drawableChecked.setColor(ContextCompat.getColor(TestFlowLayoutActivity.this, android.R.color.darker_gray));
drawableChecked.setColor(ContextCompat.getColor(activity,R.color.shallow_gray));

StateListDrawable stateListDrawable = new StateListDrawable();
stateListDrawable.addState(new int[]{android.R.attr.state_checked}, drawableChecked);
stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableChecked);

stateListDrawable.addState(new int[]{}, drawableDefault);

tv.setBackground(stateListDrawable);
//ColorStateList colorStateList = DrawableUtils.getColorSelector(getResources().getColor(R.color.black), getResources().getColor(R.color.white));
//tv.setTextColor(getResources().getColor(R.color.gray));
tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String key=tv.getText().toString();
        //System.out.println(key);
        edtKeyword.setText(key);
        listener.sendValue(key);
        //dialog = ProgressDialog.show(activity, "", "正在加载", false, false);
        //getContract().requestQueryVP(key,0);
    }
});
flowLayout.addView(tv);
```

---

## 63 服务--定时任务

```java
public class LongRunningTimeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Long secondsNextMorning =getSecondsNext(1,10);
        Intent intentMorning = new Intent(this, AlarmBroadcastReceiver.class);
        intentMorning.setAction("CLOCK_IN");
        //获取到PendingIntent的意图对象
        PendingIntent piMorning = PendingIntent.getBroadcast(this, 0, intentMorning, PendingIntent.FLAG_IMMUTABLE);     //设置事件
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + secondsNextMorning, piMorning); //提交事件，发送给 广播接收器
        return super.onStartCommand(intent, flags, startId);
    }

    private Long getSecondsNext(int hour,int minute) {
        long systemTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 选择的定时时间
        long selectTime = calendar.getTimeInMillis();
        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            selectTime = calendar.getTimeInMillis();
        }
        // 计算设定时间到现在时间的时间差
        Long seconds = selectTime-systemTime;

        return seconds.longValue();
    }
}
```

---

## 64 发送通知

```java
NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
PendingIntent pendingIntent;
Intent intent=new Intent(MainActivity.this,MainActivity.class);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    NotificationChannel mChannel = new NotificationChannel("channelId", "123", NotificationManager.IMPORTANCE_HIGH);
    manager.createNotificationChannel(mChannel);
}
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
    pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
} else {
    pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
}
Notification notification=new NotificationCompat.Builder(MainActivity.this,"channelId")
        .setContentTitle("This is content title")
        .setContentText("This is content text")
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.drawable.ic_notification)
        .setContentIntent(pendingIntent)
        .build();
manager.notify(1,notification);
```

---

### Notification通过intent传递参数后结果为空的解决办法

```java
PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
```

---

## 65 动态调用AS自带的color

`android.R.color.transparent`

---

## 66 测量控件或者布局的宽或高

```java
//制定测量规则 参数表示size + mode
int width = View.MeasureSpec.makeMeasureSpec(ZERO,View.MeasureSpec.UNSPECIFIED);
int height = View.MeasureSpec.makeMeasureSpec(ZERO,View.MeasureSpec.UNSPECIFIED);
//调用measure方法之后就可以获取宽高
imgBanner.measure(width, height);
ConstraintLayout layout1=fragmentActivity.findViewById(R.id.fragment_home);
System.out.println("layout"+layout1);
layout1.measure(width,height);
System.out.println(layout1.getMeasuredWidth());
System.out.println(imgBanner.getWidth());
System.out.println(imgBanner.getMeasuredWidth());
imgBanner.setImageBitmap(bitmap);
//设置图片前后的ImageView的宽度不一样
imgBanner.measure(width, height);
System.out.println(imgBanner.getMeasuredWidth());
viewPager.measure(width,height);
System.out.println(viewPager.getMeasuredWidth());
```

---

## 67 一些复制粘贴过来的代码规范

### 1 代码命名规范

* 类与接口大驼峰

* 变量与方法小驼峰

* 接口的默认实现类的类名在接口后加Impl，如 Connector 与 ConnectorImpl

* 静态常量采用全部大写+下划线法

  ```java
  private static final int NETWORK_CONNECT_CODE = 200;
  ```

* 布尔类型变量**不加** is 前缀

  > 如果布尔类型变量加入 is 前缀，部分框架序列化可能会导致出现异常，同时也难以与 Kotlin 代码互调用

* 函数式接口添加函数式接口注解

  ```java
  @FunctionalInterface
  public interface ResultCallback {
      fun onGetResult(String result);
  }
  ```

  > 可有效减少匿名接口实现的代码量，并提高可读性

* 模块中的方法废弃时不应立即删除，而应添加 @Deprecated 注解并提供实现的新方法，提醒使用者换用新的实现方法

  > 避免更新后导致依赖该模块的代码出现问题

---

### 2 资源命名规范

下文中所提及的**模块指分包层面的模块**，module 将被标注为组件

#### Layout

采用下划线法命名方式，格式为 **前缀 + 模块 + 说明**

前缀包括以下类型

- activity    activity 布局

- fragment    fragment 布局

- widget    自定义组件布局

- item    列表元素布局

- layout    其它类型布局

示例：

fragment_fliter.xml、

widget_progress_bar.xml、

item_music_history_list.xml

#### Drawable

采用下划线法命名方式，格式为 **前缀 + 模块 + 说明**

前缀包括以下类型

- ic    图标

- bg    背景图

- img    图片

位图文件尽可能采用 webp 格式保存，降低空间占用

图标文件尽可能采用矢量图保存，避免放大失真

可复用的资源省略不需要加模块部分

示例：ic_back_arrow.xml、bg_home_forest.webp

#### Menu

- 整个菜单的资源文件采用下划线法命名方式，格式为 **模块 + 说明**

  一个模块内有多个菜单资源需添加必要说明

  可复用的资源模块部分根据需要进行命名

  示例：playlist_toolbar.xml、playlist_item_operation.xml

- 某一个菜单项采用下划线命名方式，格式为 **menu 前缀 + 资源文件名 + 说明**

  示例：menu_playlist_toolbar_play、menu_playlist_item_operation_play_next

#### Animation

采用下划线法命名方式，格式为 **模块 + 功能 + 动画效果 + 方向**

示例：login_button_fade_in.xml

#### String

该资源置于 values 中

特定资源采用下划线法命名方式，格式为 **模块 + 说明**

示例：app_name、message_channel_id

用于界面显示的资源因常被多模块复用，不再需要对功能或所在模块进行说明，采用下划线法命名方式，格式为 **内容概括**

示例：loading、list_empty_hint、show_details

#### Color

该资源置于 values 中

采用下划线法命名方式，格式为 **模块 + 说明**

可复用的资源模块部分统一命名为 app

示例：app_primary、home_accent

#### Style

该资源置于 values 中

采用 Pascal 命名方式，以类包名格式命名，格式为 **类型 + 模块 + 说明**

类型区分 Theme、Style、ShapeAppearance 等

可复用的资源模块部分根据需要进行命名，比如 Base

示例：Theme.Account.Toolbar、Style.PlaylistItem.Title

#### Attribute

该资源置于 values 中

采用下划线法命名方式，格式为 **自定义控件 + 说明**

示例：circle_image_back_color、progress_bar_load_progress

如果多个自定义控件可复用，可独立出来，直接对属性进行说明，注意不与原有属性发生冲突

示例：back_color、load_progress

#### Resource ID

采用下划线法命名方式，格式为 **类型 + 模块 + 说明**

类型为控件缩写，常用组件缩写如下表，其它组件团队内讨论确定

|  控件全称   | 缩写 |
| :---------: | :--: |
|   Button    | btn  |
|  TextView   |  tv  |
|  ListView   |  lv  |
| RecycleView |  rv  |
|  ImageView  |  iv  |
| ImageButton | ibtn |
|  EditText   | edt  |
|   Spinner   |  sp  |
|   Toolbar   |  tb  |
|  ViewPager  |  vp  |
|  TabLayout  | tab  |

示例：edt_login_password、iv_profile_avatar

#### 资源文件分离

若需要对资源根据模块保存为多个文件，则资源文件采用下划线命名方式，格式为 **类型 + 模块**

示例：string_home.xml、color_playlist.xml、attr_progress_bar.xml

---

### 3 版本控制

#### commit提交格式

`Type类型(scope作用域):Description描述`

##### Type 类型

Type 为必须项，拼写采用小写，包括以下类型

|     类型     |                        说明                        |
| :----------: | :------------------------------------------------: |
|   **feat**   |                在代码中添加新的特性                |
|   **fix**    |               修复代码中的漏洞或问题               |
| **refactor** |       对代码进行重构，包括文件或包的重新命名       |
|  **style**   |                    代码格式更改                    |
|   **docs**   |                     文档的变更                     |
|     perf     |                优化相关，如提升性能                |
|     test     |                测试相关，如单元测试                |
|  **chore**   |         其它流程，如**依赖管理**、构建流程         |
|   **none**   | 不对代码进行任何影响的相关，比如注释或 TODO 的变更 |

##### Scope 作用域

Scope 为可选项，用于定位本次提交影响的范围，可以为包名、层级或类名

作用域为项目（比如依赖更新等）可指定为 project

##### Description 描述

Description 为必须项，是对添加或修改内容的说明与概括

中文末尾不加句号

##### 注意

当本次提交与上一版本代码不兼容时，需在 Type 后添加感叹号作为标记（详见 Footer 部分的 BREAKING CHANGE）

**BREAKING CHANGE**，当本次提交与上一版本代码不兼容时，需在此以完全大写的 BREAKING CHANGE 开头，后接说明

##### 示例

```
feat(login): 在登录窗口中进行用户过期判断
```

```
chore!(project): XXX 依赖更新到 1.2.4 版本

本次依赖更新可用于简化 XX 功能的实现

BREAKING CHANGE 新依赖与旧版本 API 存在冲突
```

```
fix(search): 修复了点击搜索框应用会崩溃的漏洞

Closes issue #240
```

#### Branch

分支命名如下

- 默认分支命名为 `master` 或 `main`
- 与 issue 对应，分支命名为 `issue-<issue 编号>`
- 紧急修复内容，分支命名为 `hotfix-<修复说明>`
- 项目内对 Gradle 等与构建相关的更改，分支命名为 `chore-<修复说明>`
- 对代码进行重构，需创建对应 issue，分支命名为 `refactor-<issue 编号>`
- 添加、修改、删除文档，分支命名为 `doc-<变更说明>`
- 添加、修改、删除项目相关或服务相关（如持续集成、模版等），分支命名为 `pro-<变更说明>`

##### 示例

```
issue-1
issue-624
hotfix-share
chore-update-gradle
refactor-15
```

#### 在Terminal中切换分支

checkout签出dev分支

`git -c credential.helper= -c core.quotepath=false -c log.showSignature=false checkout dev --`

---

### 4 分包规范

#### 原则

- 分包遵循 PBF（package-by-feather，功能性分包），降低各个功能包之间的耦合度，确保功能包内高内聚、高度模块化

- 分包规范适用于新项目开发，维护原有项目不修改分包

#### 包名规范

- 包的名称总是小写且不使用下划线

- 每个包名不宜过长，尽可能采用单个单词，如确实需要多个单词则单词间无分隔

示例：com.topview.aircraft、com.tencent.mm

#### MVP

```
+-- application        Application
+-- entity             实体类
+-- base               用于被继承的基类或用于全局的数据绑定适配器
+-- examples...        根据业务模块分包
| +-- storage            本地储存访问（包括数据库与 Shared Preferences）
| | +-- database         数据库访问
| | +-- shared           Shared Preferences 访问
| +-- network            网络访问
+-- widget             可复用的自定义控件
+-- util               可复用的工具包
+-- manager            数据管理包
```

> 如果业务模块为界面，则其 View、Model、Presenter、Contract 不必再进行分包
>
> 其它内容根据需要可进行分包，例如：
>
> - adapter    适配器
>
> - binding    数据绑定适配器
>
> - widget    业务模块控件（可复用的控件应独立到外部的 widget 包）
>
> 如果一个业务模块内有多个界面，则根据界面再进行分包
>
> 后台服务与界面分隔为不同的模块
>
> Manager 用于管理跨模块的数据，比如音乐应用中的播放列表，需注意与持久化数据的区别

+ com.yourname.www.entity（与数据库表一一对应的实体类）

+ com.yourname.www.util（工具类，与业务逻辑无关，通常为各个项目都可以通用的代码）

+ com.yourname.www.dao（放置数据库或文件读写相关的代码）

+ com.yourname.www.service（放置处理业务逻辑的代码，隔离dao层与view层）

+ com.yourname.www.view（放置与页面相关的代码，图形化界面相关代码，或者文字界面相关代码）

### 5 架构规范

#### MVP

- 由 Model 处理网络数据与本地数据的切换

- Model 与 View 不应互相持有引用

  > 模块解耦

- 可复用的 Model 层应拆分成独立模块，用于被继承

  > 代码复用
  >
  > 举例说明，一个时间管理应用的首页与管理界面都可以访问今天的事务列表，而两个界面的 Model 可以通过继承同一个 Model 来调用同一个获取今日事务列表的方法

---

