package org.heng.jutils.jsonpath;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.heng.jutils.jsonpath.JsonPath;
import org.junit.Test;

public class JsonPathTest {

	@Test
	public final void test001() {
//		fail("Not yet implemented"); // TODO
		Map m = new HashMap();
		JsonPath.setByPath(m, "7796", ".a.b.c.d[3][1].e[2].f[0]");
		System.out.println(m);
		assertEquals(JsonPath.getByPath(m, ".a.b.c.d[3][1].e[2].f[0]"), "7796");
		System.out.println(JsonPath.getByPath(m, ".a.b.c.d[3][1].e[2]"));
	}

//	@Test
//	public final void testSetByPath0() {
//		fail("Not yet implemented"); // TODO
//	}

}
