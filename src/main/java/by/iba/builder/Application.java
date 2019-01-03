package by.iba.builder;

import java.util.HashMap;
import java.util.Map;

import by.iba.builder.common.SqlBuilder;

public class Application {
	
	public static void main(String[] args){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
//SELECT * FROM USERS WHERE password = "456" AND ( (login LIKE '%asd%' AND name LIKE '%qwe%') OR surname LIKE "%123%" )
		String sql = new SqlBuilder()
						.sql("SELECT * FROM USERS ")
						.addWhere("password", "456", parameters)
						.and()
						.addLike("login", "asd", parameters)
						.and()
						.addLike(" name", "qwe", parameters)
						.or()
						.addLike("surname", "123", parameters)
						.build();
		//expresions ()()()()
		//IN
		//operator and or
		//like, =, >, <
		//named (:parametrs)
		//ORDER BY
		//Limit (DB2)
		System.out.println(sql);
	}
}
