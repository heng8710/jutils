#Web Request Context


####该包中的都是类似的工具类
- `QueryParamContext`通过将请求的query param处理，提供了获取层次化参数
- `CookieContext`通过将请求的cookie k/v处理，提供了获取层次化参数
- `WebRequestContext`提供一个类似的空容器，在request范围之内，方便获取/设置层次化参数

####本质上，都是针对uri=`/*`的一个`Servlet Filter`而已

####如果没有效果的话，请检查项目的web.xml，多半又是那个metadata-complete参数被设置为true了
导致注解失效


####起初的目的就是通过它来获取http request中的分页请求参数
page.xxx
再在应用当中通过：`QueryParamContext.get(".page.xxx")`，再取回来。


####使用的是ThreadLocal存储的，所以不要跨线程使用
如果有的话，就先取下来，再传递就好了。

