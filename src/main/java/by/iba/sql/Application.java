package by.iba.sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.builder.SqlBuilder;

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
							.addLike("name", "qwe", "nameparam", parameters)
							.and()
							.addLike("name", "qwe", "nameparam", parameters)
							.validate()
							.build();
//							.addCompare(SqlConstatnt.EQUALS, "password", "456", "pass", parameters)
//							.and()
//							.expression(filter1)
//								.expression(filter2)
//									.addLike("login", "123", "loginparam", parameters)
//									.and()
//									.addLike("name", "qwe", "nameparam", parameters)
//								.end()
//								.or()
//								.addLike("surname", "123", "surnameParam", parameters)
//								.or()
//								.in("login2", "lParam", parameters)
//								.and()
//								.in("login3", "l3Param", parameters, listIn)
//								.in("login4", "l3Param", parameters, listIn)
//								.and()
//								.in("login5", "l3Param", parameters, listIn)
//							.end()
//							.in("login", "login", parameters, (Object[]) null)
//							.and()
//							.in("login", "login", parameters, (Object[]) null)
//							.or()
//							.in("login", "login", parameters, listIn)
//							.or()
							
			System.out.println(sql);
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
