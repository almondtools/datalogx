package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.CompilerTests.createParserFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.AnonymousLiteral;
import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.DatalogXStatementCompiler;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Query;
import net.amygdalum.datalogx.SetBuilder;
import net.amygdalum.datalogx.Statement;

public class SetBuilderTest {

	@Test
	public void testSetBuilder() throws Exception {
		DatalogXParser parser = createParserFor("(X,Y) <- p(X,Y)");
		Query query = (Query) parser.query().accept(new DatalogXStatementCompiler());
		SetBuilder a = new SetBuilder(query);
		assertThat(a.toString(), equalTo("{(X,Y) <- p(X,Y)}"));
	}

	@Test
	public void tesSetBuilderFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/nonconstants.dl");
		List<Statement> statements = parser.compile();
		Query fact = (Query) statements.get(1);
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArgument(1), equalTo(new SetBuilder(new Query(
			new AnonymousLiteral(var("a")),
			new PositiveLiteral("p", var("a"), str("b"))))));
	}
}
