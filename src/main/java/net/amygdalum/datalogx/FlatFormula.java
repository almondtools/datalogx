package net.amygdalum.datalogx;

import java.util.List;

public interface FlatFormula extends Formula {

	List<AtomicFormula> getAtoms();

}
