package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import org.junit.Test;

public class DisjunctionTest {

	@Test
	public void testSimpleDisjunction() throws Exception {
		DatalogXParser parser = createParserFor("p(a,b) | ~s(b,c)");
		Disjunction statement = (Disjunction) parser.disjunction().accept(new DatalogXStatementCompiler());
		assertThat(statement.getElements(), containsInAnyOrder(
			new PositiveLiteral("p", var("a"), var("b")),
			new NegativeLiteral("s", var("b"), var("c"))
			));
	}

	@Test
	public void testNestedDisjunction() throws Exception {
		DatalogXParser parser = createParserFor("p(a,b) & r(b,a) | ~s(b,c)");
		Disjunction statement = (Disjunction) parser.disjunction().accept(new DatalogXStatementCompiler());
		assertThat(statement.getElements(), containsInAnyOrder(
			new Conjunction.Builder()
				.and(new PositiveLiteral("p", var("a"), var("b")))
				.and(new PositiveLiteral("r", var("b"), var("a")))
				.build(),
			new NegativeLiteral("s", var("b"), var("c"))
			));
	}
}
