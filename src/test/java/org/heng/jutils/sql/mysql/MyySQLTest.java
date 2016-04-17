package org.heng.jutils.sql.mysql;

import static org.junit.Assert.*;

import java.io.IOException;

import org.heng.jutils.db.sql.mysql.MyySQL;
import org.junit.Test;

public class MyySQLTest {

	@Test
	public final void test() throws IOException {
//		fail("Not yet implemented"); // TODO
		MyySQL sql = new MyySQL();
		
		sql.SELECT("*").FROM("mytable").WHERE("1=1");
		sql.ORDER_BY("updateTime desc");
		sql.LIMIT("77888832,998888");
		final Appendable a = sql.usingAppender(new StringBuilder()).append(" limit ");
//		if(offset != null){
			a.append("0,");
//		}
		
		a.append("998");
		
		System.out.println(a);
	}

}
