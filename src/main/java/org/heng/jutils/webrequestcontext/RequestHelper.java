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
	
	
	public static String httpMeta(){
		final HttpServletRequest hreq = req.get();
		final HttpServletResponse hresp = resp.get();
		
		final StringBuilder buff = new StringBuilder().append("http request meta: ******************************************\n");
		
		buff.append(String.format("method: [%s]\n", hreq.getMethod()));
		buff.append(String.format("scheme: [%s]\n", hreq.getScheme()));
		
		buff.append(String.format("query string: [%s]\n", hreq.getQueryString()));
		
		buff.append(String.format("request uri: [%s]\n", hreq.getRequestURI()));
		buff.append(String.format("context path: [%s]\n", hreq.getContextPath()));
		
		
		buff.append(String.format("associated servlet path: [%s]\n", hreq.getPathInfo()));
		
		
		buff.append("query params: \n------------------------------------\n");
		for(Enumeration<String> it= hreq.getParameterNames();it.hasMoreElements();){
			final String key = it.nextElement();
			buff.append(String.format("%s=%s\n", hreq.getParameter(key)));
		}
		buff.append("------------------------------------\n");
		
		buff.append("headers: \n------------------------------------\n");
		for(Enumeration<String> it= hreq.getHeaderNames();it.hasMoreElements();){
			final String key = it.nextElement();
			buff.append(String.format("%s=%s\n", hreq.getHeader(key)));
		}
		buff.append("------------------------------------\n");
		
		buff.append(String.format("remote address: [%s]\n", hreq.getRemoteAddr()));
		buff.append(String.format("remote host: [%s]\n", hreq.getRemoteHost()));
		buff.append(String.format("remote port: [%s]\n", hreq.getRemotePort()));
		
		buff.append("******************************************\n");
		
		buff.append("http response meta: ******************************************\n");
		
		
		if(hresp.isCommitted()){
			buff.append(String.format("status: [%s]\n", hresp.getStatus()));
		}
		buff.append(String.format("content-type: [%s]\n", hresp.getContentType()));
		buff.append(String.format("encoding: [%s]\n", hresp.getCharacterEncoding()));
		buff.append("headers: \n------------------------------------\n");
		for(final String name: hresp.getHeaderNames()){
			buff.append(String.format("%s=%s\n", hreq.getParameter(name)));
		}
		buff.append("------------------------------------\n");
		buff.append("******************************************\n");
		
		return buff.toString();
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
//			System.out.println("----------headers:");
//			for(Enumeration<String> it= req.get().getHeaderNames();it.hasMoreElements();){
//				final String header = it.nextElement();
//				System.out.println(String.format("%s:%s", header, req.get().getHeader(header)));
//			}
//			System.out.println("----------headers:end-------------------------");
//			System.out.println("remote:"+req.get().getRemoteAddr() +":"+req.get().getRemotePort());
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
