package by.iba.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.iba.sql.builder.ExpressionBuilder;
import by.iba.sql.builder.SqlBuilder;
import by.iba.sql.common.SqlConstatnt;

public class Application {
	
	public static void main(String[] args) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		boolean filter1 = true;
		boolean filter2 = true;
		
		List<Object> listIn = new ArrayList<Object>();
		listIn.add("first");
		listIn.add("second");
		listIn.add("third");
		
		//listSQL
		//SELECT * FROM USERS WHERE
		//operator - null, addCompare
		//operator - and , expression - >
								// expression ->
									//null, addLiked
									//and, addLike
								//end
							//end
		
		//select 								//select
		//null	    	-->validate()			//expression
	//and										//expression	
		//expression							//addlike
		//expression
		//null
	//or
		//addlike
		
		//select * from users where ((user like %Artem%))
		
		
		/*
		String sql = new SqlBuilder()
						.sql("SELECT * FROM USERS WHERE ")
						.addCompare(SqlConstatnt.EQUALS, "password", "456", parameters)
						.and()
						.expression(filter1)
							.expression(filter2)
								.sql("ID != 2 AND ID != 3")
								.addLike("login", "123", parameters)
								.and()
								.addLike("name", "qwe", parameters)
							.end()
							.or(filter2)
							.addLike("surname", "123", parameters)
							.or()
							.in("login2", parameters, "qwe", "asd", "zxc")
							.and()
							.in("login3", parameters, listIn)
						.end()
						.addOrder(SqlConstatnt.ASC, "as","zx")
						.addLimit(10)
						.build();*/
		
		List<Object> list = new ExpressionBuilder()
									.addLike("name", "qwe", parameters)
									.or()
									.in("login2", parameters, "qwe", "asd", "zxc")
									.getList();
		
		for (Object object : list) {
			System.out.println(object.toString());
		}
	}
}
