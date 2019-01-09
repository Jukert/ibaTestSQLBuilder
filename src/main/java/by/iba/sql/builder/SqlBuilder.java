package by.iba.sql.builder;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.common.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class SqlBuilder {

	private StringBuffer sql;
	private ExpressionBuilder expressionBuilder;
 	protected static List<Object> expressionsList = new ArrayList<Object>();
	

	public SqlBuilder() {
		sql = new StringBuffer();
	}

	public SqlBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
		this.sql = new StringBuffer();
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuffer(sql);
		return new ExpressionBuilder(this);
	}

	public String getSql() {
		return sql.toString();
	}


	public SqlBuilder validate() throws OperationNotSupportedException {

		
		for (int i = expressionsList.size() - 1; i > 0; i--) {
			Object el = expressionsList.get(i) != null ? expressionsList.get(i)
					: "";
			if (!(el.equals(SqlConstatnt.AND.getValue()) 
					|| el.equals(SqlConstatnt.OR.getValue()))) {
				continue;
			}
			
			if (i == 0 || i == expressionsList.size() - 1) {
				throw new OperationNotSupportedException(
						"Operator couldn't be first and last element");
			}
			boolean removeCondition = false;
			if (StringUtil.isEmpty((String) expressionsList.get(i + 1))) {
				expressionsList.remove(i + 1);
				expressionsList.remove(i);
				removeCondition = true;
			}
			if (StringUtil.isEmpty((String) expressionsList.get(i - 1))) {
				if (!removeCondition)
					expressionsList.remove(i);
				expressionsList.remove(i - 1);
			}
		}
		return this;
	}
	
	public String build() {
		for (Object object : expressionsList) {
			sql.append(object.toString());
		}
		return sql.toString();
	}
	
	public void setExpressionBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}
}