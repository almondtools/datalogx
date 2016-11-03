package net.amygdalum.datalogx;

public interface AtomicFormula extends FlatFormula {

	<T> T apply(AtomicFormulaVisitor<T> visitor);
	
}
