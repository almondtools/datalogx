package net.amygdalum.datalogx;

public interface Clause extends ASTNode {

	<T> T apply(ClauseVisitor<T> visitor);
	
}
