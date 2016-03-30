# jsonpath

####主要是为了应对json<-->map,list的情况，不想在许多项目中都去定义很多很多java bean
直接使用map,list就算了

####提供了getter,setter去转化map,list的内嵌元素
用apache-beanutils处理的话，如果中间节点为null，那么set是不成功的

####map,list对应语法：`.xxx`,`[123]`
set好像可以不需要……？？

####举例：
```
Map m = new HashMap();
JsonPath.setByPath(m, "7796", ".a.b.c.d[3][1].e[2].f[0]");
System.out.println(JsonPath.getByPath(m, ".a.b.c.d[3][1].e[2]"));
System.out.println(m);
```

结果：
```
{f=[7796]}
{a={b={c={d=[null, null, null, [null, {e=[null, null, {f=[7796]}]}]]}}}}
```

