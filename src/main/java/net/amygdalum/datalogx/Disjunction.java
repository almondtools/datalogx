package net.amygdalum.datalogx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Disjunction implements CompoundFormula, ASTNode {

	private List<FlatFormula> elements;
	
	public Disjunction() {
		this.elements = new ArrayList<FlatFormula>();
	}
	
	public List<FlatFormula> getElements() {
		return elements;
	}
	
	public void or(Formula formula) {
		if (formula instanceof FlatFormula) {
			elements.add((FlatFormula) formula);
		} else if (formula instanceof Disjunction) {
			elements.addAll(((Disjunction) formula).getElements());
		} else {
			throw new SemanticAnalysisException("expecting literal, conjunction of literals or disjunction of conjunctions");
		}
	}
	
	@Override
	public List<FlatFormula> getClauses() {
		return new ArrayList<>(elements);
	}
	
	@Override
	public String toString() {
		Iterator<FlatFormula> eiterator = elements.iterator();
		if (!eiterator.hasNext()) {
			return "";
		} else {
			StringBuilder buffer = new StringBuilder(eiterator.next().toString());
			while (eiterator.hasNext()) {
				buffer.append(" | ").append(eiterator.next().toString());
			}
			return buffer.toString();
		}
	}

	@Override
	public int hashCode() {
		return 7 + elements.hashCode() * 37;
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
		Disjunction that = (Disjunction) obj;
		return this.elements.equals(that.elements);  
	}

	public static class Builder {

		private Disjunction disjunction;

		public Builder() {
			this.disjunction = new Disjunction();
		}
		
		public Builder or(Formula formula) {
			disjunction.or(formula);
			return this;
		}
		
		public Disjunction build() {
			return disjunction;
		}

	}

}
