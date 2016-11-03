package net.amygdalum.datalogx;

public interface StatementVisitor<T> {

	T visitQueryStatement(Query node);
	T visitAssertStatement(Assert node);
	T visitRetractStatement(Retract node);

}
