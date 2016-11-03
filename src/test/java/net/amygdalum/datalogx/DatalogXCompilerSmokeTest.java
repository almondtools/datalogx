package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.CompilerTests.createCompiler;

import org.junit.Test;

import net.amygdalum.datalogx.DatalogXCompiler;

public class DatalogXCompilerSmokeTest {

	@Test
	public void testRules() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/smoke/testRules.dl");
		parser.compile();
	}
	
	@Test
	public void testOperators() throws Exception {
		DatalogXCompiler parser = createCompiler("src/test/resources/smoke/testOperators.dl");
		parser.compile();
	}
	
}
