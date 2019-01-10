package by.iba.test.builder;

import java.sql.SQLSyntaxErrorException;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;

import by.iba.sql.builder.SqlBuilder;

public class SqlBuilderTests {

	@Test
	public void testOrderSyntax() throws SQLSyntaxErrorException,
			OperationNotSupportedException {
		final String expected = " FETCH FIRST %d ROWS ONLY 4";

		final String result = new SqlBuilder().addLimit(4).validate().build();
		
	}

}
