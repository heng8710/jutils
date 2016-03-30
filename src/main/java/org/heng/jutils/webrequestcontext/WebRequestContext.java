package org.heng.jutils.webrequestcontext;


import static org.heng.jutils.log.loghelper.LogHelper.*;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.heng.jutils.jsonpath.JsonPath;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 可以在request范围内存放 一些东西的容器（可以层次化的）< br/>
 * 放在threadlocal之中，自动清除 < br/>
 * 用不同的key，作为隔离（default也算其中一个key） <br/>
 */
@WebFilter(value={"/*"})
public final class WebRequestContext implements Filter {
	
	private static final ThreadLocal<Map<String, Map<String, Object>>> ctx = new ThreadLocal<>();
	
	//这个要在第一层
	private static final String DEFAULT_KEY = CharMatcher.anyOf(".").replaceFrom(WebRequestContext.class.toString(), '_');
	
	
	
	public static Object getDefault(final String path){
		return get(DEFAULT_KEY, path);
	}
	
	public static void setDefault(final String path, final Object val){
		set(DEFAULT_KEY, path, val);
	}
	
	
	public static Map<String, Map<String, Object>> all(){
		return ctx.get();
	}
	
	
	
	
	public static Object get(final String key, final String path){
		if(Strings.isNullOrEmpty(path)){
			return null;
		}
		return JsonPath.getByPath(ensureContainer(key), path);
	}
	
	public static void set(final String key, final String path, final Object val){
		if(Strings.isNullOrEmpty(path)){
			throw new IllegalArgumentException("path不能为空");
		}
		JsonPath.setByPath(ensureContainer(key), path, val);
	}
	
	private static Map<String, Object> ensureContainer(final String key){
		Map<String, Object> container = ctx.get().get(key);
		if(container == null){
			container = Maps.newHashMap();
			ctx.get().put(key, container);
		}
		return container;
	}
	
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			ctx.set(Maps.newHashMap());
			chain.doFilter(request, response);
		}finally{ 
			ctx.remove();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
