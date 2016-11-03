package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXStatementCompiler;
import net.amygdalum.datalogx.NegativeLiteral;

public class NegativeLiteralTest {

	@Test
	public void testNegativeLiteral() throws Exception {
		DatalogXParser parser = createParserFor("~p(a,b)");
		NegativeLiteral statement = (NegativeLiteral) parser.negativeliteral().accept(new DatalogXStatementCompiler());
		assertThat(statement, equalTo(new NegativeLiteral("p",var("a"),var("b"))));
		assertThat(statement.toString(), equalTo("~p(a,b)"));
	}
}
