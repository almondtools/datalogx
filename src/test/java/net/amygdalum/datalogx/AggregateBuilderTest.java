package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.Variable.var;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.AggregateBuilder;
import net.amygdalum.datalogx.AnonymousLiteral;
import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.OperatorLiteral;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Query;
import net.amygdalum.datalogx.Statement;

public class AggregateBuilderTest {

	@Test
	public void testFunction() throws Exception {
		AggregateBuilder a = new AggregateBuilder("f", new Query(
			new AnonymousLiteral(var("a")),
			new PositiveLiteral("p", var("a"), str("b"))));
		assertThat(a.toString(), equalTo("f{(a) : p(a,'b')}"));
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
