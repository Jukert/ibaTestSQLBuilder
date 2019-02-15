package by.iba.sql.database.impl;

import by.iba.sql.database.DataBase;
import by.iba.sql.parts.AdditionalPart;

public class MySQLBase implements DataBase {

	private String headerLimit = "";
	private String limit = "OFFSET %d LIMIT %d ";
	private String like = "%s LIKE CONCAT('%%',:%s,'%%')";

	public AdditionalPart getLimit() {
		return new AdditionalPart(headerLimit, limit);
	}

	public String getLike() {
		return like;
	}

}
