package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Query;
import net.amygdalum.datalogx.Statement;
import net.amygdalum.datalogx.Variable;


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
