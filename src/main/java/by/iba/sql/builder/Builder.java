package by.iba.sql.builder;

import by.iba.sql.builder.impls.SqlBuilder;

public interface Builder<T> {

	T expression(boolean expression);

	T column(String column);

	T value(Object value);

	T name(String name);

	SqlBuilder end();

}
