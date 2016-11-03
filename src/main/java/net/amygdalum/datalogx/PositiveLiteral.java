package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.ALL_WILDCARD;

import org.antlr.v4.runtime.ParserRuleContext;

public class PositiveLiteral extends Literal {

	public PositiveLiteral(String name, Term... arguments) {
		super(name, arguments);
	}

	public static ASTNodeValidator hasValidOperands() {
		return new ASTNodeValidator() {

			@Override
			public void validate(ASTNode node, ParserRuleContext ctx) {
				if (node instanceof Variable) {
					Variable variable = (Variable) node;
					if (ALL_WILDCARD.equals(variable.getName())) {
						throw new SemanticAnalysisException("positive literals should not contain all quantified anonymous variables");
					}
				}
			}
		};
	}

	@Override
	public <T> T apply(AtomicFormulaVisitor<T> visitor) {
		return visitor.visitPositiveLiteral(this);
	}

	@Override
	public String toString() {
		return new StringBuilder(getName())
			.append('(')
			.append(argumentsToString())
			.append(')')
			.toString();
	}

	public static class Builder {

		private PositiveLiteral literal;

		public Builder(String name) {
			this.literal = new PositiveLiteral(name);
		}

		public PositiveLiteral build() {
			return literal;
		}

		public Builder withArgument(Term term) {
			literal.addArgument(term);
			return this;
		}

	}

}
