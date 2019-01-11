package by.iba.sql.builder;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.iba.sql.util.SqlConstatnt;
import by.iba.sql.util.StringUtil;

public class SqlBuilder {

	private StringBuffer sql;
	protected static Map<String, Object> parameters;
	protected List<Object> expressionsList;

	public SqlBuilder(Map<String, Object> parameters) {
		SqlBuilder.parameters = parameters;
		sql = new StringBuffer();
		expressionsList = new ArrayList<Object>();
	}

	 SqlBuilder() {
		sql = new StringBuffer();
		expressionsList = new ArrayList<Object>();
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuffer(sql);
		return new ExpressionBuilder(this);
	}

	public String getSql() {
		return sql != null ? sql.toString() : null;
	}

	public SqlBuilder limit(int limit) {

		expressionsList.add(String.format(" FETCH FIRST %d ROWS ONLY ", limit));

		return this;
	}

	public SqlBuilder orderBy(SqlConstatnt operator, String... fields) {

		if (fields == null || fields.length == 0) {
			throw new IllegalArgumentException("Field couldn't be null");
		}
		int size = fields.length - 1;
		expressionsList.add(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			expressionsList.add(String.format(" %s, ", fields[i]));
		}
		expressionsList.add(fields[size] + " " + operator.getValue());

		return this;
	}

	public String build() throws SQLSyntaxErrorException {

		for (int i = expressionsList.size() - 1; i > 0; i--) {
			
			if (i > expressionsList.size()-1){
				continue;
			}
				
			Object el = expressionsList.get(i) != null ? expressionsList.get(i)
					: "";
			if (!(el.equals(SqlConstatnt.AND.getValue()) || el
					.equals(SqlConstatnt.OR.getValue()))) {
				continue;
			}

			if (i == 0 || i == expressionsList.size() - 1) {
				expressionsList.remove(i);
				continue;
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
		
		if (expressionsList.size() == 1 && expressionsList.get(0).toString().contains("WHERE")){
			expressionsList.add(" 1 = 1 " );
		}

		for (Object object : expressionsList) {
			sql.append(object);
		}

		return sql.toString();
	}
}