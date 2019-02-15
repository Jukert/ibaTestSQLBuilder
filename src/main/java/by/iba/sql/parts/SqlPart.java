package by.iba.sql.parts;

import java.util.ArrayList;
import java.util.List;

import by.iba.sql.page.Pageable;
import by.iba.sql.parts.child.ExpressionChild;

public class SqlPart {

	private String headerPart;
	private List<ExpressionChild> expressionChilds;
	private AdditionalPart additionalPart;
	private SqlPart sqlPart;
	private Pageable pageable;
	private boolean conditionExpression = true;

	public SqlPart(String headerPart) {
		this.headerPart = headerPart;
		expressionChilds = new ArrayList<ExpressionChild>();
		additionalPart = new AdditionalPart();
	}

	public SqlPart(SqlPart sqlPart) {
		additionalPart = sqlPart.additionalPart;
		expressionChilds = sqlPart.expressionChilds;
		conditionExpression = sqlPart.conditionExpression;
		headerPart = sqlPart.headerPart;
		if (sqlPart.getSqlPart() != null) {
			this.sqlPart = sqlPart.getSqlPart();
		}
	}

	public String getHeaderPart() {
		return headerPart;
	}

	public void setHeaderPart(String headerPart) {
		this.headerPart = headerPart;
	}

	public List<ExpressionChild> getExpressionChilds() {
		return expressionChilds;
	}

	public void setExpressionChilds(List<ExpressionChild> child) {
		this.expressionChilds = child;
	}

	public AdditionalPart getAdditionalPart() {
		return additionalPart;
	}

	public void setAdditionalPart(AdditionalPart additionalPart) {
		this.additionalPart = additionalPart;
	}

	public SqlPart getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(SqlPart sqlPart) {
		this.sqlPart = sqlPart;
	}

	public boolean isConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(boolean conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public void empty() {
		headerPart = null;
		expressionChilds = new ArrayList<ExpressionChild>();
		additionalPart = new AdditionalPart();
		conditionExpression = true;
	}

}
