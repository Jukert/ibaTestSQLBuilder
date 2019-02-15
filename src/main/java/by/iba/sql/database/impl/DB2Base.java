package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;
import by.iba.sql.parts.AdditionalPart;

public class DB2Base implements DataBase {

	private String headerLimit = 
								"SELECT "
								    + "* "
								+ "FROM "
								    + "( "
								        + "SELECT "
								            + "INNER_TABLE.*, "
								            + "ROWNUMBER() OVER() AS ROW_NUM "
								        + "FROM "
								            + "( ";
	private String limit = 
			") INNER_TABLE ) OUTER_TABLE "
					+ "WHERE "
						+ "OUTER_TABLE.ROW_NUM BETWEEN %1$d AND " + "%1$d+%2$d" +" "
					+ "FETCH "
						+ "FIRST %2$d ROWS ONLY ";
	private String like = "%s LIKE '%%'||:%s||'%%'";
	
	public AdditionalPart getLimit() {
		return new AdditionalPart(headerLimit, limit);
	}

	public String getLike() {
		return like;
	}

}
