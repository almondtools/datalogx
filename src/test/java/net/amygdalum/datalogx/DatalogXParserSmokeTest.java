package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createParser;

import org.junit.Test;

public class DatalogXParserSmokeTest {

	@Test
	public void testRules() throws Exception {
		DatalogXParser parser = createParser("src/test/resources/smoke/testRules.dl");
		parser.program();
	}
	
	@Test
	public void testOperators() throws Exception {
		DatalogXParser parser = createParser("src/test/resources/smoke/testOperators.dl");
		parser.program();
	}
	
}
