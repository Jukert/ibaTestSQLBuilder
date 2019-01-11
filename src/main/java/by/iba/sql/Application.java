package by.iba.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.iba.sql.builder.SqlBuilder;
import by.iba.sql.util.SqlConstatnt;

public class Application {
	
	public static void main(String[] args) {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = true;
		
		List<String> listIn = Arrays.asList("val1", "val2", "val3");		
		String sql;
		try {
			sql = new SqlBuilder(parameters)
							.sql("SELECT * FROM USERS WHERE ")
							.compare(SqlConstatnt.EQUALS, "password", "456", "asd")
							.and()
							.expression(filter1)
								.expression(filter2)
									.like("field", null, "loginparam")
									.and()
									.like("name", "qwe", "nameparam")
								.end()
								.or()
								.like("surname", "123", "surnameParam")
							.end()
							.or()
							.in("login2", "lParam", "AAA", "QQQ")
							.limit(3)
							.build();
			System.out.println(sql);
		} catch (SQLSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(parameters.toString());
	}
}
