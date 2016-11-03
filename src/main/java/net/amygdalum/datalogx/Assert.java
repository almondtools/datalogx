package net.amygdalum.datalogx;

public class Assert implements Statement {

	private Clause clause;

	public Assert(Clause clause) {
		this.clause = clause;
	}
	
	@Override
	public Clause getClause() {
		return clause;
	}
	
	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visitAssertStatement(this);
	}

	@Override
	public String toString() {
		return new StringBuilder("+ ").append(clause.toString()).toString();
	}

	@Override
	public int hashCode() {
		return 19 + clause.hashCode() * 7;
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
		Assert that = (Assert) obj;
		return this.clause.equals(that.clause);  
	}

}
