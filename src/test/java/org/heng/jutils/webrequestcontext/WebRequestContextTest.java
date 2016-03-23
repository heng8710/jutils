package org.heng.jutils.webrequestcontext;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.junit.Test;

public class WebRequestContextTest {

	@Test
	public final void test() {
//		fail("Not yet implemented"); // TODO
		
		HttpServletRequest req = EasyMock.createMock(HttpServletRequest.class);
		EasyMock.expect(req.)
		WebRequestContext wrc = EasyMock.createMock(WebRequestContext.class);
		wrc.doFilter(req, response, chain);
	}

}
