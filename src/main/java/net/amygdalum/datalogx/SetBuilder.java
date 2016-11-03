package net.amygdalum.datalogx;

public class SetBuilder implements Term {

	private Query query;

	public SetBuilder(Query query) {
		this.query = query;
	}

	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitSetBuilder(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
		.append('{')
		.append(query.toString())
		.append('}')
		.toString();
	}

	@Override
	public int hashCode() {
		return 13 + query.hashCode() * 29;
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
		SetBuilder that = (SetBuilder) obj;
		return this.query.equals(that.query);
	}

}
