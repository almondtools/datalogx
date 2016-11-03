package net.amygdalum.datalogx;


public class AggregateBuilder implements Term {

	private String name;
	private Query query;
	
	public AggregateBuilder(String name, Query query) {
		this.name = name;
		this.query = query;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitAggregateBuilder(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder(name)
			.append('{')
			.append(query.toString())
			.append('}')
			.toString();
	}

	@Override
	public int hashCode() {
		return 19 + (name.hashCode() * 3 + query.hashCode()) * 13;
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
		AggregateBuilder that = (AggregateBuilder) obj;
		return this.name.equals(that.name) && this.query.equals(that.query);
	}
	
}
