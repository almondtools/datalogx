package net.amygdalum.datalogx;


public class Rule implements Clause {

	private FlatFormula head;
	private Formula body;

	public Rule(FlatFormula head, Formula body) {
		this.head = head;
		this.body = body;
	}

	public FlatFormula getHead() {
		return head;
	}

	public Formula getBody() {
		return body;
	}

	@Override
	public <T> T apply(ClauseVisitor<T> visitor) {
		return visitor.visitRule(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append(head.toString())
			.append(" <- ")
			.append(body.toString())
			.toString();
	}

	@Override
	public int hashCode() {
		return 13 + (head.hashCode() * 23 + body.hashCode()) * 7;
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
		Rule that = (Rule) obj;
		boolean headEquals = this.head.equals(that.head);
		boolean bodyEquals = this.body.equals(that.body);
		return headEquals && bodyEquals;
	}

}
