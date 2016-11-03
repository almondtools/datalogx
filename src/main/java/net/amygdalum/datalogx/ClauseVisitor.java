package net.amygdalum.datalogx;

public interface ClauseVisitor<T> {

	T visitQuery(Query node);
	T visitRule(Rule node);
	T visitFact(Fact node);

}
