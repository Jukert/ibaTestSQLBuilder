package by.iba.sql.builder.impls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.iba.sql.builder.Builder;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;

public class InBuilder implements Builder<InBuilder> {

	private String column;
	private List<Object> values;
	private String name;
	private boolean expression = true;
	private String sql;
	private SqlPart sqlPart;

	public InBuilder(SqlPart sqlPart) {
		values = new ArrayList<Object>();
		this.sqlPart = sqlPart;
	}

	public InBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public InBuilder column(String column) {
		this.column = column;
		return this;
	}

	public InBuilder values(List<String> values) {
		if (values != null) {
			this.values.addAll(values);
		}
		return this;
	}

	public InBuilder values(Object... values) {
		this.values.addAll(Arrays.asList(values));
		return this;
	}

	public InBuilder value(Object value) {
		values.add(value);
		return this;
	}

	public InBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public InBuilder sql(String sql) {
		this.sql = sql;
		return this;
	}

	public SqlBuilder end() {

		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");

		if (name == null) {
			name = column;
		}

		if (sql != null) {
			sqlPart.getExpressionChilds().add(new ExpressionChild(
					column + " IN(" + sql + ")"
					));
			return new SqlBuilder(sqlPart);
		}
		
		if ((values.size() == 0 || values.contains(null) || !expression) && sql == null) {
			sqlPart.getExpressionChilds().add(
					new ExpressionChild(null));
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(column + " IN(");
			int size = values.size() - 1;
			for (int i = 0; i < size; i++) {
				sb.append(String.format(":%s, ", name + i));
				ExpressionBuilder.parameters.put(name + i, values.get(i));
			}
			sb.append(String.format(":%s%s)", name, size));
			sqlPart.getExpressionChilds().add(
					new ExpressionChild(sb.toString()));
			ExpressionBuilder.parameters.put(name + size, values.get(size));
		}

		return new SqlBuilder(sqlPart);
	}

}
