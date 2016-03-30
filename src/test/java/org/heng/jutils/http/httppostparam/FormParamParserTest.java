package org.heng.jutils.http.httppostparam;

import java.util.Map;
import static org.junit.Assert.*;
import org.heng.jutils.jsonpath.JsonPath;
import org.junit.Test;


public class FormParamParserTest {

	@Test
	public final void test() {
//		fail("Not yet implemented"); // TODO
		Map<String, Object> m = FormParamParser.parse("777&zh.name=hahahhah&zh.id=998&zh.qq=8216006&jjww=&   	orderby=1&myname=东方& show =title%2Cbname&myorder=1&keyboard=abccc&button=%CB%D1%CB%F7%C2%FE%BB%AD");
		System.out.println(m);
		assertEquals("8216006", JsonPath.getByPath(m, ".zh.qq"));
	}

}
