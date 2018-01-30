package net.amygdalum.datalogx;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import net.amygdalum.datalogx.DatalogXParser.ProgramContext;

public class DatalogXCompiler extends DatalogXStatementCompiler {

	private DatalogXLexer lexer;
	private DatalogXParser parser;


	public DatalogXCompiler(String code) {
		this(CharStreams.fromString(code));
	}

	public DatalogXCompiler(File file) throws IOException {
		this(CharStreams.fromFileName(file.getPath()));
	}

	public DatalogXCompiler(CharStream input) {
		lexer = new DatalogXLexer(input);
		parser = new DatalogXParser(new CommonTokenStream(lexer));
	}

	public List<Statement> compile() {
		ProgramContext program = parser.program();
		Statements statements = (Statements) program.accept(this);
		return statements.getStatements();
	}

}
