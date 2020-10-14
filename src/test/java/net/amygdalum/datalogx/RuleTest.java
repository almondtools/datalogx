package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

public class RuleTest {

	@Test
	public void testJoin() throws Exception {
		DatalogXParser parser = createParserFor("join(a,c) <- r(a,b) & s(b,c)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("join", var("a"), var("c"))));
		assertThat(statement.getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r", var("a"), var("b")))
			.and(new PositiveLiteral("s", var("b"), var("c")))
			.build()));
		assertThat(statement.toString(), equalTo("join(a,c) <- r(a,b) & s(b,c)"));
	}

	@Test
	public void testUnion() throws Exception {
		DatalogXParser parser = createParserFor("union(a,b) <- r(a,b) | s(a,b)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("union", var("a"), var("b"))));
		assertThat(statement.getBody(), equalTo(new Disjunction.Builder()
			.or(new PositiveLiteral("r", var("a"), var("b")))
			.or(new PositiveLiteral("s", var("a"), var("b")))
			.build()));
		assertThat(statement.toString(), equalTo("union(a,b) <- r(a,b) | s(a,b)"));
	}

	@Test
	public void testDifference() throws Exception {
		DatalogXParser parser = createParserFor("diff(a,b) <- r(a,b) & ~s(b,c)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("diff", var("a"), var("b"))));
		assertThat(statement.getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r", var("a"), var("b")))
			.and(new NegativeLiteral("s", var("b"), var("c")))
			.build()));
		assertThat(statement.toString(), equalTo("diff(a,b) <- r(a,b) & ~s(b,c)"));
	}

	@Test
	public void testJoinWithWildcard() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & s(_, b)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("result", var("a"))));
		assertThat(statement.getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r", var("a"), var("b")))
			.and(new PositiveLiteral("s", var("_"), var("b")))
			.build()));
		assertThat(statement.toString(), equalTo("result(a) <- r(a,b) & s(_,b)"));
	}

	@Test
	public void testDifferenceWithWildcard() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & ~s(*, b)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("result", var("a"))));
		assertThat(statement.getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r", var("a"), var("b")))
			.and(new NegativeLiteral("s", var("*"), var("b")))
			.build()));
		assertThat(statement.toString(), equalTo("result(a) <- r(a,b) & ~s(*,b)"));
	}

	@Test(expected=SemanticAnalysisException.class)
	public void testJoinWithIllegalWildcard() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & s(*, b)");
		parser.idbrule().accept(new DatalogXStatementCompiler());
	}

	@Test(expected=SemanticAnalysisException.class)
	public void testDifferenceWithIllegalWildcard() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & ~s(_, b)");
		parser.idbrule().accept(new DatalogXStatementCompiler());
	}

	@Test
	public void testComplex() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & s(b) | s(a)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("result", var("a"))));
		assertThat(statement.getBody(), equalTo(new Disjunction.Builder()
			.or(new Conjunction.Builder()
				.and(new PositiveLiteral("r", var("a"), var("b")))
				.and(new PositiveLiteral("s", var("b")))
				.build())
			.or(new PositiveLiteral("s", var("a")))
			.build()));
		assertThat(statement.toString(), equalTo("result(a) <- r(a,b) & s(b) | s(a)"));
	}

	@Test
	public void testComplexWithUnification() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & s(b) & a == b | s(a)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("result", var("a"))));
		assertThat(statement.getBody(), equalTo(new Disjunction.Builder()
			.or(new Conjunction.Builder()
				.and(new PositiveLiteral("r", var("a"), var("b")))
				.and(new PositiveLiteral("s", var("b")))
				.and(new OperatorLiteral("==", var("a"), var("b")))
				.build())
			.or(new PositiveLiteral("s", var("a")))
			.build()));
		assertThat(statement.toString(), equalTo("result(a) <- r(a,b) & s(b) & a == b | s(a)"));
	}

	@Test
	public void testComplexWithComparison() throws Exception {
		DatalogXParser parser = createParserFor("result(a) <- r(a,b) & s(b) & a <= b | s(a)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead(), equalTo(new PositiveLiteral("result", var("a"))));
		assertThat(statement.getBody(), equalTo(new Disjunction.Builder()
			.or(new Conjunction.Builder()
				.and(new PositiveLiteral("r", var("a"), var("b")))
				.and(new PositiveLiteral("s", var("b")))
				.and(new OperatorLiteral("<=", var("a"), var("b")))
				.build())
			.or(new PositiveLiteral("s", var("a")))
			.build()));
		assertThat(statement.toString(), equalTo("result(a) <- r(a,b) & s(b) & a <= b | s(a)"));
	}

	@Test
	public void testMultiHead() throws Exception {
		DatalogXParser parser = createParserFor("r(a) & r(b) <- s(a,b)");
		Rule statement = (Rule) parser.idbrule().accept(new DatalogXStatementCompiler());
		assertThat(statement.getHead().getAtoms(), contains(new PositiveLiteral("r", var("a")), new PositiveLiteral("r", var("b"))));
		assertThat(statement.getBody(), equalTo(new PositiveLiteral("s", var("a"), var("b"))));
		assertThat(statement.toString(), equalTo("r(a) & r(b) <- s(a,b)"));
	}
}
