package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.DecimalConstant.dec;
import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.TestHelper.bd;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;

public class FunctionTest {

	@Test
	public void testFunction() throws Exception {
		Function a = new Function("f");
		a.addArgument(str("b"));
		a.addArgument(dec(bd(3, 0).setScale(1)));
		assertThat(a.toString(), equalTo("f('b',3.0)"));
	}

	@Test
	public void testFunctionFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
		List<Statement> statements = parser.compile();
		Fact fact = (Fact) statements.get(4).getClause();
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArgument(0), equalTo(new Function("f", str("b"), dec(bd(3, 0).setScale(1)))));
	}

}
