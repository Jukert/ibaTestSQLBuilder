package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;

public class DB2Base implements DataBase {

	private String limitQuery = " FETCH FIRST %d ROWS ONLY ";
	private String like = "%s LIKE '%%'||:%s||'%%'";
	
	public String getLimit() {
		return limitQuery;
	}

	public String getLike() {
		return like;
	}

}
