package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;

public class DB2Base implements DataBase {

	private String limitQuery = " FETCH FIRST %d ROWS ONLY ";
	
	public String getLimit() {
		return limitQuery;
	}

}
