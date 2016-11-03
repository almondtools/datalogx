package net.amygdalum.datalogx;

import org.antlr.v4.runtime.ParserRuleContext;


public class OperatorLiteral extends Literal {

	public OperatorLiteral(String operator, Term leftoperand, Term rightoperand) {
		super(operator);
		addArgument(leftoperand);
		addArgument(rightoperand);
	}

	public static ASTNodeValidator hasValidOperands() {
		return new ASTNodeValidator() {
			
			@Override
			public void validate(ASTNode node, ParserRuleContext ctx) {
				if (node instanceof OperatorLiteral) {
					OperatorLiteral op = (OperatorLiteral) node;
					if (!(op.getArgument(0) instanceof Variable || op.getArgument(1) instanceof Variable)) {
						throw new SemanticAnalysisException("operator literals should comply to \"<variable> op <expression>\" or \"<expression> op <variable>\"");
					}
				}
			}
		};
	}

	@Override
	public <T> T apply(AtomicFormulaVisitor<T> visitor) {
		return visitor.visitOperatorLiteral(this);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append(getArgument(0).toString())
			.append(' ')
			.append(getName())
			.append(' ')
			.append(getArgument(1).toString())
			.toString();
	}

}
