package by.iba.sql.builder;

import java.util.List;
import java.util.UUID;

import by.iba.sql.common.SqlConstatnt;

public class SqlBuilder {

	private StringBuffer sql;
	private List<Object> expressionsList;
	
	public SqlBuilder() {

	}

	public SqlBuilder(List<Object> list) {
		this.expressionsList = list;
	}
	
	public SqlBuilder sql(String sql) {
		this.sql = new StringBuffer(sql);
		return this;
	}

	public SqlBuilder addOrder(SqlConstatnt operator, String... fields) {
		int size = fields.length - 1;
		sql.append(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			sql.append(String.format(" %s, ", fields[i]));
		}
		sql.append(String.format(" %s %s ", fields[size], operator.getValue()));
		return this;
	}

	public SqlBuilder addLimit(int limit) {
		sql.append(String.format(" FETCH FIRST %d ROWS ONLY ", limit));
		return this;
	}

	public String build() {
		for (Object object : expressionsList) {
			sql.append(object.toString());
		}
		return sql.toString();
	}

	protected String getUUID() {
		return UUID.randomUUID().toString();
	}
}