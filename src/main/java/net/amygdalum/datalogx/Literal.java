package net.amygdalum.datalogx;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Literal implements AtomicFormula, ASTNode {

	private String name;
	private List<Term> arguments;

	public Literal(String name) {
		this.name = name;
		this.arguments = new ArrayList<Term>();
	}
	
	public Literal(String name, Term... arguments) {
		this.name = name;
		this.arguments = new ArrayList<Term>(asList(arguments));
	}
	
	public String getName() {
		return name;
	}

	public int getArity() {
		return arguments.size();
	}

	public void addArgument(Term term) {
		arguments.add(term);
	}
	
	public Term getArgument(int i) {
		return arguments.get(i);
	}
	
	public List<Term> getArguments() {
		return arguments;
	}
	
	@Override
	public List<AtomicFormula> getAtoms() {
		return asList(this);
	}

	@Override
	public List<FlatFormula> getClauses() {
		return asList(this);
	}
	
	protected String argumentsToString() {
		Iterator<Term> aiterator = arguments.iterator();
		if (!aiterator.hasNext()) {
			return "";
		} else {
			StringBuilder buffer = new StringBuilder(aiterator.next().toString());
			while (aiterator.hasNext()) {
				buffer.append(',').append(aiterator.next().toString());
			}
			return buffer.toString();
		}
	}

	@Override
	public int hashCode() {
		return 13 + (arguments.hashCode() * 19 + ((name == null) ? 0 : name.hashCode())) * 7;
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
		Literal that = (Literal) obj;
		boolean nameEquals = this.name == null && that.name == null 
			|| this.name != null && that.name != null && this.name.equals(that.name);
		boolean argumentsEquals = this.arguments.equals(that.arguments);
		return nameEquals && argumentsEquals;  
	}
	
}
