package by.iba.sql.builder.impls;

import java.sql.SQLSyntaxErrorException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import by.iba.sql.database.DataBase;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;
import by.iba.sql.util.Lists;
import by.iba.sql.util.SqlConstatnt;

public class SqlBuilder {

	private StringBuilder sql;
	protected static DataBase dataBase;
	protected static Map<String, Object> parameters;
	protected static SqlPart sqlPart;

	// List<? extends SqlPart> parts

	SqlBuilder() {
		sql = new StringBuilder();
	}

	public SqlBuilder(Map<String, Object> parameters) {
		SqlBuilder.parameters = parameters;
		sql = new StringBuilder();
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuilder(sql);
		sqlPart = new SqlPart(sql);
		return new ExpressionBuilder();
	}

	public String getSql() {
		return sql != null ? sql.toString() : null;
	}

	public SqlBuilder limit(int limit) {

		sqlPart.getAdditionalPart().setLimit(
				String.format(dataBase.getLimit(), limit));
		;

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
		sqlPart.getAdditionalPart().setOrder(sb.toString());
		;

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

		String header = sqlPart.getHeaderPart();
		String limit = sqlPart.getAdditionalPart().getLimit();
		String order = sqlPart.getAdditionalPart().getOrder();
		String additional = (limit != null ? limit : "")
				+ (order != null ? order : "");

		StringBuilder sb = new StringBuilder(header != null ? header : "");
		
		List<ExpressionChild> list = SqlBuilder.valid(sqlPart
				.getExpressionChilds());

		for (ExpressionChild ex : list) {
			sb.append(ex.getExpression()
					+ (ex.getOperator() != null ? ex.getOperator() : ""));
		}

		sb.append(additional);
		return sb.toString();
	}

	public static List<ExpressionChild> valid(List<ExpressionChild> expressionChilds) {

		Iterator<ExpressionChild> itr = expressionChilds.iterator();
		
		while(itr.hasNext()) {
			ExpressionChild ex = itr.next();
			if (ex.getExpression() == null) {
				itr.remove();
			}
		}
		
		if (expressionChilds.size() > 0)
			Lists.getLast(expressionChilds).setOperator(null);

		return expressionChilds;
	}
}