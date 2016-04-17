package org.heng.jutils.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.Test;

public class JSParserTest {

	@Test
	public final void test() {
//		fail("Not yet implemented"); // TODO
		Map m = JSParser.parse("settings");
		System.out.println(m);
//		assertEquals(JSParser.get(".working_directory.windows.command"), "%tmp%");
//		assertEquals(JSParser.get(".working_directory.windows.command"), "%tmp%");
	}

	
	
//	@Test
	public void t2() throws FileNotFoundException, IOException{
		Properties ps = new Properties();
		ps.load(JSParser.class.getClassLoader().getResource("settings.properties").openStream());
		
		String path = ps.getProperty("settings");
		System.out.println(path);
	}
}
