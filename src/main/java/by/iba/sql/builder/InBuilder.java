package by.iba.sql.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.iba.sql.parts.child.ExpressionChild;

public class InBuilder {

	private String column;
	private List<Object> values;
	private String name;
	private boolean expression = true;
	private ExpressionBuilder expressionBuilder;

	public InBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
		values = new ArrayList<Object>();
	}

	public InBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public InBuilder column(String column) {
		this.column = column;
		return this;
	}

	public InBuilder values(List<Object> values) {
		this.values.addAll(values);
		return this;
	}

	public InBuilder values(Object... values) {
		this.values.addAll(Arrays.asList(values));
		return this;
	}

	public InBuilder values(Object value) {
		values.add(value);
		return this;
	}

	public InBuilder name(String name) {
		this.name = name;
		return this;
	}

	public OperatorBuilder end() {

		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");

		if (name == null) {
			name = column;
		}

		if (values.size() == 0 || values.contains(null) || !expression) {
			SqlBuilder.sqlPart.getChild().add(new ExpressionChild(null));
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(column + " IN(");
			int size = values.size() - 1;
			for (int i = 0; i < size; i++) {
				sb.append(String.format(":%s, ", name + i));
				ExpressionBuilder.parameters.put(name + i, values.get(i));
			}
			sb.append(String.format(":%s%s)", name, size));
			SqlBuilder.sqlPart.getChild().add(new ExpressionChild(sb.toString()));
			ExpressionBuilder.parameters.put(name + size, values.get(size));
		}

		return new OperatorBuilder(expressionBuilder);
	}

}
