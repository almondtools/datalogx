package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerMatchers.factAssertion;
import static net.amygdalum.datalogx.CompilerMatchers.ruleAssertion;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.Assert;
import net.amygdalum.datalogx.Conjunction;
import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.DatalogXStatementCompiler;
import net.amygdalum.datalogx.Fact;
import net.amygdalum.datalogx.NegativeLiteral;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Rule;
import net.amygdalum.datalogx.Statement;

public class AssertTest {

	@Test
	public void testAddFact() throws Exception {
		DatalogXParser parser = createParserFor("+ p(a,b);");
		Assert statement = (Assert) parser.add().accept(new DatalogXStatementCompiler());
		assertThat(statement.getClause(), instanceOf(Fact.class));
		assertThat(((Fact) statement.getClause()).getBody(), equalTo(new PositiveLiteral("p", var("a"), var("b"))));

		parser = createParserFor("p(a,b);");
		Assert statement2 = (Assert) parser.add().accept(new DatalogXStatementCompiler());
		assertThat(statement2, equalTo(statement));
	}

	@Test
	public void testAddRule() throws Exception {
		DatalogXParser parser = createParserFor("+ p(a,b) : r(a,c) & ~s(c,b);");
		Assert statement = (Assert) parser.add().accept(new DatalogXStatementCompiler());
		assertThat(statement.getClause(), instanceOf(Rule.class));
		assertThat(((Rule) statement.getClause()).getHead(), equalTo(new PositiveLiteral("p", var("a"), var("b"))));
		assertThat(((Rule) statement.getClause()).getBody(), equalTo(new Conjunction.Builder()
			.and(new PositiveLiteral("r", var("a"), var("c")))
			.and(new NegativeLiteral("s", var("c"), var("b")))
			.build()));

		parser = createParserFor("p(a,b) : r(a,c) & ~s(c,b);");
		Assert statement2 = (Assert) parser.add().accept(new DatalogXStatementCompiler());
		assertThat(statement2, equalTo(statement));
	}

	@Test
	public void testAddRuleFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/addRule.dl");
		List<Statement> statements = parser.compile();
		assertThat(statements, contains(ruleAssertion()));
	}

	@Test
	public void testAddFactFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/addFact.dl");
		List<Statement> statements = parser.compile();
		assertThat(statements, contains(factAssertion()));
	}

}
