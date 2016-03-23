package org.heng.jutils.webrequestcontext;

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

import com.google.common.collect.Lists;


/**
 * 最初始目的是用来从query params中获取分页参数。
 * 留了扩充的步骤：从(request, response)中获取数据放到context之中。
 */
@WebFilter(value={"/*"}/*, initParams={@WebInitParam(name="gacl",value="孤傲苍狼")}*/)
public final class WebRequestContext implements Filter {
	
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
		if(ctx.get() == null){
			ctx.set(new HashMap<>());
		}
		JsonPath.setByPath(ctx, val, path);
	}
	
	
	
	/**
	 * 这里放置填充context的动作逻辑，可以放多个。
	 * 比如说下面这个，是一个用来从query param中获取分页参数的逻辑（page.xx）
	 * 同时有request, response这2个参数，能获取的应该是足够了。
	 */
	private static final List<WebRequestVisitor> visitors = Lists.newArrayList((req, resp, context) -> {
		for(final String pname: req.getParameterMap().keySet()){
			if(pname != null && pname.startsWith("page.")){
				JsonPath.setByPath(context, req.getParameter(pname), "."+pname);
			}
		}
	});

//	public RequestContext(final List<RequestVisitor> visitors) {
//		// TODO Auto-generated constructor stub
//		this.visitors = visitors;
//	}
//
//	public RequestContext(final RequestVisitor visitor) {
//		// TODO Auto-generated constructor stub
//		this.visitors = Lists.newArrayList(visitor);
//	}
//
//	public RequestContext() {
//		// TODO Auto-generated constructor stub
//		this.visitors = Lists.newArrayList();
//	}
	
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
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		
		try {
			if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
				final HttpServletRequest req = (HttpServletRequest)request;
				final HttpServletResponse resp = (HttpServletResponse)response;
				ctx.set(new HashMap<>());
				if(visitors != null){
					for(final WebRequestVisitor visitor: visitors){
						try {
							visitor.visit(req, resp, ctx.get());
						} catch (final Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			chain.doFilter(request, response);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ctx.remove();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
