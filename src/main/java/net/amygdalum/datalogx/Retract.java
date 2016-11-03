package net.amygdalum.datalogx;

public class Retract implements Statement {

	private Clause clause;

	public Retract(Clause clause) {
		this.clause = clause;
	}
	
	@Override
	public Clause getClause() {
		return clause;
	}
	
	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visitRetractStatement(this);
	}

	@Override
	public String toString() {
		return new StringBuilder("- ").append(clause.toString()).toString();
	}

}
