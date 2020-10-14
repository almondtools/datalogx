package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

public class ConjunctionTest {

	@Test
	public void testConjunction() throws Exception {
		DatalogXParser parser = createParserFor("p(a,b) & ~s(b,c)");
		Conjunction statement = (Conjunction) parser.conjunction().accept(new DatalogXStatementCompiler());
		assertThat(statement.getAtoms(), containsInAnyOrder(
			new PositiveLiteral("p",var("a"),var("b")),
			new NegativeLiteral("s",var("b"),var("c"))
			));
	}
}
