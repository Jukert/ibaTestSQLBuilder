package by.iba.sql.builder.impls;

import by.iba.sql.builder.Builders;
import by.iba.sql.parts.child.ExpressionChild;

public class LikeBuilder implements Builders<LikeBuilder> {

	private String column;
	private Object value;
	private String name;
	private boolean expression = true;
	
	public LikeBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public LikeBuilder column(String column) {
		this.column = column;
		return this;
	}

	public LikeBuilder value(Object value) {
		this.value = value;
		return this;
	}

	public LikeBuilder name(String name) {
		this.name = name;
		return this;
	}

	public OperatorBuilder end() {

		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");

		if (name == null) {
			name = column;
		}

		if (value == null || !expression) {
			SqlBuilder.sqlPart.getExpressionChilds().add(new ExpressionChild(null));
		} else {
			SqlBuilder.sqlPart.getExpressionChilds().add(
					new ExpressionChild(column + " LIKE '%'||:" + name
							+ "||'%'"));
			ExpressionBuilder.parameters.put(name, value);
		}

		return new OperatorBuilder();
	}
}
