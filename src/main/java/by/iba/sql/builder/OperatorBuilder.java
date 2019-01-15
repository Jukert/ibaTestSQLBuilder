package by.iba.sql.builder;

import by.iba.sql.util.Lists;
import by.iba.sql.util.OperatorConstant;
import by.iba.sql.util.SqlConstatnt;

public class OperatorBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder(ExpressionBuilder expressionBuilder) {
		if (expressionBuilder.getExpressionBuilder() != null)
			this.expressionBuilder = expressionBuilder.getExpressionBuilder();
		sqlList = expressionBuilder.sqlList;
	}

	public ExpressionBuilder and() {
		Lists.getLast(sqlPart.getChild()).setOperator(OperatorConstant.AND);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder or() {
		Lists.getLast(sqlPart.getChild()).setOperator(OperatorConstant.OR);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder all() {
		Lists.getLast(sqlPart.getChild()).setOperator(OperatorConstant.ALL);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder any() {
		Lists.getLast(sqlPart.getChild()).setOperator(OperatorConstant.ANY);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder some() {
		Lists.getLast(sqlPart.getChild()).setOperator(OperatorConstant.SOME);
		return new ExpressionBuilder(this);
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		Lists.getLast(sqlPart.getChild()).setOperator(operator.getValue());
		return new ExpressionBuilder(this);
	}

	public OperatorBuilder end() {

		if (expressionBuilder.isConditionExpression()) {
			//add to start and end list '(' ')'
			sqlList.add(0, "(");
			sqlList.add(")");
			//if true add to main expression
			expressionBuilder.sqlList.addAll(sqlList);
			sqlList = expressionBuilder.sqlList;
		} else {
			//if false remove expression part and save main expression
			sqlList = expressionBuilder.sqlList;
			sqlList.add(null);
		}
		expressionBuilder = expressionBuilder.getExpressionBuilder();
		return this;
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}
}
