package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerMatchers.factRetraction;
import static net.amygdalum.datalogx.CompilerMatchers.ruleRetraction;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class RetractTest {

	@Test
	public void testAddFact() throws Exception {
		DatalogXParser parser = createParserFor("- p(a,b,c);");
		Retract statement = (Retract) parser.remove().accept(new DatalogXStatementCompiler());
		assertThat(statement.getClause(), instanceOf(Fact.class));
		assertThat(((Fact) statement.getClause()).getBody(), equalTo(new PositiveLiteral("p",var("a"),var("b"),var("c"))));
	}

	@Test
	public void testAddRule() throws Exception {
		DatalogXParser parser = createParserFor("- p(a,b) <- r(a,c) & s(c,b) & a == b;");
		Retract statement = (Retract) parser.remove().accept(new DatalogXStatementCompiler());
		assertThat(statement.getClause(), instanceOf(Rule.class));
		assertThat(((Rule) statement.getClause()).getHead(), equalTo(new PositiveLiteral("p",var("a"),var("b"))));
		assertThat(((Rule) statement.getClause()).getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r",var("a"),var("c")))
			.and(new PositiveLiteral("s",var("c"),var("b")))
			.and(new OperatorLiteral("==", var("a"), var("b")))
			.build()));
	}

	@Test
	public void testAddRuleFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/removeRule.dl");
		List<Statement> statements = parser.compile();
		assertThat(statements, contains(ruleRetraction()));
	}
	
	@Test
	public void testAddFactFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/removeFact.dl");
		List<Statement> statements = parser.compile();
		assertThat(statements, contains(factRetraction()));
	}
	
}
