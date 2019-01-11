package by.iba.sql.builder;

import java.util.Arrays;
import java.util.List;

import by.iba.sql.util.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class ExpressionBuilder extends SqlBuilder {

	public ExpressionBuilder(OperatorBuilder operatorBuilder) {
		expressionsList = operatorBuilder.expressionsList;
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

	public OperatorBuilder like(String field, String paramName, String value) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (value == null || value.isEmpty()) {
			expressionsList.add(null);
		} else {
			expressionsList.add(" " + field + "  LIKE '%'||:" + paramName
					+ "||'%' ");
			parameters.put(paramName, value);
		}
		return new OperatorBuilder(this);
	}

	public OperatorBuilder compare(SqlConstatnt operator, String field,
			String paramName, Object value) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		if (value == null) {
			expressionsList.add(null);
		} else {
			expressionsList.add(String.format(" %s %s :%s ", field,
					operator.getValue(), paramName));
			parameters.put(paramName, value);
		}

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName, Object... values) {

		if (field == null) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		
		if (Arrays.asList(values).size() == 0  || Arrays.asList(values).contains(null)) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.length - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s, ", paramName + i));
			parameters.put(paramName + i, values[i]);
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values[size]);

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName,
			List<Object> values) {

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
			sb.append(String.format(" :%s, ", paramName + i));
			parameters.put(paramName + i, values.get(i));
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values.get(size));

		return new OperatorBuilder(this);
	}
}
