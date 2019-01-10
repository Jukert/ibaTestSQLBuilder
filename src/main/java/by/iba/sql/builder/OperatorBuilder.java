package by.iba.sql.builder;

import by.iba.sql.common.SqlConstatnt;

public class OperatorBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder(ExpressionBuilder expressionBuilder) {
		expressionsList = expressionBuilder.expressionsList;
	}

	public ExpressionBuilder and() {
		expressionsList.add(SqlConstatnt.AND.getValue());
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder or() {
		expressionsList.add(SqlConstatnt.OR.getValue());
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		expressionsList.add(operator.getValue());
		return new ExpressionBuilder(this);
	}

	public OperatorBuilder end() {

		if (expressionBuilder == null) {
			expressionsList.add(") ");
		}
		return this;
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
}
