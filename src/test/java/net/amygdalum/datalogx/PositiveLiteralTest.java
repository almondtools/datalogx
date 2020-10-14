package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
public class PositiveLiteralTest {

	@Test
	public void testPositiveLiteral() throws Exception {
		DatalogXParser parser = createParserFor("p(a,b)");
		PositiveLiteral statement = (PositiveLiteral) parser.positiveliteral().accept(new DatalogXStatementCompiler());
		assertThat(statement, equalTo(new PositiveLiteral("p",var("a"),var("b"))));
		assertThat(statement.toString(), equalTo("p(a,b)"));
	}
}
