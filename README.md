# MyFramework
这是我根据工作经验总结出来的一些小东西

# 1 代码规范
## 1.1     Class 命名
 任何类名都应该使用 UpperCamelCase （驼峰法）命名, 例如:

      AndroidActivity, NetworkHelper, UserFragment, PerActivity

## 1.2 变量名。

 -  变量名：静态常量使用大写字母，使用下划线“_”分词。
 -  非静态成员变量、局部变量、首字母小写，驼峰式分词。
 -  Activity、Fragment、Adapter、View 的子类的成员变量：m开头、驼峰式分词。
 -  方法名：首字母小写，驼峰式分词。

# 1.3 成员变量定义顺序（建议）

- 公用静态常量
- 公用静态变量
- 私用静态常量
- 私用静态变量
- 私有非静态变量

## 1.4 禁止”魔术数”：

    switch(i) {
      case 1:
        mManager.updloadTag();
      break;
        mManager.uploadTabMore();
      case 2:
        // do something
      break;
       // ...
      case 3:
       // ....
      break;
    }

代码中的 case 后的数字即为魔术数，应该使用有明示意义的常量代替。

## 1.5 禁止忽略异常：

  public void setUserId(String id) {
      try {
          mUserId = Integer.parseInt(id);
      } catch (NumberFormatException e) { }
  }


## 1.6 避免UI线程IO：

 不要在主线程执行IO或网络的操作 ( 卡顿工具检查主线程的方法时间设置阀值，超过用子线程去执行 )。

# 1.7 非静态匿名内部类请用WeakReference 方式持有外部对象的引用

    Runnable timeCallback = new Runnable () {
      if(mRef ! = null) {
        Activity activity = mRef.get();
        if(activity != null) {
         // do stuff
        }
      }

    }


# 1.8 长度限制

-  一行代码的长度：不要超过160个字符。
-  一个方法的长度：不要超过：80行。
- 一个文件的长度不超过：1000行。
- 一个方法的参数列表不要超过：7个。
- if 嵌套层次：不要超过4层。
- 无用代码
- 禁止无用的import。
- 禁止import ＊。
- 禁止无用变量。

# 2  Resource 命名

## 2.1 Activity、Fragment、Dialog的布局命名：activity/fragment/dialog+模块，小写字母使用下划线 ”_ ”分词。
 例如：

     activity_main, fragment_user,dialog_login_input.xml


## 2.2 控件布局命名：模块名＋布局类型，小写字母使用下划线 ”_ ”分词。例如：goods_list_item。

## 2.3 图片资源

不再使用的布局资源及时删除。

## 2.2 图片

图片以ic_为前缀，与功能、状态一起命名。例如：

    ic_accept

其他 drawable 文件应该使用相应的前缀，例如：

|类型    |	前缀	    |例如   |
|----- |---- |---- |
|Selector	|selector_	|selector_button_cancel|
|Background	|bg_	|bg_rounded_button|
|Circle	|circle_	|circle_white|
|Progress	|progress_	|progress_circle_purple|
|Divider	|divider_	|divider_grey|

## 2.3 字符串命名

相同英文含义，小写字母使用下划线 ”_ ”分词。

## 2.4 其他
避免使用"px"作为单位。

# 3 项目架构

## 3.1 项目框架模式

进过多年的项目开发总结得出，较为适合本人的开发模式：MVP

model：数据请求模型，个人较为习惯将其作为数据请求，包括网络请求，本地数据库请求。可以根据个人喜好再再做处理，可以直接转成需要的数据模型，也可以直接返回，由p层进行数据转换。

view：UI展示，为了方便业务调整，以及UI与业务分离，采用了EventBus作为信息传递通道用于宏指令操作：比如token过期需要重新登陆，由EventBus进行通知各个页面并及时响应

presenter: 业务处理，和Controller的作用是一样的，用于业务处理，数据绑定，只是不同点在于数据来源以及UI的刷新，处理方式一般是异步处理，拿到数据之后才会响应操作

注：这是目前一步步总结下来的结果，还未研究大牛们的项目框架，后续还会进一步优化

## 3.2 项目结构

项目结构采用模块话结构：basemodule、widget、othermodule...

basemodule：项目核心模块
- app：基类Activity、基类Fragment、Application
- bean：基类数据请求模型、EventBus数据模型、other数据模型
- cache：应用数据缓存
- code：APIUrl
- model：基类数据请求模型
- network：http接口封装、基类request、otherRequest
- presenter：基类ActivityPresenter、基类FragmentPresenter

widget：通用工具模块
-  adapter：各类适配器
-  amap：高德地图(根据需要调整LBS)
-  bar：状态栏背景
-  dialog：通用对话框（建议只这里只放通用版本，模块独有的dialog请在各自的模块中再添加一个widget/dialog）
-  push:推送工具(根据需要调整推送SDK)
-  recyclerview：这是使用了大牛的框架，并进行了微调，是对RecyclerView的封装使用，可以进行刷新、上拉加载、删除等list操作
-  scan：二维码扫码
-  utils：各类通用工具：图片压缩、Empji过滤、Gson封装、TimeCount等等...(根据需要添加，最好是分类添加，避免使用时花时间查找工具类)
-  view：自定义View

othermodule：各个业务模块需要独立开来，可以引用核心模块、通用模块，尽量避免各个业务模块之间的相互调用（页面启动除外）

### 3.2.1 recyclerview封装使用

# 项目成员
Muyunfan，Zuimenglong
