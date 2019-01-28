package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;

public class MySQLBase implements DataBase {

	private String limitQuery = " LIMIT %d ";
	private String like = "%s LIKE CONCAT('%%',:%s,'%%')";

	public String getLimit() {
		return limitQuery;
	}

	public String getLike() {
		return like;
	}

}
