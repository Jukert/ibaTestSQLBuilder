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

import by.iba.sql.builder.impls.SqlBuilder;
import by.iba.sql.common.ExpressionFilter;
import by.iba.sql.common.UserFiler;
import by.iba.sql.database.impl.DB2Base;
import by.iba.sql.util.SqlConstatnt;

@RunWith(Parameterized.class)
public class ExpressionTest {

	//SELECT * FROM USERS WHERE ((age > :age AND name LIKE '%'||:name||'%') OR id > :id) AND surname LIKE '%'||:surname||'%'
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						{new ExpressionFilter(true, false),
						"SELECT * FROM user WHERE (id > :id) AND surname LIKE '%'||:surname||'%'"},
						{new ExpressionFilter(true, true),
						"SELECT * FROM user WHERE ((age > :age AND name LIKE '%'||:name||'%') OR id > :id) AND surname LIKE '%'||:surname||'%'"},
						{new ExpressionFilter(false, false),
						"SELECT * FROM user WHERE surname LIKE '%'||:surname||'%'"},
						{new ExpressionFilter(false, true),
						"SELECT * FROM user WHERE surname LIKE '%'||:surname||'%'"},
				});
	}
	

	@Parameter
	public ExpressionFilter filter;
	@Parameter(1)
	public String expected;
	private UserFiler user = new UserFiler(1L, "Pablo", "Escobaro", 25L);
	
	//SELECT * FROM USERS WHERE ((age > :age AND name LIKE '%'||:name||'%') OR id > :id) AND surname LIKE '%'||:surname||'%'
	@Test
	public void validateNullObject() throws SQLSyntaxErrorException {

		final String actual = new SqlBuilder(new HashMap<String, Object>(), new DB2Base())
				.sql("SELECT * FROM user WHERE ")
				.expression(filter.isFirstExpression())
					.expression(filter.isSecondExpression())
						.compare()
							.column("age")
							.name("age")
							.value(user.getAge())
							.operator(SqlConstatnt.MORE)
						.end()
						.and()
						.like()//"name", "name", user.getName()
							.column("name")
							.value(user.getName())
						.end()
					.end()
					.or()
					.compare()
						.column("id")
						.name("id")
						.value(user.getId())
						.operator(SqlConstatnt.MORE)
					.end()
				.end()
				.and()
				.like()
					.column("surname")
					.value(user.getSurname())
				.end()
				.build();
		assertEquals(expected, actual);
	}
		
	
	
}
