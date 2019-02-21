package by.iba.sql.builder.impls;

import java.sql.SQLSyntaxErrorException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import by.iba.sql.database.DataBase;
import by.iba.sql.page.Pageable;
import by.iba.sql.page.impl.Sort;
import by.iba.sql.parts.AdditionalPart;
import by.iba.sql.parts.SqlPart;
import by.iba.sql.parts.child.ExpressionChild;
import by.iba.sql.util.Lists;
import by.iba.sql.util.OperatorConstant;
import by.iba.sql.util.SqlConstatnt;

public class SqlBuilder {

	private StringBuilder sql;
	private Pageable pageable;
	protected static DataBase dataBase;
	protected static Map<String, Object> parameters;
	protected SqlPart sqlPart;

	SqlBuilder() {
		sql = new StringBuilder();
	}

	public SqlBuilder(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}

	public SqlBuilder(Map<String, Object> parameters, DataBase dataBase) {
		this(parameters, dataBase, null);
	}

	public SqlBuilder(Map<String, Object> parameters, DataBase dataBase,
			Pageable pageable) {
		SqlBuilder.parameters = parameters;
		SqlBuilder.dataBase = dataBase;
		sql = new StringBuilder();
		if (pageable != null) {
			this.pageable = pageable;
		}
	}

	public ExpressionBuilder sql(String sql) {
		this.sql = new StringBuilder(sql);
		sqlPart = new SqlPart(sql);
		if (pageable != null) {
			sqlPart.setPageable(pageable);
		}
		return new ExpressionBuilder(sqlPart);
	}

	public ManualBuilder sql() {
		return new ManualBuilder(sqlPart);
	}

	public String getSql() {
		return sql != null ? sql.toString() : null;
	}

	public SqlBuilder pageable(Pageable pageable) {
		this.pageable = pageable;
		return this;
	}

	public void limit() {
		Pageable page = sqlPart.getPageable();
		String order = sqlPart.getAdditionalPart().getOrder();
		sqlPart.setAdditionalPart(new AdditionalPart(dataBase.getLimit()
				.getHeaderLimit(), String.format(
				dataBase.getLimit().getLimit(), page.getPage(),
				page.getPerPage())));
		sqlPart.getAdditionalPart().setOrder(order);
	}

	public SqlBuilder orderBy() {
		Pageable page = sqlPart.getPageable();
		Sort sort = page.getSort();
		if (sort.getOrders() == null) {
			sqlPart.getExpressionChilds().add(new ExpressionChild(null));
			return this;
		}
		int size = sort.getOrders().size() - 1;
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (int i = 0; i < size; i++) {
			sb.append(String.format(" %s, ", sort.getOrders().get(i)));
		}
		sb.append(sort.getOrders().get(size).getProperty() + " "
				+ sort.getDirection().name());
		sqlPart.getAdditionalPart().setOrder(sb.toString());
		return this;
	}

	public SqlBuilder type(Object database) {
		SettingDB setting = new SettingDB();
		if (database instanceof String) {
			setting.type((String) database);
		} else {
			setting.type((DataBase) database);
		}
		return this;
	}

	public String build() throws SQLSyntaxErrorException {
		if (sqlPart.getPageable() != null) {
			orderBy().limit();
		}
		String header = sqlPart.getHeaderPart();
		String limit = sqlPart.getAdditionalPart().getLimit();
		String headerLimit = sqlPart.getAdditionalPart().getHeaderLimit();
		String order = sqlPart.getAdditionalPart().getOrder();
		String additional = (order != null ? order : "")
				+ (limit != null ? limit : "");

		StringBuilder sb = new StringBuilder(
				header != null ? (headerLimit + header) : "");

		List<ExpressionChild> list = SqlBuilder.valid(sqlPart
				.getExpressionChilds());

		for (ExpressionChild ex : list) {
			sb.append(ex.getExpression()
					+ (ex.getOperator() != null ? ex.getOperator() : ""));
		}
		if (list == null || list.size() == 0) {
			return header.contains("WHERE") ? header.replace("WHERE", "")
					: null;
		}
		sb.append(additional);
		return sb.toString();
	}

	public ExpressionBuilder and() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.AND);
		return new ExpressionBuilder(sqlPart);
	}

	public ExpressionBuilder or() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.OR);
		return new ExpressionBuilder(sqlPart);
	}

	public ExpressionBuilder all() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.ALL);
		return new ExpressionBuilder(sqlPart);
	}

	public ExpressionBuilder any() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.ANY);
		return new ExpressionBuilder(sqlPart);
	}

	public ExpressionBuilder some() {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				OperatorConstant.SOME);
		return new ExpressionBuilder(sqlPart);
	}

	public ExpressionBuilder operator(SqlConstatnt operator) {
		Lists.getLast(sqlPart.getExpressionChilds()).setOperator(
				operator.getValue());
		return new ExpressionBuilder(sqlPart);
	}

	// end expression()
	public SqlBuilder end() {
		SqlPart child = sqlPart.getSqlPart();
		sqlPart.setExpressionChilds(SqlBuilder.valid(sqlPart
				.getExpressionChilds()));
		// if expression(true)
		if (child.isConditionExpression()) {
			// do (list)
			sqlPart.getExpressionChilds().add(0, new ExpressionChild("("));
			sqlPart.getExpressionChilds().add(new ExpressionChild(")"));
			sqlPart.getExpressionChilds()
					.addAll(0, child.getExpressionChilds());
		}
		// if expression(false)
		else {
			// rewrite
			sqlPart.setExpressionChilds(child.getExpressionChilds());
			sqlPart.getExpressionChilds().add(new ExpressionChild(null));
		}
		// if not exist another part, save header and additional part
		// but if not null that rewrite
		if (child.getSqlPart() != null) {
			sqlPart.setSqlPart(child.getSqlPart());
		} else {
			sqlPart.setAdditionalPart(child.getAdditionalPart());
			sqlPart.setHeaderPart(child.getHeaderPart());
		}
		return this;
	}

	public static List<ExpressionChild> valid(
			List<ExpressionChild> expressionChilds) {

		Iterator<ExpressionChild> itr = expressionChilds.iterator();

		while (itr.hasNext()) {
			ExpressionChild ex = itr.next();
			if (ex.getExpression() == null) {
				itr.remove();
			}
		}

		if (expressionChilds.size() > 0)
			Lists.getLast(expressionChilds).setOperator(null);

		return expressionChilds;
	}

	public static DataBase getDataBase() {
		return dataBase;
	}

	public static void setDataBase(DataBase dataBase) {
		SqlBuilder.dataBase = dataBase;
	}

}