package by.iba.sql.builder;

import by.iba.sql.builder.impls.OperatorBuilder;

public interface Builders<T> {

	T expression(boolean expression);
	
	T column(String column);
	
	T value(Object value);
	
	T name(String name);
	
	OperatorBuilder end();
	
}
