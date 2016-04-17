# js parser解析classpath下的某一个xx.js文件


####settingsKey必须是在settings.properties之中注册的key
value即为xx.js文件的路径（相对于classpath的）


####例子
```
		Map m = JSParser.parse("settings");
		System.out.println(m);
		assertEquals(JSParser.get("settings", ".working_directory.windows.command"), "%tmp%");
```


####js文件中的格式只能是这样子（js对象）：【将会被解析成map】
```
{
	working_directory: {
		windows: {
			command: "%tmp%",
		},
		
		linux: {
			path: "~",/*或者是/tmp*/
		}
	},
	
	
	zk_url: "192.168.1.100:2181",
}
```
