package by.iba.sql.builder;

import by.iba.sql.util.OperatorConstant;
import by.iba.sql.util.SqlConstatnt;

public class OperatorBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder(ExpressionBuilder expressionBuilder) {
		if (expressionBuilder.getExpressionBuilder() != null)
			this.expressionBuilder = expressionBuilder.getExpressionBuilder();
		expressionsList = expressionBuilder.expressionsList;
	}

	public ExpressionBuilder and() {
		expressionsList.add(OperatorConstant.AND);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder or() {
		expressionsList.add(OperatorConstant.OR);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder all() {
		expressionsList.add(OperatorConstant.ALL);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder any() {
		expressionsList.add(OperatorConstant.ANY);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder some() {
		expressionsList.add(OperatorConstant.SOME);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		expressionsList.add(operator.getValue());
		return new ExpressionBuilder(this);
	}

	public OperatorBuilder end() {

		if (expressionBuilder.isConditionExpression()) {
			expressionsList.add(0, "(");
			expressionsList.add(")");
			expressionBuilder.expressionsList.addAll(expressionsList);
		} else {
			expressionsList = expressionBuilder.expressionsList;
			expressionsList.add(null);
		}
		expressionsList = expressionBuilder.expressionsList;
		expressionBuilder = expressionBuilder.getExpressionBuilder();
		return this;
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
}
