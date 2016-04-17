package org.heng.jutils.js;

import static org.heng.jutils.log.loghelper.LogHelper.debug;

import java.net.URI;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Strings;

import lombok.experimental.UtilityClass;

@UtilityClass
//@Slf4j
class SettingsHelper {
	
	private static final String SETTINGS_FILE = "settings.properties";
	
	
	
	
//	public static URI relativeFile( ){
//		return relativeFile(JS_SETTINGS_KEY);
//	}
	
	
	public static URI relativeFile(final String name){
		try {
			final URL settings = JSParser.class.getClassLoader().getResource(SETTINGS_FILE);
			debug(String.format("classpath:settings.properties=[%s]", settings));
			if(settings == null){
				throw new IllegalStateException("classpath:settings.properties=[%s] 找不到");
			}
			final Properties ps = new Properties();
			ps.load(JSParser.class.getClassLoader().getResource(SETTINGS_FILE).openStream());
			final String relativeSettingsFileName = ps.getProperty(name);
			if(Strings.isNullOrEmpty(relativeSettingsFileName)){
				throw new IllegalStateException(String.format("settings.properties之中找到对应当前环境的[%s]配置项", name));
			}
			
			
			final URL relativeSettingsFile = JSParser.class.getClassLoader().getResource(relativeSettingsFileName);
			debug(String.format("%s=[%s]",name, relativeSettingsFile));
			if(relativeSettingsFile == null){
				throw new IllegalStateException(String.format("%s=[%s] 找不到", name, relativeSettingsFileName));
			}
			
			return relativeSettingsFile.toURI();
		}catch(Exception e){
			throw new IllegalStateException("读取settings出错", e);
		}
	}
}
