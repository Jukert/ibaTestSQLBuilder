package by.iba.sql.builder.impls;

import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;
import by.iba.sql.util.Lists;
import by.iba.sql.util.OperatorConstant;
import by.iba.sql.util.SqlConstatnt;

public class OperatorBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;

	public OperatorBuilder() {
		expressionBuilder = new ExpressionBuilder();
	}

	public ExpressionBuilder and() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.AND);
		return expressionBuilder;
	}

	public ExpressionBuilder or() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.OR);
		return expressionBuilder;
	}

	public ExpressionBuilder all() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.ALL);
		return expressionBuilder;
	}

	public ExpressionBuilder any() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.ANY);
		return expressionBuilder;
	}

	public ExpressionBuilder some() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.SOME);
		return expressionBuilder;
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				operator.getValue());
		return expressionBuilder;
	}

	// end expression()
	public OperatorBuilder end() {
		SqlPart child = sqlPart.getSqlPart();
		sqlPart.setExpressionChilds(SqlBuilder.valid(sqlPart
				.getExpressionChilds()));
		// if expression(true)
		if (child.isConditionExpression()) {
			// do (list)
			sqlPart.getExpressionChilds().add(0, new ExpressionChild("("));
			sqlPart.getExpressionChilds().add(new ExpressionChild(")"));
			sqlPart.getExpressionChilds()
					.addAll(0, child.getExpressionChilds());
		}
		// if expression(false)
		else {
			// rewrite
			sqlPart.setExpressionChilds(child.getExpressionChilds());
			sqlPart.getExpressionChilds().add(new ExpressionChild(null));
		}
		// if not exist another part, save header and additional part
		// but if not null that rewrite
		if (child.getSqlPart() != null) {
			sqlPart.setSqlPart(child.getSqlPart());
		} else {
			sqlPart.setAdditionalPart(child.getAdditionalPart());
			sqlPart.setHeaderPart(child.getHeaderPart());
		}
		return this;
	}
}
