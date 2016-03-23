#Web Request Context


####起初的目的就是通过它来获取http request中的分页请求参数
page.xxx
再在应用当中通过：`WebRequestContext.get(".page.xxx")`，再取回来。


####使用的是ThreadLocal存储的，所以不要跨线程使用
如果有的话，就先取下来，再传递就好了。


####`WebRequestContext`中扩充`visitors`可以实现别的逻辑
暂时还没有想到其他的扩展方式。