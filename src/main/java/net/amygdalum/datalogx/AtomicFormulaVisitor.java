package net.amygdalum.datalogx;

public interface AtomicFormulaVisitor<T> {

	T visitPositiveLiteral(PositiveLiteral node);
	T visitNegativeLiteral(NegativeLiteral node);
	T visitOperatorLiteral(OperatorLiteral node);
	T visitAnonymousLiteral(AnonymousLiteral node);

}
