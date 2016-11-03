package net.amygdalum.datalogx;


public class AnonymousLiteral extends Literal {

	public AnonymousLiteral(Term... arguments) {
		super(null, arguments);
	}
	
	@Override
	public <T> T apply(AtomicFormulaVisitor<T> visitor) {
		return visitor.visitAnonymousLiteral(this);
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append('(')
			.append(argumentsToString())
			.append(')')
			.toString();
	}

}
