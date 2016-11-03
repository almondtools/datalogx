package net.amygdalum.datalogx;

public interface Term extends ASTNode {

	<T> T apply(TermVisitor<T> visitor);
	
}
