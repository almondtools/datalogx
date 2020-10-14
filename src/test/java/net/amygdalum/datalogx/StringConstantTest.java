package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.StringConstant.strL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class StringConstantTest {

	@Test
	public void testStringConstant() throws Exception {
		StringConstant a = strL("'a'");
		assertThat(a.getValue(), equalTo("a"));
		assertThat(a.toString(), equalTo("'a'"));
		
		StringConstant aQuote = strL("'a\\\"'");
		assertThat(aQuote.getValue(), equalTo("a\""));
		assertThat(aQuote.toString(), equalTo("'a\\\"'"));
		
		StringConstant aBackslash = strL("'a\\\\'");
		assertThat(aBackslash.getValue(), equalTo("a\\"));
		assertThat(aBackslash.toString(), equalTo("'a\\\\'"));
	}

	@Test
	public void testAddRuleFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
		List<Statement> statements = parser.compile();
		Fact fact = (Fact) statements.get(0).getClause();
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArguments(), contains(
			str("a"),
			str("\n"),
			str("\\n")));
	}

}
