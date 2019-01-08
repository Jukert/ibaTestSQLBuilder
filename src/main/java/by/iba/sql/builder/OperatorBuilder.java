package by.iba.sql.builder;

import by.iba.sql.common.SqlConstatnt;

public class OperatorBuilder {

	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public ExpressionBuilder and() {
		expressionBuilder.addToList(SqlConstatnt.AND.getValue());
		return expressionBuilder;
	}

	public ExpressionBuilder or() {
		expressionBuilder.addToList(SqlConstatnt.OR.getValue());
		return expressionBuilder;
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		expressionBuilder.addToList(operator.getValue());
		return expressionBuilder;
	}

}
