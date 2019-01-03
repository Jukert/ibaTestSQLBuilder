package by.iba.builder.common;

import java.util.Map;

public class SqlBuilder {
	
	private String sql;
	private static final String AND = " AND ";
	private static final String OR = " OR ";
	
	public SqlBuilder sql(String sql) {
		this.sql = sql;
		return this;
	}
	
	public SqlBuilder addLike(String field, String value, Map<String, Object> parameters) {
		sql += " ? LIKE '%'||?||'%' ";
		parameters.put(field, value);
		return this;
	}
	
	
	public SqlBuilder addWhere(String field, String value, Map<String, Object> parameters){
		sql +="";
		return this;
		}
	
	public SqlBuilder and(){
		sql += AND;
		return this;
	}
	
	public SqlBuilder or(){
		sql += OR;
		return this;
	}
	
	public String build() {
		return sql;
	}
	//SELECT * FROM USERS WHERE password = "456" AND ( (login LIKE '%asd%' AND name LIKE '%qwe%') OR surname LIKE "%123%" ) 
}
