# NeoBaseLibV2
Android BaseLib Version 2

##2017-06-01
###接口请求说明：
    + **添加请求类**
        1.在com.android.weiyi.nei.request包中新建一个Request，继承BaseRequest
        2.add(key,value)可以添加请求参数，公共参数已经添加无需关系，只要添加业务相关参数
        3.getApi()添加必要api
        4.getResponseClazz()添加必要ResponseData序列化类
    
    + **新建MVP，可参考登录相关**
        1.mvp.contract包中新建一个相关的Contract接口，里面包含操作的View，Presenter,Intractor接口
        2.分别在presenter和interactor包中建立实现类，实现Contaract声明的接口
        3.
    
    + **MVP使用方式**
        1.对应的Activity实现View接口，例如登录LoginActivity实现LoginContract.View接口
        2. Activity的initPresents中创建Presenter具体实例
           @Override
           public LoginContract.Presenter initPresents() {
               return new LoginPresenterImpl(this);
           }
        3.基类的mPresenter即可使用Presenter中相应方法
    
    ps：Contract是一个契约接口，没有实现，只是将MVP三个接口管理在了一起，我们找到相关的contract即可知道
        对应的界面有哪些逻辑。具体实现通过实现类去实现。
        M:Contract.Interactor数据提供，可以是网络或者数据库
        V:Contract.View界面相关
        P:Contract.Presenter主持人，从Interactor(数据层)拿到数据从而指挥View(界面相关)

##2016-10-09
###添加了MVP范例：
    + **mvp包中含有：contract(契约类)、interactor数据提供、presenter逻辑处理**
        1.contract:契约类，管理mvp接口，基本每个界面一个contaract，里面View,Presenter,Interactor接口名不变
        2.interactor:数据提供器，为contaract定义接口的实现，需要继承BaseInteractorImpl
        3.presenter:逻辑处理，为contaract中Presenter接口实现，需要继承BasePresenterImpl

        ps：presenter主要协调界面和数据的调度，具体的Activity中创建PresenterImpl的实例，可参考LoginActivity


##2016-9-21
###添加了moco本地服务器(WeiYi/moco目录下)：
    + **使用**
        1.修改com.neo.duan.utils.constants.Constants.SERVER_HOST_DEV中的ip地址为本机ip地址
        2.start.bat开启本地服务器
        3.进入app即可
    + **自定义数据格式**
         1.模仿UserLogin.json新建自己的数据接口返回json(注意：定义好请求url)
         2.在moco目录下的config.json中添加刚刚的json文件名
         3.启动start，json文件可以随时修改并且是即时生效



###NeoBaseLib库地址：https://github.com/duanfb/NeoBaseLib
    + **使用**：下载该库，将项目放到WeiYi根目录然后同步gradle即可

###技术选型：
+ **Fresco**：高效解决图片加载OOM问题
+ **Butterknife**：通过注解方式findViewById和setOnClickListener等，其发生在编译期，不会影响app性能
+ **Retrofit**：高效网络请求，定制化强，并有取消请求功能
+ **Fastjson**：高效服务器数据序列化
+ **EventBus**：模块之间通信

###UI篇：
	-BaseActivity封装了顶部和内容布局，未完待续

	同理BaseFragment
###网络框架篇：
	HttpLoader处理所有请求，未完待续
###图片加载篇：
	使用Facebook出品的Fresco加载图片，并且封装在XImageView中，App使用的所有需要加载网络图篇的请求请使用XImageView引用，
	方便日后的框架替换和维护
###业务逻辑MVP：
	采用MVP方式：
		-M：即MVP包中interactor包，代表数据提供器
		-V：在contract契约类中，操作VIew层的接口
		-P：即MVP包中的present包,代表业务处理
		管理mvp：引入contract契约类管理mvp接口，减少类的数量


