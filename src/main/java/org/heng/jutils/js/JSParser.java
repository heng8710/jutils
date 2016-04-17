package org.heng.jutils.js;

import static org.heng.jutils.log.loghelper.LogHelper.info;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.heng.jutils.jsonpath.JsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JSParser {
	
	
	public static final String DEFAULT_JSFILE = "settings.js";
	
	
	/**
	 * 把classpath下的某一个xx.js文件，解析成一个map对象，原来的xx.js文件中是：{ a: 123, b: 'hello'}的js对象格式。<br/>
	 * 注意了，xx.js文件中（就像js对象语法中一样），逗号宁可多也不可少（多了不会出错，少了会出错） <br/>
	 * 
	 * @param configFilePath：相对于[classes]的路径；基于文件系统的路径表示方法，最好是用[/]作为分隔符，【要带.js结尾（注意大小 写）】
	 * @return
	 */
	public static Map<String,Object> parse(final String jsFile){
		
		try {
			//启动js引擎
			final ScriptEngineManager sem = new ScriptEngineManager();
			final ScriptEngine engine = sem.getEngineByName("javascript");
			
			final URL settings = JSParser.class.getClassLoader().getResource(jsFile);
			info(String.format("jsFile=[%s]", settings));
			if(settings == null){
				throw new IllegalStateException(String.format("%s 找不到", jsFile));
			}
		    final String objStr = Files.toString(Paths.get(settings.toURI()).toFile(), Charsets.UTF_8);
		    final String root = "root";
		    //把配置对象字面量（js），转成json格式字符串出来
		    final String str = (String)engine.eval(String.format("var %s=%s; JSON.stringify(%s);", root, objStr, root));
		    //然后，再转成java中的LinkedHashMap
		    final Map<String,Object> map = new ObjectMapper().readValue(str, Map.class);
		    return map;
		} catch (final Exception e) {
			throw new IllegalStateException(String.format("读取配置文件=[%s]失败", jsFile) , e);
		}
	}
	
	
	
	
	public static Object get(final String path){
		return get(DEFAULT_JSFILE, path);
	}
	
	public static Object get(final String jsFile, final String path){
		final Map<String, Object> root = parse(jsFile);
		
		if(root == null || root.isEmpty()){
			return null;
		}
		return JsonPath.getByPath(root, path);
	}
	
	
	public static String getString(final String path){
		return getString(DEFAULT_JSFILE, path);
	}
	
	public static String getString(final String jsFile, final String path){
		final Object r = get(jsFile, path);
		if(r == null){
			return null;
		}
		return r.toString();
	}
	
	public static Integer getInteger(final String path){
		return getInteger(DEFAULT_JSFILE, path);
	}
	public static Integer getInteger(final String jsFile, final String path){
		final Object r = get(jsFile, path);
		if(r == null){
			return null;
		}
		return Integer.valueOf(r.toString());
	}
}
