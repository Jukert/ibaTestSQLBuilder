package by.iba.sql.builder;


public class LikeBuilder extends ExpressionBuilder {

	private String column;
	private Object value;
	private String name;
	private boolean expression = true;
	private ExpressionBuilder expressionBuilder;
	
	public LikeBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder;
	}

	public LikeBuilder expression(boolean expression) {
		this.expression = expression;
		return this;
	}
	
	public LikeBuilder column(String column) {
		this.column = column;
		return this;
	}
	
	public LikeBuilder value(Object value) {
		this.value = value;
		return this;
	}
	
	public LikeBuilder name(String name){
		this.name = name;
		return this;
	}
	
	public OperatorBuilder end() {
		
		if (!expression){
			return new OperatorBuilder(expressionBuilder);
		}
		
		if (column == null)
			throw new UnsupportedOperationException("Column couldn't be null!!");
		
		if (name == null) {
			name = column;
		}

		if (value == null) {
			expressionBuilder.expressionsList.add(null);
		} else {
			expressionBuilder.expressionsList.add(column + " LIKE '%'||:" + name
					+ "||'%'");
			parameters.put(name, value);
		}
		
		return new OperatorBuilder(expressionBuilder);
	}
	
//	.expression(value.length() < 25)
//	.column("")
//	.value("")
//	.name("")
//	.end()
}
