package by.iba.sql.functions;

public class CountBuilder {

	private final static String SQL_COUNT = "SELECT COUNT FROM (%s)";
	
	public static String count(String sql) {
		if (sql == null || sql.isEmpty()) {
			throw new IllegalArgumentException("Sql shouldn't be null");
		}
		return String.format(SQL_COUNT, sql);		
	}
	
}
