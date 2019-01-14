package by.iba.sql.builder;

import java.util.Arrays;
import java.util.List;

import by.iba.sql.util.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class ExpressionBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;
	private boolean conditionExpression;
	
	
	public ExpressionBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public ExpressionBuilder(OperatorBuilder operatorBuilder) {
		expressionsList = operatorBuilder.expressionsList;
		if (operatorBuilder.getExpressionBuilder() != null)
			expressionBuilder = operatorBuilder.getExpressionBuilder();
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
		conditionExpression = condition;
		return new ExpressionBuilder(this);
	}

	public LikeBuilder like() {
		return new LikeBuilder(this);
	}

	public OperatorBuilder compare(SqlConstatnt operator, String field,
			String paramName, Object value) {

		if (StringUtil.isEmpty(field) || StringUtil.isEmpty(paramName)) {
			throw new IllegalArgumentException("Fields couldn't be null");
		}
		if (value == null) {
			expressionsList.add(null);
		} else {
			expressionsList.add(String.format("%s %s :%s", field,
					operator.getValue(), paramName));
			parameters.put(paramName, value);
		}

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName, Object... values) {

		if (StringUtil.isEmpty(field) || StringUtil.isEmpty(paramName)) {
			throw new IllegalArgumentException("Fields couldn't be null");
		}
		
		if (Arrays.asList(values).size() == 0  || Arrays.asList(values).contains(null)) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s IN(", field));
		int size = values.length - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(":%s, ", paramName + i));
			parameters.put(paramName + i, values[i]);
		}
		sb.append(String.format(":%s%s)", paramName, size));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values[size]);

		return new OperatorBuilder(this);
	}

	public OperatorBuilder in(String field, String paramName,
			List<Object> values) {

		if (StringUtil.isEmpty(field) || StringUtil.isEmpty(paramName)) {
			throw new IllegalArgumentException("Fields couldn't be null");
		}
		if (values == null || values.size() == 0) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s IN(", field));
		int size = values.size() - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(":%s, ", paramName + i));
			parameters.put(paramName + i, values.get(i));
		}
		sb.append(String.format(":%s%s)", paramName, size));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values.get(size));

		return new OperatorBuilder(this);
	}
	
	public OperatorBuilder between(String field, String paramName, Object fValue, Object sValue) {
		
		if (StringUtil.isEmpty(field) || StringUtil.isEmpty(paramName)) {
			throw new IllegalArgumentException("Fields couldn't be null");
		}
		if (fValue == null && sValue == null) {
			expressionsList.add(null);
			return new OperatorBuilder(this);
		}
		expressionsList.add(String.format("%s BETWEEN %s0 AND %s1", field, paramName, paramName));
		parameters.put(paramName + "0", fValue);
		parameters.put(paramName + "1", fValue);
		
		return new OperatorBuilder(this);
	}
	
	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
	
	public boolean isConditionExpression() {
		return conditionExpression;
	}
}
