package by.iba.sql.builder.impls;

import by.iba.sql.builder.Builder;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;
import by.iba.sql.util.SqlConstatnt;

public class CompareBuilder implements Builder<CompareBuilder> {

	private String column;
	private Object value;
	private String name;
	private boolean expression = true;
	private SqlConstatnt operator = SqlConstatnt.EQUALS;
	private SqlPart sqlPart;
	
	public CompareBuilder(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}
	
	public CompareBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public CompareBuilder column(String column) {
		this.column = column;
		return this;
	}

	public CompareBuilder value(Object value) {
		this.value = value;
		return this;
	}

	public CompareBuilder name(String name) {
		this.name = name;
		return this;
	}

	public CompareBuilder operator(SqlConstatnt operator) {
		this.operator = operator;
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
					new ExpressionChild(String.format("%s %s :%s", column,
							operator.getValue(), name)));
			ExpressionBuilder.parameters.put(name, value);
		}

		return new SqlBuilder(sqlPart);
	}
}
