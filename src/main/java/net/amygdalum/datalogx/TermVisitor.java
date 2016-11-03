package net.amygdalum.datalogx;

public interface TermVisitor<T> {

	T visitBooleanConstant(BooleanConstant node);
	T visitDecimalConstant(DecimalConstant node);
	T visitIntegerConstant(IntegerConstant node);
	T visitStringConstant(StringConstant node);
	T visitFunction(Function node);
	T visitSetConstant(SetConstant node);
	T visitListConstant(ListConstant node);

	T visitVariable(Variable node);
	T visitSetBuilder(SetBuilder node);
	T visitAggregateBuilder(AggregateBuilder node);

}
