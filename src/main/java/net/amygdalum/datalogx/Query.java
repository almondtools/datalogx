package net.amygdalum.datalogx;

public class Query implements Clause, Statement {

	private AtomicFormula head;
	private Formula body;

	public Query(AtomicFormula head, Formula body) {
		this.head = head;
		this.body = body;
	}

	public AtomicFormula getHead() {
		return head;
	}

	public Formula getBody() {
		return body;
	}

	@Override
	public Clause getClause() {
		return this;
	}

	@Override
	public <T> T apply(StatementVisitor<T> visitor) {
		return visitor.visitQueryStatement(this);
	}

	@Override
	public <T> T apply(ClauseVisitor<T> visitor) {
		return visitor.visitQuery(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append(head.toString())
			.append(" : ")
			.append(body.toString())
			.toString();
	}

	@Override
	public int hashCode() {
		return 17 + (head.hashCode() * 13 + body.hashCode()) * 3;
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
		Query that = (Query) obj;
		return this.head.equals(that.head) && this.body.equals(that.body);  
	}

}
