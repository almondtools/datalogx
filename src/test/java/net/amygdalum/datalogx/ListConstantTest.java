package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.IntegerConstant.intg;
import static net.amygdalum.datalogx.StringConstant.str;
import static net.amygdalum.datalogx.TestHelper.bi;
import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXCompiler;
import net.amygdalum.datalogx.Fact;
import net.amygdalum.datalogx.ListConstant;
import net.amygdalum.datalogx.PositiveLiteral;
import net.amygdalum.datalogx.Statement;


public class ListConstantTest {

	@Test
	public void testListConstant() throws Exception {
		ListConstant a = ListConstant.list(asList(str("a"), intg(bi(2))));
		assertThat(a.toString(), equalTo("['a',2]"));
	}

	@Test
	public void testListConstantFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
		List<Statement> statements = parser.compile();
		Fact fact = (Fact) statements.get(6).getClause();
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArgument(1), equalTo(ListConstant.list(asList(str("a"), intg(bi(2))))));
	}

}