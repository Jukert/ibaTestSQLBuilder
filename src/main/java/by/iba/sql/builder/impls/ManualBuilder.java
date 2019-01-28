package by.iba.sql.builder.impls;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.builder.Builder;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;

public class ManualBuilder implements Builder<ManualBuilder> {

	private String sql;
	private String name;
	private boolean expression = true;
	private SqlPart sqlPart;
	
	public ManualBuilder(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}
	
	public ManualBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}

	public ManualBuilder column(String column) {
		try {
			throw new OperationNotSupportedException("Not use this method");
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ManualBuilder value(Object sql) {
		this.sql = (String) sql;
		return this;
	}
	
	public ManualBuilder name(String name) {
		this.name = name;
		return this;
	}

	public SqlBuilder end() {
		if (name == null) {
			throw new IllegalArgumentException("Not declare name field.");
		}
		
		if (expression){
			sqlPart.getExpressionChilds().add(new ExpressionChild(sql));
		}
		return new SqlBuilder();
	}
}
