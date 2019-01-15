package by.iba.sql.builder;

import by.iba.sql.util.StringUtil;

public class ExpressionBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;
	private boolean conditionExpression;

	public ExpressionBuilder() {
		// TODO Auto-generated constructor stub
	}

	public ExpressionBuilder(OperatorBuilder operatorBuilder) {
		sqlList = operatorBuilder.sqlList;
		if (operatorBuilder.getExpressionBuilder() != null)
			expressionBuilder = operatorBuilder.getExpressionBuilder();
	}

	public ExpressionBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public ExpressionBuilder(SqlBuilder sqlBuilder) {
		if (!StringUtil.isEmpty(sqlBuilder.getSql())) {
			sqlList.add(sqlBuilder.getSql());
		}
	}

	public ExpressionBuilder expression(boolean condition) {
		conditionExpression = condition;
		return new ExpressionBuilder(this);
	}

	public LikeBuilder like() {
		return new LikeBuilder(this);
	}

	public CompareBuilder compare() {
		return new CompareBuilder(this);
	}

	public InBuilder in() {
		return new InBuilder(this);
	}

	public BetweenBuilder between() {
		return new BetweenBuilder(this);
	}

	public ExpressionBuilder getExpressionBuilder() {
		return expressionBuilder;
	}

	public boolean isConditionExpression() {
		return conditionExpression;
	}
}
