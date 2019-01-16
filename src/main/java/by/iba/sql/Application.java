package by.iba.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

import by.iba.sql.builder.impls.SqlBuilder;
import by.iba.sql.util.SqlConstatnt;

public class Application {
	
	public static void main(String[] args) throws SQLSyntaxErrorException {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = true;
		
		
		String sql;
		try {
			sql = new SqlBuilder(parameters)
//							.type("db2")
//							.sql("SELECT * FROM USERS WHERE ")
//							.like()
//								.expression(filter2)
//								.column("qwe1")
//								.value("asd1")
//								.name("zxc1")
//							.end()
//							.and()
//							.expression(true)
//								.like()
//									.expression(filter2)
//									.column("qwe")
//									.value("asd")
//									.name("zxc")
//								.end()
//								.and()
//								.expression(true)
//									.compare()
//										.column("aaa")
//										.name("compare")
//										.value("asda")
//									.end()
//									.and()
//									.in()
//										.column("in")
//										.values("valll")
//										.name("testIN1")
//									.end()
//								.end()
//							.end()
//							.and()
//							.in()
//								.column("in")
//								.name("testIN2")
//							.end()
//							.and()
//							.between()
//								.column("Between")
//
//								.name("btw")
//							.end()
//							.limit(5)
//							.build();
			.type("db2")
			.sql("")
			.compare()
				.column("id")
				.name("id")
				.value(null)
				.operator(SqlConstatnt.MORE)
			.end()
			.and()
			.like()
					.column("name")
			.end()
			.or()
			.in()
				.column("surname")
				.name(null)
			.end()
			.and()
			.compare()
				.column("age")
				.name("age")
				.value(null)
				.operator(SqlConstatnt.MORE)
			.end()
			.build();
			System.out.println(sql);
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
		System.out.println(parameters.toString());
	}
}