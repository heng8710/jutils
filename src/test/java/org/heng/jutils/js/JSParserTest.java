package org.heng.jutils.js;

import java.util.Map;
import static org.junit.Assert.*;

import org.junit.Test;

public class JSParserTest {

	@Test
	public final void test() {
//		fail("Not yet implemented"); // TODO
		Map m = JSParser.parse("settings.js");
		System.out.println(m);
		assertEquals(JSParser.get("settings.js", ".working_directory.windows.command"), "%tmp%");
	}

}
