package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class AnonymousLiteralTest {

	@Test
	public void testAnonymousLiteral() throws Exception {
		DatalogXParser parser = createParserFor("(a,b)");
		AnonymousLiteral statement = (AnonymousLiteral) parser.anonymousliteral().accept(new DatalogXStatementCompiler());
		assertThat(statement, equalTo(new AnonymousLiteral(var("a"), var("b"))));
		assertThat(statement.toString(), equalTo("(a,b)"));
	}
}
