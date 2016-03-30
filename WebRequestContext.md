#Web Request Context


####该包中的都是类似的工具类
`QueryParamContext`通过query param获取层次化参数
`CookieContext`通过请求的cookie k/v获取层次化参数
`WebRequestContext`提供一个类似的容器，在request范围之内，方便获取/设置层次化参数

####本质上，都是针对uri=`/*`的一个`Servlet Filter`而已

####起初的目的就是通过它来获取http request中的分页请求参数
page.xxx
再在应用当中通过：`WebRequestContext.get(".page.xxx")`，再取回来。


####使用的是ThreadLocal存储的，所以不要跨线程使用
如果有的话，就先取下来，再传递就好了。

