package by.iba.sql.builder.impls;

import by.iba.sql.builder.Builders;
import by.iba.sql.parts.child.ExpressionChild;

public class BetweenBuilder implements Builders<BetweenBuilder> {

	private String column;
	private Object fValue;
	private Object sValue;
	private String name;
	private boolean expression = true;

	public BetweenBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public BetweenBuilder column(String column) {
		this.column = column;
		return this;
	}

	public BetweenBuilder firstValue(Object value) {
		fValue = value;
		return this;
	}

	public BetweenBuilder secondValue(Object value) {
		sValue = value;
		return this;
	}

	public BetweenBuilder name(String name) {
		this.name = name;
		return this;
	}

	public OperatorBuilder end() {

		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");

		if (name == null) {
			name = column;
		}

		if (fValue == null || sValue == null || !expression) {
			SqlBuilder.sqlPart.getExpressionChilds().add(new ExpressionChild(null));
		} else {
			SqlBuilder.sqlPart.getExpressionChilds().add(
					new ExpressionChild(String.format(
							"%s BETWEEN :%s0 AND :%s1", column, name, name)));
			ExpressionBuilder.parameters.put(name + "0", fValue);
			ExpressionBuilder.parameters.put(name + "1", fValue);
		}

		return new OperatorBuilder();
	}

	public BetweenBuilder value(Object value) {
		throw new UnsupportedOperationException("In this method use fValue() and sValue()");
	}
}
