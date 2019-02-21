package by.iba.sql.factory;

import java.util.Map;

import by.iba.sql.builder.impls.SqlBuilder;

public interface SqlBuilderFactory {

	public SqlBuilder newSqlBuilder(Map<String, Object> paramMap);
	
}
