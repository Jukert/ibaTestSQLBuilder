package by.iba.sql;

import static org.junit.Assert.assertEquals;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import by.iba.sql.builder.SqlBuilder;
import by.iba.sql.common.UserFiler;
import by.iba.sql.util.SqlConstatnt;

@RunWith(Parameterized.class)
public class FirstTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						{ new UserFiler(null, null, null, null),
						select + "1 = 1" },
						
						{ new UserFiler(1L, null, null, null),
						select + "id > :id" },
						
						{ new UserFiler(null, "Albert", null, null),
						select + "name LIKE '%'||:name||'%'" },
						
						{new UserFiler(null, null, "Svann", null),
						select + "surname IN(:surname0, :surname1)" },
						
						{ new UserFiler(null, null, null, 18L),
						select + "age > :age" },
						
						{new UserFiler(null, "Albert", "Svann", null),
						select + "name LIKE '%'||:name||'%' OR surname IN(:surname0, :surname1)" },
						
						{new UserFiler(null, "Albert", null, 18L),
						select + "name LIKE '%'||:name||'%' OR age > :age" },
						
						{new UserFiler(null, "Albert", "Svann", 18L),
						select + "name LIKE '%'||:name||'%' OR surname IN(:surname0, :surname1) AND age > :age" },
						
						{new UserFiler(1L, "Albert", "Svann", 18L),
						select + "id > :id AND name LIKE '%'||:name||'%' OR surname IN(:surname0, :surname1) AND age > :age" },
						
						{new UserFiler(1L, null, "Svann", 18L),
						select + "id > :id AND surname IN(:surname0, :surname1) AND age > :age" }, });
	}

	@Parameter
	public UserFiler filter;
	@Parameter(1)
	public String expected;

	private static String select = "SELECT * FROM user WHERE ";

	@Test
	public void validateNullObject() throws SQLSyntaxErrorException {

		final String actual = new SqlBuilder(new HashMap<String, Object>())
				.type("db2")
				.sql(select)
				.compare()
					.column("id")
					.name("id")
					.value(filter.getId())
					.operator(SqlConstatnt.MORE)
				.end()
				.and()
				.like()
						.column("name")
						.value(filter.getName())
				.end()
				.or()
				.in()
					.column("surname")
					.name("surname")
					.values(filter.getSurname(), filter.getSurname())
				.end()
				.and()
				.compare()
					.column("age")
					.name("age")
					.value(filter.getAge())
					.operator(SqlConstatnt.MORE)
				.end()
				.build();
		assertEquals(expected, actual);
	}
}
