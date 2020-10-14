package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class VariableTest {

	@Test
	public void testVariable() throws Exception {
		Variable a = var("a");
		assertThat(a.getName(), equalTo("a"));
		assertThat(a.toString(), equalTo("a"));
	}

	@Test
	public void testAddRuleFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/nonconstants.dl");
		List<Statement> statements = parser.compile();
		Query query = (Query) statements.get(0);
		PositiveLiteral body = (PositiveLiteral) query.getBody();
		assertThat(body.getArguments(), contains(
			var("a"),
			var("b"),
			var("c")));
	}

}
