package by.iba.sql.common;

public class ExpressionFilter {

	private boolean firstExpression;
	private boolean secondExpression;

	public ExpressionFilter() {
		// TODO Auto-generated constructor stub
	}

	public ExpressionFilter(boolean firstExpression, boolean secondExpression) {
		super();
		this.firstExpression = firstExpression;
		this.secondExpression = secondExpression;
	}

	public boolean isFirstExpression() {
		return firstExpression;
	}

	public void setFirstExpression(boolean firstExpression) {
		this.firstExpression = firstExpression;
	}

	public boolean isSecondExpression() {
		return secondExpression;
	}

	public void setSecondExpression(boolean secondExpression) {
		this.secondExpression = secondExpression;
	}

}
