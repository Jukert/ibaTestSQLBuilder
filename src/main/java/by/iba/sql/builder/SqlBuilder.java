package by.iba.sql.builder;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.common.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class SqlBuilder {

	private StringBuffer sql;
	private SqlBuilder sqlBuilder;
	protected List<Object> expressionsList;

	public SqlBuilder() {
		sql = new StringBuffer();
		expressionsList = new ArrayList<Object>();
	}

	public SqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuffer(sql);
		return new ExpressionBuilder(this);
	}

	public String getSql() {
		return sql != null ? sql.toString() : null;
	}

	public SqlBuilder addLimit(int limit) {

		expressionsList.add(String.format(" FETCH FIRST %d ROWS ONLY ", limit));

		return this;
	}

	public SqlBuilder addOrder(SqlConstatnt operator, String... fields) {

		if (fields == null || fields.length == 0) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		int size = fields.length - 1;
		expressionsList.add(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			expressionsList.add(String.format(" %s, ", fields[i]));
		}
		expressionsList.add(fields[size] + " " + operator.getValue());

		return this;
	}
	
	public SqlBuilder validate() throws OperationNotSupportedException,
			SQLSyntaxErrorException {

		for (int i = expressionsList.size() - 1; i > 0; i--) {
			Object el = expressionsList.get(i) != null ? expressionsList.get(i)
					: "";
			if (!(el.equals(SqlConstatnt.AND.getValue()) || el
					.equals(SqlConstatnt.OR.getValue()))) {
				continue;
			}

			if (i == 0 || i == expressionsList.size() - 1) {
				throw new SQLSyntaxErrorException(
						"Operator couldn't be first and last element");
			}
			boolean removeCondition = false;
			if (StringUtil.isEmpty((String) expressionsList.get(i + 1))) {
				expressionsList.remove(i + 1);
				expressionsList.remove(i);
				removeCondition = true;
			}
			if (StringUtil.isEmpty((String) expressionsList.get(i - 1))) {
				if (!removeCondition)
					expressionsList.remove(i);
				expressionsList.remove(i - 1);
			}
		}
		return this;
	}

	public String build() {

		for (Object object : expressionsList) {
			sql.append(object);
		}

		return sql.toString();
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}
}