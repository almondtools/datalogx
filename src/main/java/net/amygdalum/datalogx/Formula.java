package net.amygdalum.datalogx;

import java.util.List;

public interface Formula extends ASTNode {

	List<FlatFormula> getClauses();
}
