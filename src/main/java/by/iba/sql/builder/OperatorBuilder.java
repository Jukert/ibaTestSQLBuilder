package by.iba.sql.builder;

import by.iba.sql.common.SqlConstatnt;

public class OperatorBuilder extends SqlBuilder {
	
	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder() {
		
	}
	
	public OperatorBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public ExpressionBuilder and() {
		expressionsList.add(SqlConstatnt.AND.getValue());
		return expressionBuilder;
	}

	public ExpressionBuilder or() {
		expressionsList.add(SqlConstatnt.OR.getValue());
		return expressionBuilder;
	}
}
