package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;

import org.junit.Test;


public class BooleanConstantTest {

	@Test
	public void testBooleanConstant() throws Exception {
		BooleanConstant t = BooleanConstant.boolL("true");
		assertThat(t.getValue(), equalTo(true));
		assertThat(t.toString(), equalTo("true"));

		BooleanConstant f = BooleanConstant.boolL("false");
		assertThat(f.getValue(), equalTo(false));
		assertThat(f.toString(), equalTo("false"));
	}

	@Test
	public void testBooleanFromFile() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/feature/constants.dl");
		List<Statement> statements = parser.compile();
		Fact fact = (Fact) statements.get(3).getClause();
		PositiveLiteral body = (PositiveLiteral) fact.getBody();
		assertThat(body.getArguments(), contains(
			BooleanConstant.bool(true),
			BooleanConstant.bool(false)));
	}

}
