package net.amygdalum.datalogx;

import org.antlr.v4.runtime.ParserRuleContext;

public interface ASTNodeValidator {

	void validate(ASTNode node, ParserRuleContext ctx);

}
