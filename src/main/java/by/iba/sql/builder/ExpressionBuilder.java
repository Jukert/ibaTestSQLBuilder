package by.iba.sql.builder;

import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import by.iba.sql.common.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class ExpressionBuilder extends SqlBuilder {

	private ExpressionBuilder expressionBuilder;
	private SqlBuilder sqlBuilder;

	public ExpressionBuilder(SqlBuilder sqlBuilder) {
		expressionsList.add(sqlBuilder.getSql());
	}
	
	private ExpressionBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public ExpressionBuilder sql(String sql) {
		expressionsList.add(sql);
		return this;
	}

	public OperatorBuilder addLike(String field, String value,
			String paramName, Map<String, Object> parameters) {
		if (field == null)
			throw new IllegalArgumentException("Field couldn't be null");
		if (value == null || value.isEmpty()){
			expressionsList.add(null);
		}
		expressionsList.add(" " + field + "  LIKE '%'||:" + paramName
				+ "||'%' ");
		parameters.put(paramName, value);
		return new OperatorBuilder(this);
	}
	
	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public ExpressionBuilder expression(boolean condition) {
		if (!condition) {
			ExpressionBuilder eb = new ExpressionBuilder(this);
			return eb;
		}
		expressionsList.add(" (");
		return this;
	}

	public ExpressionBuilder end() {
		if (expressionBuilder != null) {
			expressionsList = expressionBuilder.getList();
			expressionBuilder = null;
		} else {
			expressionsList.add(") ");
		}

		return this;
	}

	public ExpressionBuilder addCompare(SqlConstatnt operator, String field,
			String value, String paramName, Map<String, Object> parameters) {
		if (field == null)
			throw new IllegalArgumentException("Field couldn't be null");
		if (value == null || value.isEmpty()){
			expressionsList.add(null);
			return this;
		}
		expressionsList.add(String.format(" %s %s :%s ", field,
				operator.getValue(), paramName));
		parameters.put(paramName, value);
		return this;
	}

	public ExpressionBuilder in(String field, String paramName,
			Map<String, Object> parameters, Object... values) {
		if (field == null)
			throw new IllegalArgumentException("Field couldn't be null");
		if (values == null || values.length == 0){
			expressionsList.add(null);
			return this;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.length - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", paramName + i));
			parameters.put(paramName + i, values[i]);
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values[size]);
		return this;
	}

	public ExpressionBuilder in(String field, String paramName,
			Map<String, Object> parameters, List<Object> values) {
		if (field == null)
			throw new IllegalArgumentException("Field couldn't be null");
		if (values == null || values.size() == 0){
			expressionsList.add(null);
			return this;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(" %s IN( ", field));
		int size = values.size() - 1;
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" :%s , ", paramName + i));
			parameters.put(paramName + i, values.get(i));
		}
		sb.append(String.format(" :%s ) ", paramName));
		expressionsList.add(sb.toString());
		parameters.put(paramName + size, values.get(size));
		return this;
	}

	public ExpressionBuilder and() {
		expressionsList.add(SqlConstatnt.AND.getValue());
		return this;
	}

	public ExpressionBuilder or() {
		expressionsList.add(SqlConstatnt.OR.getValue());
		return this;
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		expressionsList.add(operator.getValue());
		return this;
	}

	public ExpressionBuilder addOrder(SqlConstatnt operator, String... fields) {
		if (fields == null || fields.length == 0)
			throw new IllegalArgumentException("Field couldn't be null");
		int size = fields.length - 1;
		expressionsList.add(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			expressionsList.add(String.format(" %s, ", fields[i]));
		}
		expressionsList.add(String.format(" %s %s ", fields[size],
				operator.getValue()));
		return this;
	}

	public ExpressionBuilder addLimit(int limit) {
		expressionsList.add(String.format(" FETCH FIRST %d ROWS ONLY ", limit));
		return this;
	}

	public List<Object> getList() {
		return expressionsList;
	}

	public void addToList(Object o) {
		expressionsList.add(o);
	}

	public SqlBuilder validate() throws OperationNotSupportedException {

		for (int i = expressionsList.size() - 1; i > 0; i--) {
			Object el = expressionsList.get(i) != null ? expressionsList.get(i)
					: "";
			if (!(el.equals(SqlConstatnt.AND.getValue()) 
					|| el.equals(SqlConstatnt.OR.getValue()))) {
				continue;
			}
			
			if (i == 0 || i == expressionsList.size() - 1){
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
		return new SqlBuilder(this);
	}
}
