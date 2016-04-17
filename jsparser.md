# js parser解析classpath下的某一个xx.js文件


####例子
```
		Map m = JSParser.parse("settings.js");
		System.out.println(m);
		assertEquals(JSParser.get("settings.js", ".working_directory.windows.command"), "%tmp%");
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
