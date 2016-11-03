package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXStatementCompiler;
import net.amygdalum.datalogx.OperatorLiteral;

public class OperatorLiteralTest {

	@Test
	public void testOperatorLiteral() throws Exception {
		DatalogXParser parser = createParserFor("a == b");
		OperatorLiteral statement = (OperatorLiteral) parser.operatorliteral().accept(new DatalogXStatementCompiler());
		assertThat(statement, equalTo(new OperatorLiteral("==", var("a"), var("b"))));
		assertThat(statement.toString(), equalTo("a == b"));
	}
}
