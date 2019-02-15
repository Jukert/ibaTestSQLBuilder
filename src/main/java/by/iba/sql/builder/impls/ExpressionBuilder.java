package by.iba.sql.builder.impls;

import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;

public class ExpressionBuilder extends SqlBuilder {

	private SqlPart sqlPart;
	
	public ExpressionBuilder(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}

	public ExpressionBuilder expression(boolean condition) {

		SqlPart parent = sqlPart;
		parent.setConditionExpression(condition);
		parent.setSqlPart(new SqlPart(sqlPart));
		parent.empty();
		return new ExpressionBuilder(sqlPart);
	}

	public LikeBuilder like() {
		return new LikeBuilder(sqlPart);
	}

	public CompareBuilder compare() {
		return new CompareBuilder(sqlPart);
	}

	public InBuilder in() {
		return new InBuilder(sqlPart);
	}

	public BetweenBuilder between() {
		return new BetweenBuilder(sqlPart);
	}
	
	public ExpressionBuilder innerSql(String sql) {
		sqlPart.getExpressionChilds().add(new ExpressionChild(sql));
		return this;
	}
}
