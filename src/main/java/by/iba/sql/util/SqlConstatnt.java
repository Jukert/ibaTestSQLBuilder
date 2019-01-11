package by.iba.sql.util;

public class SqlConstatnt {

	public final static SqlConstatnt AND = new SqlConstatnt(" AND ");

	public final static SqlConstatnt OR = new SqlConstatnt(" OR ");

	public final static SqlConstatnt LESS = new SqlConstatnt("<");

	public final static SqlConstatnt MORE = new SqlConstatnt(">");

	public final static SqlConstatnt MORE_EQUALS = new SqlConstatnt(">=");

	public final static SqlConstatnt LESS_EQUALS = new SqlConstatnt("<=");

	public final static SqlConstatnt EQUALS = new SqlConstatnt("=");

	public final static SqlConstatnt ASC = new SqlConstatnt(" ASC ");

	public final static SqlConstatnt DESC = new SqlConstatnt(" DESC ");

	private String value;

	public SqlConstatnt(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
