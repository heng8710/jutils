package org.heng.jutils.js;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.heng.jutils.jsonpath.JsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JSParser {
	
	//在settings.properties中对应于settings.js的位置
	private static final String JS_SETTINGS_KEY = "settings";
	
	
	/**
	 * 把classpath下的某一个xx.js文件，解析成一个map对象，原来的xx.js文件中是：{ a: 123, b: 'hello'}的js对象格式。<br/>
	 * 注意了，xx.js文件中（就像js对象语法中一样），逗号宁可多也不可少（多了不会出错，少了会出错） <br/>
	 * 
	 * @param configFilePath：相对于[classes]的路径；基于文件系统的路径表示方法，最好是用[/]作为分隔符，【要带.js结尾（注意大小 写）】
	 * @return
	 */
	public static Map<String,Object> parse(@NonNull final String settingsKey){
		
		try {
			final URI jsSettings = SettingsHelper.relativeFile(settingsKey);
			
			//启动js引擎
			final ScriptEngineManager sem = new ScriptEngineManager();
			final ScriptEngine engine = sem.getEngineByName("javascript");
			
		    final String objStr = Files.toString(Paths.get(jsSettings).toFile(), Charsets.UTF_8);
		    final String root = "root";
		    //把配置对象字面量（js），转成json格式字符串出来
		    final String str = (String)engine.eval(String.format("var %s=%s; JSON.stringify(%s);", root, objStr, root));
		    //然后，再转成java中的LinkedHashMap
		    final Map<String,Object> map = new ObjectMapper().readValue(str, Map.class);
		    return map;
		} catch (final Exception e) {
			throw new IllegalStateException(String.format("解析settings[%s]失败", settingsKey), e);
		}
	}
	
	
	
	
	public static Object get(final String path){
		return get(JS_SETTINGS_KEY, path);
	}
	
	public static Object get(final String settingsKey, final String path){
		final Map<String, Object> root = parse(settingsKey);
		
		if(root == null || root.isEmpty()){
			return null;
		}
		return JsonPath.getByPath(root, path);
	}
	
	
	public static String getString(final String path){
		return getString(JS_SETTINGS_KEY, path);
	}
	
	public static String getString(final String settingsKey, final String path){
		final Object r = get(settingsKey, path);
		if(r == null){
			return null;
		}
		return r.toString();
	}
	
	public static Integer getInteger(final String path){
		return getInteger(JS_SETTINGS_KEY, path);
	}
	public static Integer getInteger(final String settingsKey, final String path){
		final Object r = get(settingsKey, path);
		if(r == null){
			return null;
		}
		return Integer.valueOf(r.toString());
	}
}
