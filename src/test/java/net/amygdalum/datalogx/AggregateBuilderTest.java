package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.Variable.var;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;

public class AggregateBuilderTest {

	@Test
	public void testFunction() throws Exception {
		AggregateBuilder a = new AggregateBuilder("f", new Query(
			new AnonymousLiteral(var("a")),
			new PositiveLiteral("p", var("a"), str("b"))));
		assertThat(a.toString(), equalTo("f{(a) <- p(a,'b')}"));
	}

	@Test
	public void testAggregateFunctionFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/nonconstants.dl");
		List<Statement> statements = parser.compile();
		Query query = (Query) statements.get(2);
		OperatorLiteral body = (OperatorLiteral) query.getBody();
		assertThat(body.getArgument(0), equalTo(var("d")));
		assertThat(body.getArgument(1), equalTo(new AggregateBuilder("max", new Query(
			new AnonymousLiteral(var("a")),
			new PositiveLiteral("p", var("a"), str("b"))))));
	}

}
