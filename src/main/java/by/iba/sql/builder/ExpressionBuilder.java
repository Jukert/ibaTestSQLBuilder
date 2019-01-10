package by.iba.sql.builder;

import java.util.List;
import java.util.Map;

import by.iba.sql.common.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class ExpressionBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;
	private boolean expressionCondition;

	public ExpressionBuilder(OperatorBuilder operatorBuilder) {
		expressionsList = operatorBuilder.expressionsList;
		this.expressionBuilder = operatorBuilder.getExpressionBuilder();
	}

	public ExpressionBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public ExpressionBuilder(SqlBuilder sqlBuilder) {
		if (!StringUtil.isEmpty(sqlBuilder.getSql())) {
			expressionsList.add(sqlBuilder.getSql());
		}
	}

	public ExpressionBuilder expression(boolean condition) {
		ExpressionBuilder eb = null;
		if (!condition) {
			eb = new ExpressionBuilder(this);
			return eb;
		}
		expressionsList.add(" (");
		return this;
	}

	public OperatorBuilder addLike(String field, String value,
			String paramName, Map<String, Object> parameters) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (value == null || value.isEmpty()) {
			expressionsList.add(null);
		}
		expressionsList.add(" " + field + "  LIKE '%'||:" + paramName
				+ "||'%' ");
		parameters.put(paramName, value);

		return new OperatorBuilder(this);
	}

	public OperatorBuilder addCompare(SqlConstatnt operator, String field,
			String value, String paramName, Map<String, Object> parameters) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (value == null || value.isEmpty()) {
			expressionsList.add(null);
		} else {
			expressionsList.add(String.format(" %s %s :%s ", field,
					operator.getValue(), paramName));
			parameters.put(paramName, value);
		}

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName,
			Map<String, Object> parameters, Object... values) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (values == null || values.length == 0) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.length - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", paramName + i));
			parameters.put(paramName + i, values[i]);
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values[size]);

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName,
			Map<String, Object> parameters, List<Object> values) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (values == null || values.size() == 0) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.size() - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", paramName + i));
			parameters.put(paramName + i, values.get(i));
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values.get(size));

		return new OperatorBuilder(this);
	}

	public void addToList(Object o) {
		expressionsList.add(o);
	}

	public boolean isExpressionCondition() {
		return expressionCondition;
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
}
