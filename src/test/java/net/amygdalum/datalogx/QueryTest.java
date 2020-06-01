package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXStatementCompiler;
import net.amygdalum.datalogx.Query;


public class QueryTest {

	@Test
	public void testQuery1() throws Exception {
		DatalogXParser parser = createParserFor("(X,Y) <- p(X,Y)");
		Query query = (Query) parser.query().accept(new DatalogXStatementCompiler());
		assertThat(query.getHead(), notNullValue());
		assertThat(query.getBody(), notNullValue());
		assertThat(query.toString(), equalTo("(X,Y) <- p(X,Y)"));
	}
	
}
