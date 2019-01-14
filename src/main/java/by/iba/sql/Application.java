package by.iba.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.iba.sql.builder.SqlBuilder;
import by.iba.sql.common.ExpressionFilter;
import by.iba.sql.common.UserFiler;

public class Application {
	
	private static UserFiler user = new UserFiler(1L, "Pablo", "Escobaro", 25L);
	private static String select = "SELECT * FROM user WHERE ";
	private static List<Object[]> ob = Arrays
			.asList(new Object[][] {
					{new ExpressionFilter(true, false),
					"SELECT * FROM user WHERE (id > :id) AND surname LIKE '%'||:surname||'%'"},
					{new ExpressionFilter(true, true),
					"SELECT * FROM user WHERE ((age > :age AND name LIKE '%'||:name||'%') OR id > :id) AND surname LIKE '%'||:surname||'%'"},
					{new ExpressionFilter(false, false),
					"SELECT * FROM user WHERE surname LIKE '%'||:surname||'%'"},
					{new ExpressionFilter(false, true),
					"SELECT * FROM user WHERE surname LIKE '%'||:surname||'%'"},
			});
	
	public static void main(String[] args) throws SQLSyntaxErrorException {
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = false;
		
		
		String sql;
		try {
			sql = new SqlBuilder(parameters)
							.sql("SELECT * FROM USERS WHERE ")
//							
							.like()
								.expression(filter2)
								.column("qwe")
								.value("asd")
								.name("zxc")
							.end()
							.build();
			System.out.println(sql);
		} catch (SQLSyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(parameters.toString());
	}
}