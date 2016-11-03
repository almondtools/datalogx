package net.amygdalum.datalogx;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class SemanticAnalysisException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int line;
	private int pos;
	private String source;
	
	public SemanticAnalysisException(String msg) {
		super(msg);
	}

	public SemanticAnalysisException(String msg, ParserRuleContext ctx) {
		super(msg);
		updateContext(ctx);
	}
	
	@Override
	public String getMessage() {
		return line + "," + pos + " " + super.getMessage() + ":" + source;
	}

	public void updateContext(ParserRuleContext ctx) {
		Token start = ctx.getStart();
		if (start != null) {
			this.line = start.getLine();
			this.pos = start.getCharPositionInLine();
		}
		this.source = ctx.getText();
	}

}
