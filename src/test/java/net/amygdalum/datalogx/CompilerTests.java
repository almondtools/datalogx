package net.amygdalum.datalogx;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CompilerTests {

	public static DatalogXCompiler createCompiler(String file) throws IOException {
		return new DatalogXCompiler(CharStreams.fromFileName(file));
	}
	
	public static DatalogXParser createParser(String file) throws IOException {
		DatalogXLexer lexer = new DatalogXLexer(CharStreams.fromFileName(file));
		lexer.addErrorListener(new SmokeTestErrorListener());
		DatalogXParser parser = new DatalogXParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new SmokeTestErrorListener());
		return parser;
	}

	public static DatalogXParser createParserFor(String code) throws IOException {
		DatalogXLexer lexer = new DatalogXLexer(CharStreams.fromString(code));
		lexer.addErrorListener(new SmokeTestErrorListener());
		DatalogXParser parser = new DatalogXParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new SmokeTestErrorListener());
		return parser;
	}
}
