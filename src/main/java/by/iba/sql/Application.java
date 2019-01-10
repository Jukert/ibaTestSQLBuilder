package by.iba.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.builder.SqlBuilder;
import by.iba.sql.common.SqlConstatnt;

public class Application {
	
	public static void main(String[] args) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = true;
		
		List<String> listIn = Arrays.asList("val1", "val2", "val3");		
		String sql;
		try {
			sql = new SqlBuilder()
							.sql("SELECT * FROM USERS WHERE ")
							.addCompare(SqlConstatnt.EQUALS, "password", "456", "pass", parameters)
							.and()
							.expression(filter1)
								.expression(filter2)
									.addLike("login", "123", "loginparam", parameters)
									.and()
									.addLike("name", "qwe", "nameparam", parameters)
								.end()
								.or()
								.addLike("surname", "123", "surnameParam", parameters)
								.end()
								.or()
								.in("login2", "lParam", parameters, "AAA", "QQQ")
								.addLimit(3)
							.validate()
							.build();
			System.out.println(sql);
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(parameters.toString());
	}
}
