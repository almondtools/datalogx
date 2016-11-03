package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.IntegerConstant.intg;
import static net.amygdalum.datalogx.IntegerConstant.intgL;
import static net.amygdalum.datalogx.TestHelper.bi;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.Fact;
import net.amygdalum.datalogx.IntegerConstant;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Statement;


public class IntegerConstantTest {

	@Test
		public void testIntggerConstant() throws Exception {
			IntegerConstant a = intgL("123");
			assertThat(a.getValue(), equalTo(bi(123)));
			assertThat(a.toString(), equalTo("123"));
		}

	@Test
		public void testIntggerFromFile() throws Exception {
			DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
			List<Statement> statements = parser.compile();
			Fact fact = (Fact) statements.get(1).getClause();
			PositiveLiteral body = (PositiveLiteral) fact.getBody();
			assertThat(body.getArguments(), contains(
				intg(bi(123)),
				intg(BigInteger.valueOf(-1)),
				intg(bi(0))));
		}
}
