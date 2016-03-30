package org.heng.jutils.webrequestcontext;


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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.heng.jutils.jsonpath.JsonPath;


import static org.heng.jutils.log.loghelper.LogHelper.*;

/**
 * 获取所有query param <br/>
 * 比如说【?a=1&b=2&c=3&d=4&e=5&f=6&h.x=zheng&j.x.y=heng&j.q[0]=777&k.x.z[2]=888#998】就会生成：{a=1, b=2, c=3, d=4, e=5, f=6, h={x=zheng}, j={q=[777], x={y=heng}}, k={x={z=[null, null, 888]}}} <br/>
 * ？？？一般query param更容易放置常用的元数据？ <br/>
 * 适合所有动词，因为只是取uri上的query param而已，从http来讲，只是取了header而已，不会影响body（对应流）的。<br/>
 * api主要是在结构化和扁平之间平衡一下 <br/ >
 * 可能并不适用于"MultiValue"的情况 <br/>
 * 值很可能都String化了 ，要数值格式自己去格式化吧。<br/>
 */
@WebFilter(value={"/*"})
public class QueryParamContext implements Filter{
	
	
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
					//ctx初始化
					//这之后，get、set都安全
					ctx.set(new HashMap<>());
					//下面不会有空指针 
					for(final String pname: req.getParameterMap().keySet()){
						try {
//							System.out.println(":"+ pname + "---" + req.getParameter(pname));
							set("." + pname, req.getParameter(pname));
						} catch (Exception e) {
							error(String.format("query param: name=[%s], value=[%s]的类型与已经解析的参数=[%s]类型不匹配", pname,req.getParameter(pname), all()));
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
