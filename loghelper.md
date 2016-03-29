#log helper


####为了让日志调用时的msg，只需要传有意义的消息，而不需要传上下文信息

####自动关联所调用的上一层方法，这经常是一个业务方法，而且，按照命令约定原则，（类名+方法名）经常 就能代表所操作的事情了。

####默认关联于slf4j

####例子：
```
LogHelper.info("hello info");
LogHelper.error("hello err", new RuntimeException("toString errrrrrrrrr"));
```


输出：
```
2016-03-29 23:26:16,914 [main] [LogHelper.java:83] [INFO LogHelper:all] - [LogHelperTest].[test] line=[19] msg=[hello info]
2016-03-29 23:26:16,920 [main] [LogHelper.java:131] [ERROR LogHelper:all] - [LogHelperTest$1].[lambda$0] line=[40] msg=[hello err]
java.lang.RuntimeException: toString errrrrrrrrr
	at org.heng.jutils.log.loghelper.LogHelperTest$1.lambda$0(LogHelperTest.java:40)
	at org.heng.jutils.log.loghelper.LogHelperTest$1$$Lambda$2/892529689.accept(Unknown Source)
	at org.heng.jutils.log.loghelper.LogHelperTest$1.toString(LogHelperTest.java:43)
	at org.heng.jutils.log.loghelper.LogHelperTest.test(LogHelperTest.java:46)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:483)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)

```


标志点：
`LogHelper:all`是这种方式的标志字符串开头。
 
```
[ERROR LogHelper:all]
```

下面分别对应了类名、方法名、所在的文件行数、消息体
```
[LogHelperTest$1].[lambda$0] line=[40] msg=[hello err]
```