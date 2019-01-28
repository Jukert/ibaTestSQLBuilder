package by.iba.sql.builder.impls;

import by.iba.sql.builder.Builder;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;

public class LikeBuilder implements Builder<LikeBuilder> {

	private String column;
	private Object value;
	private String name;
	private boolean expression = true;
	private SqlPart sqlPart;
	
	public LikeBuilder(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}
	
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

	public SqlBuilder end() {

		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");

		if (name == null) {
			name = column;
		}

		if (value == null || !expression) {
			sqlPart.getExpressionChilds().add(
					new ExpressionChild(null));
		} else {
			sqlPart.getExpressionChilds().add(
					new ExpressionChild(String.format(SqlBuilder.dataBase.getLike(), column, name)));
			ExpressionBuilder.parameters.put(name, value);
		}

		return new SqlBuilder(sqlPart);
	}
}
