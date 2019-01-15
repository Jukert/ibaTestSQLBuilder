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
			//add to start and end list '(' ')'
			expressionsList.add(0, "(");
			expressionsList.add(")");
			//if true add to main expression
			expressionBuilder.expressionsList.addAll(expressionsList);
			expressionsList = expressionBuilder.expressionsList;
		} else {
			//if false remove expression part and save main expression
			expressionsList = expressionBuilder.expressionsList;
			expressionsList.add(null);
		}
		expressionBuilder = expressionBuilder.getExpressionBuilder();
		return this;
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
}
