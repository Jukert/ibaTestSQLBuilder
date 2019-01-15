package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;

public class MySQLBase implements DataBase {

	private String limitQuery = " LIMIT %d ";
	
	public String getLimit() {
		return limitQuery;
	}

}
