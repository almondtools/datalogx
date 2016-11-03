package net.amygdalum.datalogx;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import net.amygdalum.datalogx.DatalogXCompiler;

public class CompilerTests {

	public static DatalogXCompiler createCompiler(String file) throws IOException {
		return new DatalogXCompiler(new ANTLRFileStream(file));
	}
	
	public static DatalogXParser createParser(String file) throws IOException {
		DatalogXLexer lexer = new DatalogXLexer(new ANTLRFileStream(file));
		lexer.addErrorListener(new SmokeTestErrorListener());
		DatalogXParser parser = new DatalogXParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new SmokeTestErrorListener());
		return parser;
	}

	public static DatalogXParser createParserFor(String code) throws IOException {
		DatalogXLexer lexer = new DatalogXLexer(new ANTLRInputStream(code));
		lexer.addErrorListener(new SmokeTestErrorListener());
		DatalogXParser parser = new DatalogXParser(new CommonTokenStream(lexer));
		parser.addErrorListener(new SmokeTestErrorListener());
		return parser;
	}
}
