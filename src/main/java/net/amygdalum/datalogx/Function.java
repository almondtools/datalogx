package net.amygdalum.datalogx;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Function implements Term {

	private String name;
	private List<Term> arguments;
	
	public Function(String name) {
		this.name = name;
		this.arguments = new ArrayList<>();
	}
	
	public Function(String name, Term... arguments) {
		this.name = name;
		this.arguments = new ArrayList<>(asList(arguments));
	}
	
	public String getName() {
		return name;
	}
	
	public void addArgument(Term term) {
		arguments.add(term);
	}
	
	public List<Term> getArguments() {
		return arguments;
	}

	private String argumentsToString() {
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
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitFunction(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder(name)
			.append('(')
			.append(argumentsToString())
			.append(')')
			.toString();
	}

	@Override
	public int hashCode() {
		return 23 + (name.hashCode() * 3 + arguments.hashCode()) * 37;
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
		Function that = (Function) obj;
		return this.name.equals(that.name) && this.arguments.equals(that.arguments);
	}
	
}
