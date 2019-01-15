package by.iba.sql.builder;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.iba.sql.database.DataBase;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;
import by.iba.sql.util.SqlConstatnt;

public class SqlBuilder {

	private StringBuilder sql;
	protected static DataBase dataBase;
	protected static Map<String, Object> parameters;
	protected static SqlPart sqlPart;
	protected List<Object> sqlList;

	// List<? extends SqlPart> parts

	SqlBuilder() {
		sql = new StringBuilder();
		sqlList = new ArrayList<Object>();
	}

	public SqlBuilder(Map<String, Object> parameters) {
		SqlBuilder.parameters = parameters;
		sql = new StringBuilder();
		sqlList = new ArrayList<Object>();
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuilder(sql);
		sqlPart = new SqlPart(sql);
		return new ExpressionBuilder(this);
	}

	public String getSql() {
		return sql != null ? sql.toString() : null;
	}

	public SqlBuilder limit(int limit) {

		sqlPart.getChild().add(
				new ExpressionChild(String.format(dataBase.getLimit(), limit)));

		return this;
	}

	public SqlBuilder orderBy(SqlConstatnt operator, String... fields) {

		if (fields == null || fields.length == 0) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		int size = fields.length - 1;
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" %s, ", fields[i]));
		}
		sb.append(fields[size] + " " + operator.getValue());
		sqlPart.getChild().add(new ExpressionChild(sb.toString()));
		
		return this;
	}

	public SqlBuilder type(Object database) {
		SettingDB setting = new SettingDB();
		if (database instanceof String) {
			setting.type((String) database);
		} else {
			setting.type((DataBase) database);
		}
		return this;
	}

	public String build() throws SQLSyntaxErrorException {
		
		sql = new StringBuilder(sqlPart.getHeaderPart());
		for (ExpressionChild e : sqlPart.getChild()) {
			if (e.getExpression() != null) {
				sql.append(e.getExpression()
						+ (e.getOperator() != null ? e.getOperator() : ""));
			}
		}
		return sql.toString();
	}
}