package net.amygdalum.datalogx;

import static net.amygdalum.datalogx.Variable.EXIST_WILDCARD;

import org.antlr.v4.runtime.ParserRuleContext;

public class NegativeLiteral extends Literal {

	public NegativeLiteral(String name, Term... arguments) {
		super(name, arguments);
	}

	public static ASTNodeValidator hasValidOperands() {
		return new ASTNodeValidator() {

			@Override
			public void validate(ASTNode node, ParserRuleContext ctx) {
				if (node instanceof Variable) {
					Variable variable = (Variable) node;
					if (EXIST_WILDCARD.equals(variable.getName())) {
						throw new SemanticAnalysisException("negative literals should not contain existential quantified anonymous variables");
					}
				}
			}
		};
	}

	@Override
	public <T> T apply(AtomicFormulaVisitor<T> visitor) {
		return visitor.visitNegativeLiteral(this);
	}

	@Override
	public String toString() {
		return new StringBuilder("~").append(getName())
			.append('(')
			.append(argumentsToString())
			.append(')')
			.toString();
	}
}
