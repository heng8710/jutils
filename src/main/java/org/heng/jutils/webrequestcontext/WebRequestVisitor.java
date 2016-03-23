package org.heng.jutils.webrequestcontext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebRequestVisitor {
	
	/**
	 * 从(request, response)中获取数据放到context之中
	 * @param req
	 * @param resp
	 * @param context
	 */
	public void visit(final HttpServletRequest req, final HttpServletResponse resp, final Map<String, Object> context);
	
}
