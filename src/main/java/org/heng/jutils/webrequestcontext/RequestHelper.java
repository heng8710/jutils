package org.heng.jutils.webrequestcontext;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;


/**
 * 把ThreadLocal加进来，在request完全处理完之前，会自动清理掉< br/>
 */
@WebFilter(urlPatterns={"/*"})
public final class RequestHelper implements Filter {
	
	private static final ThreadLocal<HttpServletRequest> req = new ThreadLocal<>();
	private static final ThreadLocal<HttpServletResponse> resp = new ThreadLocal<>();
	
	/**
	 * 把值添加 到request域（request.setAttribute(className, val)）< br/>
	 * @param val
	 */
	public static void attribute(final Object val){
		if(val != null){
			req.get().setAttribute(simpleClassName(val.getClass()), val);	
		}
	}
	
	private static String simpleClassName(final Class<?> clazz){
		final String name0 = clazz.getSimpleName();
		if(Strings.isNullOrEmpty(name0)){
//			throw new IllegalArgumentException("匿名内部类");
			return simpleClassName(clazz.getSuperclass());
		}else{
			 return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name0);
		}
	}
	
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
				req.set((HttpServletRequest)request);
				resp.set((HttpServletResponse)response);
			}	
			System.out.println("----------headers:");
			for(Enumeration<String> it= req.get().getHeaderNames();it.hasMoreElements();){
				final String header = it.nextElement();
				System.out.println(String.format("%s:%s", header, req.get().getHeader(header)));
			}
			System.out.println("----------headers:end-------------------------");
			System.out.println("remote:"+req.get().getRemoteAddr() +":"+req.get().getRemotePort());
			chain.doFilter(request, response);
		}finally{
			req.remove();
			resp.remove();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
