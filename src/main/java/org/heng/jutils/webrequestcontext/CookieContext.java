package org.heng.jutils.webrequestcontext;


import static org.heng.jutils.log.loghelper.LogHelper.error;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.heng.jutils.jsonpath.JsonPath;

/**
 * 与{@link QueryParamContext}是一样的。 <br/>
 * 获取所有cookie key/value <br/>
 * 这里只取k/v就好了，对于cookie中的其他成份，如果真的需要，自己去取就是了。 <br/ >
 */
@WebFilter(urlPatterns={"/*"})
public class CookieContext implements Filter{
	
	
	private static final ThreadLocal<Map<String, Object>> ctx = new ThreadLocal<>();
	
	
	/**
	 * @param path : 类似这样【.page.num】
	 * @return
	 */
	public static Object get(final String path){
		return JsonPath.getByPath(ctx.get(), path);
	}
	
	
	/**
	 * @param path : 类似这样【.page.num】
	 * @param val
	 */
	public static void set(final String path, final Object val){
		JsonPath.setByPath(ctx.get(), path, val);
	}
	
	
	/**
	 * 校验用途，不要改。
	 * @return
	 */
	public static Map<String, Object> all( ){
		return ctx.get();
	}
	
	


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
				final HttpServletRequest req = (HttpServletRequest)request;
				final HttpServletResponse resp = (HttpServletResponse)response;
				try {
					final Cookie[] cookies = req.getCookies();
					//这里可能会为null
					if(cookies != null){
						//ctx初始化
						//这之后，get、set都安全
						ctx.set(new HashMap<>());
						for(final Cookie cookie: req.getCookies()){
							try {
//								System.out.println(":"+ pname + "---" + req.getParameter(pname));
								set("." + cookie.getName(), cookie.getValue());
							} catch (Exception e) {
								error(String.format("cookie: name=[%s], value=[%s]的类型与已经解析的参数=[%s]类型不匹配", cookie.getName(), cookie.getValue(), all()));
							}
						}
					}
				} catch (final Exception e) {
					error(e);
				}
			}
			chain.doFilter(request, response);
		} catch (final Exception e) {
			error(e);
		} finally{
			ctx.remove();
		}
	}


	@Override
	public void destroy() {
		
	}
}
