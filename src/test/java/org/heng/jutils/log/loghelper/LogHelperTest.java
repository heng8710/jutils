package org.heng.jutils.log.loghelper;

import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.base.Strings;

public class LogHelperTest {

	@Test
	public final void test() {
//		fail("Not yet implemented"); // TODO
		System.out.println(LogHelper.log());
		System.out.println("-------------");
//		TraceLog.logRich();
		System.out.println(LogHelper.log("ye wu msg"));
		System.out.println("-------------");
		LogHelper.info("hello info");
		System.out.println("-------------");
		System.out.println("-------------");
		System.out.println("-------------");
		new Object(){{
			System.out.println(LogHelper.log());
			System.out.println("-------------");
//			TraceLog.logRich();
			System.out.println(LogHelper.log(Strings.repeat("ye wu msg", 10)));
			System.out.println("-------------");
			LogHelper.info("hello info");
			System.out.println("-------------");
			LogHelper.error("hello error", new RuntimeException());
			System.out.println("-------------");
			System.out.println("-------------");
			
		}
		
			@Override
				public String toString() {
					Consumer<String> c = s -> {
						LogHelper.error("hello err", new RuntimeException("toString errrrrrrrrr"));
					};
					
					c.accept("aa");
					return super.toString();
				}
		}.toString();
	}

}
