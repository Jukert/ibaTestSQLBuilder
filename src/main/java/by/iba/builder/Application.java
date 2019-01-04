package by.iba.builder;

import java.util.HashMap;
import java.util.Map;

import by.iba.builder.common.SqlBuilder;
import by.iba.builder.common.SqlConstatnt;

public class Application {
	
	public static void main(String[] args) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = new SqlBuilder()
						.sql("SELECT * FROM USERS WHERE ")
						.addCompare(SqlConstatnt.EQUALS, "password", "456", parameters)
						.and()
						/*.expression(filter.isEmpty())
							.expression(true)
						.end()*/
						.begin()
							.begin()
								.addLike("login", "asd", parameters)
								.and()
								.addLike(" name", "qwe", parameters)
							.end()
							.or()
							.addLike("surname", "123", parameters)
							.operator(SqlConstatnt.OR)
							.in("login2", parameters, "qwe", "asd", "zxc")//object
						.end()
						/*.orderBy()

						.end()*/
						.addOrder(SqlConstatnt.ASC, "login", "name")
						.addLimit(10)
						.build();
		//begin && end -> expression && end 
		System.out.println(sql);
		System.out.println();
		System.out.println(parameters.toString());
	}
}
