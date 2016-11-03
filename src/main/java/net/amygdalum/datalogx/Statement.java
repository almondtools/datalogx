package net.amygdalum.datalogx;

public interface Statement extends ASTNode {

	Clause getClause();

	<T> T apply(StatementVisitor<T> visitor);

}
