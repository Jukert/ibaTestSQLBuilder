package by.iba.builder.common;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SqlBuilder {
	
	private String sql;
	private String valueId;
	
	public SqlBuilder sql(String sql) {
		this.sql = sql;
		return this;
	}
	
	public SqlBuilder addLike(String field, String value, Map<String, Object> parameters) {
		valueId = getUUID();
		sql += field + " LIKE '%'||:" + valueId + "||'%' ";
		parameters.put(valueId, value);
		return this;
	}
	
	public SqlBuilder addLike(String field, String value, String paramName, Map<String, Object> parameters) {
		valueId = getUUID();
		sql += field + " LIKE '%'||:" + valueId + "||'%' ";
		parameters.put(paramName, value);
		return this;
	}
	
	public SqlBuilder operator(SqlConstatnt operator){
		sql += operator.getValue();
		return this;
	}
	
	public SqlBuilder expression(boolean condition){
			sql += " ( ";
		return this;
	}
	
	public SqlBuilder end(){
		sql += " ) ";
		return this;
	}
	
	public SqlBuilder addCompare(SqlConstatnt operator, String field, String value, Map<String, Object> parameters){
		valueId = getUUID();
		sql += field + operator.getValue() + " :" + valueId;
		parameters.put(valueId, value);
		return this;
	}
	
	public SqlBuilder addOrder(SqlConstatnt operator, String...fields){
		int size  = fields.length-1;
		sql += " ORDER BY ";
		for (int i = 0; i < size; i++) {
			sql += fields[i] + ", ";
		}
		sql += fields[size] + " " + operator.getValue();
		return this;
	}
	
	public SqlBuilder in(String field, Map<String, Object> parameters, Object...values){
		valueId = getUUID();
		sql += field + " IN( ";
		int size = values.length-1;
		for (int i = 0; i < size; i++) {
			sql += " :" + valueId + ", "; 
			parameters.put(valueId, values[i]);
			valueId = getUUID();
		}
		sql += " :" + valueId + " ) ";
		parameters.put(valueId, values[size]);
		return this;
	}
	
	public SqlBuilder in(String field, Map<String, Object> parameters, List<Object> values){
		valueId = getUUID();
		sql += field + " IN( ";
		int size = values.size()-1;
		for (int i = 0; i < size; i++) {
			sql += " :" + valueId + ", "; 
			parameters.put(valueId, values.get(i));
			valueId = getUUID();
		}
		sql += " :" + valueId + " ) ";
		parameters.put(valueId, values.get(size));
		return this;
	}
	
	public SqlBuilder addLimit(int limit){
		sql += " FETCH FIRST " + limit + " ROWS ONLY ";
		return this;
	}
	
	public SqlBuilder and(){
		sql += SqlConstatnt.AND;
		return this;
	}
	
	public SqlBuilder or(){
		sql += SqlConstatnt.OR;
		return this;
	}
	
	public String build() {
		return sql;
	}
	
	private String getUUID() {
		return UUID.randomUUID().toString();
	}
}