package net.amygdalum.datalogx;

import java.util.ArrayList;
import java.util.List;

public class Statements implements ASTNode {

	private List<Statement> statements;
	
	public Statements() {
		this.statements = new ArrayList<Statement>();
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	public void append(Statements statements) {
		this.statements.addAll(statements.getStatements());
	}

	public void append(Statement statement) {
		this.statements.add(statement);
	}
	
}
