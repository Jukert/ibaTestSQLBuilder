package by.iba.sql.builder.impls;

import java.util.Map;

public class UpdateBuilder {

	private Map<String, Object> paramMap;
	private StringBuilder sql;

	public UpdateBuilder(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
		this.sql = new StringBuilder(sql);
	}

	public UpdateBuilder sql(String sql) {
		this.sql = new StringBuilder(sql);
		return this;
	}
	
	public UpdateBuilder insert(String column, Object value) {
		paramMap.put(column, value);
		return this;
	}
	
	public String build() {
		
		int i = 0;
		int size = paramMap.size() - 1;
		sql.append("(");
		for (String column: paramMap.keySet()) {
			sql.append(String.format(" :%s%s", column, (size > i) ? "," : ""));
			i++;
		}
		sql.append(")");
		return sql.toString();
	}
		
}
