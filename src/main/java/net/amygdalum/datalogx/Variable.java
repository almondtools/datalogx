package net.amygdalum.datalogx;

public class Variable implements Term {

	public static final String ALL_WILDCARD = "*";
	public static final String EXIST_WILDCARD = "_";
	
	private String name;

	private Variable(String name) {
		this.name = name;
	}
	
	public static Variable var(String name) {
		return new Variable(name);
	}

	public String getName() {
		return name;
	}
	
	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitVariable(this);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return 7 * name.hashCode() * 13;
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
		Variable that = (Variable) obj;
		return this.name == null && that.name == null 
			|| this.name != null && that.name != null && this.name.equals(that.name);  
	}

}
