package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FactTest {

	@Mock
	private ParserRuleContext ctx;
	
	@Test
	public void testFact() throws Exception {
		DatalogXParser parser = createParserFor("p(a,b)");
		Fact statement = (Fact) parser.edbfact().accept(new DatalogXStatementCompiler());
		assertThat(statement.getBody(), equalTo(new PositiveLiteral("p",var("a"),var("b"))));
		assertThat(statement.toString(), equalTo("p(a,b)"));
	}

	@Test
	public void testValidateBodyForConjunction() throws Exception {
		Conjunction in = new Conjunction.Builder()
			.and(new PositiveLiteral("p"))
			.and(new NegativeLiteral("q"))
			.build();
		Fact.isFactBody().validate(in, null);
	}

	@Test
	public void testValidateBodyForLiteral() throws Exception {
		AtomicFormula pos = new PositiveLiteral("p");
		Fact.isFactBody().validate(pos, null);
		AtomicFormula neg = new NegativeLiteral("q");
		Fact.isFactBody().validate(neg, null);
	}

	@Test(expected = SemanticAnalysisException.class)
	public void testValidateBodyNoConjunction() throws Exception {
		Disjunction in = new Disjunction.Builder().build();
		Fact.isFactBody().validate(in, ctx);
	}

	@Test(expected = SemanticAnalysisException.class)
	public void testValidateBodyOperatorLiteral() throws Exception {
		Disjunction in = new Disjunction.Builder().build();
		Fact.isFactBody().validate(in, ctx);
	}

}
