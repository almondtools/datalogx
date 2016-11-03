package net.amygdalum.datalogx;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Conjunction implements FlatFormula, CompoundFormula, ASTNode {

	private List<AtomicFormula> elements;
	
	public Conjunction() {
		this.elements = new ArrayList<AtomicFormula>();
	}
	
	public void and(AtomicFormula formula) {
		elements.add(formula);
	}
	
	@Override
	public List<AtomicFormula> getAtoms() {
		return new ArrayList<>(elements);
	}
	
	@Override
	public List<FlatFormula> getClauses() {
		return asList(this);
	}
	
	@Override
	public String toString() {
		Iterator<AtomicFormula> eiterator = elements.iterator();
		if (!eiterator.hasNext()) {
			return "";
		} else {
			StringBuilder buffer = new StringBuilder(eiterator.next().toString());
			while (eiterator.hasNext()) {
				buffer.append(" & ").append(eiterator.next().toString());
			}
			return buffer.toString();
		}
	}

	@Override
	public int hashCode() {
		return 11 + elements.hashCode() * 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Conjunction that = (Conjunction) obj;
		return this.elements.equals(that.elements);  
	}

	public static class Builder {

		private Conjunction conjunction;

		public Builder() {
			this.conjunction = new Conjunction();
		}
		
		public Builder and(AtomicFormula formula) {
			conjunction.and(formula);
			return this;
		}
		
		public Conjunction build() {
			return conjunction;
		}

	}

}
