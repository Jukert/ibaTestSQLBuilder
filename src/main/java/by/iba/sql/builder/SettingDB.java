package by.iba.sql.builder;

import by.iba.sql.database.DataBase;
import by.iba.sql.database.impl.DB2Base;
import by.iba.sql.database.impl.MySQLBase;

public class SettingDB {

	private String mysql = "mysql";
	private String db2 = "db2";

	public void type(String databse) {

		if (databse.equals(mysql)) {
			SqlBuilder.dataBase = new MySQLBase();
		} else if (databse.equals(db2)) {
			SqlBuilder.dataBase = new DB2Base();
		} else {
			throw new UnsupportedOperationException(
					"There isn't supported this base!!!");
		}
	}
	
	public void type(DataBase dataBase) {
		if(dataBase != null){
			SqlBuilder.dataBase = dataBase;
			return;
		}
		throw new UnsupportedOperationException("Error initialisation base");
	}

}
