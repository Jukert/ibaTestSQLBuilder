package by.iba.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

import by.iba.sql.builder.SqlBuilder;

public class Application {
	
	public static void main(String[] args) throws SQLSyntaxErrorException {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = true;
		
		
		String sql;
		try {
			sql = new SqlBuilder(parameters)
							.type("db2")
							.sql("SELECT * FROM USERS WHERE ")
							.expression(true)
								.like()
									.expression(filter2)
									.column("qwe")
									.value("asd")
									.name("zxc")
								.end()
								.and()
								.expression(true)
									.compare()
										.column("aaa")
										.name("compare")
										.value("asda")
									.end()
									.and()
									.in()
										.column("in")
										.values((Object) null)
										.name("testIN1")
									.end()
								.end()
							.end()
							.and()
							.in()
								.column("in")
								.values("asd")
								.values("qwe")
								.name("testIN2")
							.end()
							.and()
							.between()
								.column("Between")
								.firstValue(null)
								.secondValue("second")
								.name("btw")
							.end()
							.limit(5)
							.build();
			System.out.println(sql);
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
		System.out.println(parameters.toString());
	}
}