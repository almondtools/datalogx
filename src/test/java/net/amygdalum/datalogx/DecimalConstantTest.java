package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static net.amygdalum.datalogx.DecimalConstant.dec;
import static net.amygdalum.datalogx.DecimalConstant.decL;
import static net.amygdalum.datalogx.TestHelper.bd;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class DecimalConstantTest {

	@Test
	public void testDecimalConstant() throws Exception {
		DecimalConstant a = decL("123.1");
		assertThat(a.getValue(), equalTo(bd(1231, 1)));
		assertThat(a.toString(), equalTo("123.1"));
	}

	@Test
	public void testDecimalFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
		List<Statement> statements = parser.compile();
		Fact fact = (Fact) statements.get(2).getClause();
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArguments(), contains(
			dec(bd(1231, 1)),
			dec(bd(21, 7)),
			dec(bd(136, -7).setScale(1))));
	}
}
