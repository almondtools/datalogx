package net.amygdalum.datalogx;

import org.antlr.v4.runtime.ParserRuleContext;

public class Fact implements Clause {

	private FlatFormula body;

	public Fact(FlatFormula body) {
		this.body = body;
	}

	public FlatFormula getBody() {
		return body;
	}

	public static ASTNodeValidator isFactBody() {
		return new ASTNodeValidator() {

			@Override
			public void validate(ASTNode node, ParserRuleContext ctx) {
				if (node instanceof Conjunction) {
				} else if (node instanceof PositiveLiteral) {
				} else if (node instanceof NegativeLiteral) {
				} else {
					throw new SemanticAnalysisException("expecting fact body to be a conjunction of positive or negative literals", ctx);
				}
			}

		};
	}

	public static ASTNodeValidator isConstant() {
		return new ASTNodeValidator() {

			@Override
			public void validate(ASTNode node, ParserRuleContext ctx) {
				if (node instanceof Constant) {
				} else if (node instanceof Function) {
				} else {
					throw new SemanticAnalysisException("expecting constant term", ctx);
				}
			}

		};
	}

	@Override
	public <T> T apply(ClauseVisitor<T> visitor) {
		return visitor.visitFact(this);
	}

	@Override
	public String toString() {
		return body.toString();
	}

	@Override
	public int hashCode() {
		return 17 + body.hashCode() * 41;
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
		Fact that = (Fact) obj;
		return this.body.equals(that.body);
	}

}
