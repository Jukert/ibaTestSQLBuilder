package by.iba.sql.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty());
	}

	public static boolean isEqualsOperators(String str) {
		return str.equals(OperatorConstant.AND)
				|| str.equals(OperatorConstant.OR);
	}

	public static boolean whereContains(String str) {
		return str.contains("WHERE");
	}
}
