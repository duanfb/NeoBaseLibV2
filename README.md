# NeoBaseLibV2
Android BaseLib Version 2

## 2017-06-01
### 接口请求说明：
+ **添加请求类**

        1.新建BaseRequest并实现IBaseRequest
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
    
+ ps：Contract是一个契约接口，没有实现，只是将MVP三个接口管理在了一起，我们找到相关的contract即可知道
        对应的界面有哪些逻辑。具体实现通过实现类去实现。
        M:Contract.Interactor数据提供，可以是网络或者数据库
        V:Contract.View界面相关
        P:Contract.Presenter主持人，从Interactor(数据层)拿到数据从而指挥View(界面相关)

### 技术选型：
+ **Fresco**：高效解决图片加载OOM问题
+ **Butterknife**：通过注解方式findViewById和setOnClickListener等，其发生在编译期，不会影响app性能
+ **Retrofit**：高效网络请求，定制化强，并有取消请求功能
+ **Fastjson**：高效服务器数据序列化
+ **EventBus**：模块之间通信
+ **RecycleView Adapter**：新建Adapter集成XBaseAdapter

### UI篇：

	-BaseActivity封装了顶部和内容布局，未完待续
	同理BaseFragment
	
### 网络框架篇：
	HttpLoader处理所有请求:
	    HttpLoaderConfiguration configuration = new HttpLoaderConfiguration.Builder()
                    .setServerHost(Constants.SERVER_HOST) //配置服务器地址
                    .setEnableCache(false) //是否开启网络缓存
                    .setTimeout(15) //超时时间设置，单位秒
                    .setHttpHandler(HttpHandler.class) //设置网络返回处理类
                    .build();
        HttpLoader.getInstance().init(configuration);
        
        HttpHandler:
            extends BaseHttpHandler,并实现方法，可参考DefaultHttpHandler
	
### 图片加载篇：
	使用Facebook出品的Fresco加载图片，并且封装在XImageView中，App使用的所有需要加载网络图篇的请求请使用XImageView引用，
	方便日后的框架替换和维护
	
### 业务逻辑MVP：
	采用MVP方式：
		-M：即MVP包中interactor包，代表数据提供器
		-V：在contract契约类中，操作VIew层的接口
		-P：即MVP包中的present包,代表业务处理
		管理mvp：引入contract契约类管理mvp接口，减少类的数量


