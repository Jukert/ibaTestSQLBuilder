package by.iba.sql.builder.impls;

import by.iba.sql.parts.SqlPart;


public class ExpressionBuilder extends SqlBuilder {

	public ExpressionBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public ExpressionBuilder expression(boolean condition) {
		
		SqlPart parent = SqlBuilder.sqlPart;
		parent.setConditionExpression(condition);
		parent.setSqlPart(new SqlPart(SqlBuilder.sqlPart));
		parent.empty();
		return new ExpressionBuilder();
	}

	public LikeBuilder like() {
		return new LikeBuilder();
	}

	public CompareBuilder compare() {
		return new CompareBuilder();
	}

	public InBuilder in() {
		return new InBuilder();
	}

	public BetweenBuilder between() {
		return new BetweenBuilder();
	}
}
