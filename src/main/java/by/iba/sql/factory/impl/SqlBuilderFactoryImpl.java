package by.iba.sql.factory.impl;

import java.util.HashMap;
import java.util.Map;

import by.iba.sql.builder.impls.SqlBuilder;
import by.iba.sql.database.DataBase;
import by.iba.sql.factory.SqlBuilderFactory;

public class SqlBuilderFactoryImpl implements SqlBuilderFactory {

	private final DataBase dataBase;
	
	public SqlBuilderFactoryImpl(DataBase dataBase) {
		if (dataBase == null) {
			throw new IllegalArgumentException("Argument database is null");
		}
		this.dataBase = dataBase;
	}

	public SqlBuilder newSqlBuilder(Map<String, Object> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		return new SqlBuilder(paramMap, dataBase);
	}
}
