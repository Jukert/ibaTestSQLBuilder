package by.iba.sql.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.iba.sql.common.SqlConstatnt;

public class ExpressionBuilder extends SqlBuilder {

	private StringBuffer sql;
	protected List<Object> expressionsList;
	private OperatorBuilder operatorBuilder;

	public ExpressionBuilder() {
		expressionsList = new ArrayList<Object>();
		operatorBuilder = new OperatorBuilder(this);
	}

	public OperatorBuilder addLike(String field, String value,
			Map<String, Object> parameters) {
		String valueId = getUUID();
		expressionsList.add(" " + field + "  LIKE '%'||:" + valueId + "||'%' ");
		parameters.put(valueId, value);
		return operatorBuilder;
	}

	public OperatorBuilder addLike(String field, String value,
			String paramName, Map<String, Object> parameters) {
		String valueId = getUUID();
		expressionsList.add(" " + field + "  LIKE '%'||:" + valueId + "||'%' ");
		parameters.put(paramName, value);
		return operatorBuilder;
	}

	/**
 * 
 */
	public ExpressionBuilder expression(boolean condition) {
		/*
		 * if (!condition) { SqlBuilder sb = new SqlBuilder(this); return sb; }
		 */
		sql.append(" (");
		return this;
	}

	public ExpressionBuilder end() {
		/*
		 * if (sqlBuilder != null) { sql = new StringBuffer(sqlBuilder.build());
		 * sqlBuilder = null; } else { sql.append(") "); }
		 */
		return this;
	}

	public OperatorBuilder addCompare(SqlConstatnt operator, String field,
			String value, Map<String, Object> parameters) {
		String valueId = getUUID();
		expressionsList.add(String.format(" %s %s :%s ", field,
				operator.getValue(), valueId));
		parameters.put(valueId, value);
		return operatorBuilder;
	}

	public OperatorBuilder in(String field, Map<String, Object> parameters,
			Object... values) {
		String valueId = getUUID();
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.length - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", valueId));
			parameters.put(valueId, values[i]);
			valueId = getUUID();
		}
		sb.append(String.format(" :%s ) ", valueId));
		expressionsList.add(sb.toString());
		parameters.put(valueId, values[size]);
		return operatorBuilder;
	}

	public OperatorBuilder in(String field, Map<String, Object> parameters,
			List<Object> values) {
		String valueId = getUUID();
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.size() - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", valueId));
			parameters.put(valueId, values.get(i));
			valueId = getUUID();
		}
		sb.append(String.format(" :%s ) ", valueId));
		expressionsList.add(sb.toString());
		parameters.put(valueId, values.get(size));
		return operatorBuilder;
	}

	public SqlBuilder validate() {
		return new SqlBuilder();
	}

	public List<Object> getList() {
		return expressionsList;
	}

	public void addToList(Object o) {
		expressionsList.add(o);
	}
}
