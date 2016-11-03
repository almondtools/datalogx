package net.amygdalum.datalogx;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static net.amygdalum.datalogx.BooleanConstant.boolL;
import static net.amygdalum.datalogx.DecimalConstant.decL;
import static net.amygdalum.datalogx.IntegerConstant.intgL;
import static net.amygdalum.datalogx.ListConstant.list;
import static net.amygdalum.datalogx.SetConstant.set;
import static net.amygdalum.datalogx.StringConstant.strL;
import static net.amygdalum.datalogx.Variable.var;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;

import net.amygdalum.datalogx.DatalogXParser.AddContext;
import net.amygdalum.datalogx.DatalogXParser.AggregateContext;
import net.amygdalum.datalogx.DatalogXParser.AnonymousliteralContext;
import net.amygdalum.datalogx.DatalogXParser.BodyContext;
import net.amygdalum.datalogx.DatalogXParser.BooleanContext;
import net.amygdalum.datalogx.DatalogXParser.ConjunctionContext;
import net.amygdalum.datalogx.DatalogXParser.DecimalContext;
import net.amygdalum.datalogx.DatalogXParser.DisjunctionContext;
import net.amygdalum.datalogx.DatalogXParser.EdbfactContext;
import net.amygdalum.datalogx.DatalogXParser.FunctionContext;
import net.amygdalum.datalogx.DatalogXParser.HeadContext;
import net.amygdalum.datalogx.DatalogXParser.IdbruleContext;
import net.amygdalum.datalogx.DatalogXParser.IntegerContext;
import net.amygdalum.datalogx.DatalogXParser.LiteralContext;
import net.amygdalum.datalogx.DatalogXParser.NameContext;
import net.amygdalum.datalogx.DatalogXParser.NamespaceContext;
import net.amygdalum.datalogx.DatalogXParser.NegativeliteralContext;
import net.amygdalum.datalogx.DatalogXParser.OperatorliteralContext;
import net.amygdalum.datalogx.DatalogXParser.PositiveliteralContext;
import net.amygdalum.datalogx.DatalogXParser.ProgramContext;
import net.amygdalum.datalogx.DatalogXParser.QueryContext;
import net.amygdalum.datalogx.DatalogXParser.QueryheadContext;
import net.amygdalum.datalogx.DatalogXParser.RemoveContext;
import net.amygdalum.datalogx.DatalogXParser.RequestContext;
import net.amygdalum.datalogx.DatalogXParser.SetContext;
import net.amygdalum.datalogx.DatalogXParser.StatementContext;
import net.amygdalum.datalogx.DatalogXParser.StringContext;
import net.amygdalum.datalogx.DatalogXParser.TermContext;
import net.amygdalum.datalogx.DatalogXParser.ValuelistContext;
import net.amygdalum.datalogx.DatalogXParser.ValuesetContext;
import net.amygdalum.datalogx.DatalogXParser.VariableContext;
import net.amygdalum.datalogx.DatalogXParser.WildcardContext;

public class DatalogXStatementCompiler extends DatalogXBaseVisitor<ASTNode> {

	private String namespace;
	private Map<Class<? extends ParserRuleContext>, ASTNodeValidator> assertions;

	public DatalogXStatementCompiler() {
		this.assertions = new LinkedHashMap<>();
	}

	@Override
	public ASTNode visitProgram(ProgramContext ctx) {
		Statements statements = new Statements();
		for (NamespaceContext namespaceNode : ctx.namespace()) {
			Statements statementsForScope = (Statements) namespaceNode.accept(this);
			statements.append(statementsForScope);
		}
		return statements;
	}

	@Override
	public ASTNode visitNamespace(NamespaceContext ctx) {
		NameContext nameSpaceNode = ctx.name();
		if (nameSpaceNode != null) {
			namespace = nameSpaceNode.getText();
		}
		Statements statements = new Statements();
		for (StatementContext statementNode : ctx.statement()) {
			Statement stmt = (Statement) statementNode.accept(this);
			statements.append(stmt);
		}
		return statements;
	}

	@Override
	public ASTNode visitAdd(AddContext ctx) {
		IdbruleContext rule = ctx.idbrule();
		EdbfactContext fact = ctx.edbfact();
		if (rule != null) {
			return validate(new Assert((Rule) rule.accept(this)), ctx);
		} else if (fact != null) {
			return validate(new Assert((Fact) fact.accept(this)), ctx);
		} else {
			throw new SemanticAnalysisException("expecting fact or rule", ctx);
		}
	}

	@Override
	public ASTNode visitRemove(RemoveContext ctx) {
		IdbruleContext rule = ctx.idbrule();
		EdbfactContext fact = ctx.edbfact();
		if (rule != null) {
			return validate(new Retract((Rule) rule.accept(this)), ctx);
		} else if (fact != null) {
			return validate(new Retract((Fact) fact.accept(this)), ctx);
		} else {
			throw new SemanticAnalysisException("expecting fact or rule", ctx);
		}
	}

	@Override
	public ASTNode visitRequest(RequestContext ctx) {
		return validate(ctx.query().accept(this), ctx);
	}

	@Override
	public ASTNode visitQuery(QueryContext ctx) {
		AtomicFormula head = (AtomicFormula) ctx.queryhead().accept(this);
		Formula body = (Formula) ctx.body().accept(this);
		return validate(new Query(head, body), ctx);
	}

	@Override
	public ASTNode visitIdbrule(IdbruleContext ctx) {
		FlatFormula head = (FlatFormula) ctx.head().accept(this);
		Formula body = (Formula) ctx.body().accept(this);
		return validate(new Rule(head, body), ctx);
	}

	@Override
	public ASTNode visitEdbfact(EdbfactContext ctx) {
		assertThat(HeadContext.class, Fact.isFactBody());
		assertThat(TermContext.class, Fact.isConstant());
		return validate(new Fact((FlatFormula) ctx.head().accept(this)), ctx);
	}

	@Override
	public ASTNode visitQueryhead(QueryheadContext ctx) {
		return validate(ctx.anonymousliteral().accept(this), ctx);
	}
	
	@Override
	public ASTNode visitHead(HeadContext ctx) {
		ASTNode node = super.visitHead(ctx);
		return validate(node, ctx);
	}

	@Override
	public ASTNode visitBody(BodyContext ctx) {
		return validate(ctx.disjunction().accept(this), ctx);
	}

	@Override
	public ASTNode visitDisjunction(DisjunctionContext ctx) {
		List<ConjunctionContext> disjunctiveNodes = ctx.conjunction();
		if (disjunctiveNodes.size() == 1) {
			return validate(disjunctiveNodes.get(0).accept(this), ctx);
		} else {
			final Disjunction disjunction = new Disjunction();
			for (ConjunctionContext disjunctiveNode : disjunctiveNodes) {
				Formula formula = (Formula) disjunctiveNode.accept(this);
				disjunction.or(formula);
			}
			return validate(disjunction, ctx);
		}
	}

	@Override
	public ASTNode visitConjunction(ConjunctionContext ctx) {
		List<LiteralContext> conjunctiveNodes = ctx.literal();
		if (conjunctiveNodes.size() == 1) {
			return validate(conjunctiveNodes.get(0).accept(this), ctx);
		} else {
			Conjunction conjunction = new Conjunction();
			for (LiteralContext conjunctiveNode : conjunctiveNodes) {
				conjunction.and((AtomicFormula) conjunctiveNode.accept(this));
			}
			return validate(conjunction, ctx);
		}
	}

	@Override
	public ASTNode visitPositiveliteral(PositiveliteralContext ctx) {
		String predicateName = resolve(ctx.localname().getText());
		PositiveLiteral literal = new PositiveLiteral(predicateName);
		assertThat(WildcardContext.class, PositiveLiteral.hasValidOperands());
		for (TermContext argNode : ctx.terms().term()) {
			literal.addArgument((Term) argNode.accept(this));
		}
		return validate(literal, ctx);
	}

	@Override
	public ASTNode visitNegativeliteral(NegativeliteralContext ctx) {
		String predicateName = resolve(ctx.localname().getText());
		NegativeLiteral literal = new NegativeLiteral(predicateName);
		assertThat(WildcardContext.class, NegativeLiteral.hasValidOperands());
		for (TermContext argNode : ctx.terms().term()) {
			literal.addArgument((Term) argNode.accept(this));
		}
		return validate(literal, ctx);
	}

	private String resolve(String localname) {
		if (localname.charAt(0) == '\\') {
			return localname.substring(1);
		} else if (namespace == null) {
			return localname;
		} else {
			return namespace + '.' + localname;
		}
	}

	@Override
	public ASTNode visitOperatorliteral(OperatorliteralContext ctx) {
		String operatorName = ctx.Operator().getText();
		Term leftoperand = (Term) ctx.term(0).accept(this);
		Term rightoperand = (Term) ctx.term(1).accept(this);
		assertThat(OperatorliteralContext.class, OperatorLiteral.hasValidOperands());
		return validate(new OperatorLiteral(operatorName, leftoperand, rightoperand), ctx);
	}

	@Override
	public ASTNode visitAnonymousliteral(AnonymousliteralContext ctx) {
		AnonymousLiteral literal = new AnonymousLiteral();
		for (TermContext argNode : ctx.terms().term()) {
			literal.addArgument((Term) argNode.accept(this));
		}
		return validate(literal, ctx);
	}

	@Override
	public ASTNode visitString(StringContext ctx) {
		return validate(strL(ctx.StringLiteral().getText()), ctx);
	}

	@Override
	public ASTNode visitInteger(IntegerContext ctx) {
		return validate(intgL(ctx.IntegerLiteral().getText()), ctx);
	}

	@Override
	public ASTNode visitDecimal(DecimalContext ctx) {
		return validate(decL(ctx.DecimalLiteral().getText()), ctx);
	}

	@Override
	public ASTNode visitBoolean(BooleanContext ctx) {
		return validate(boolL(ctx.BooleanLiteral().getText()), ctx);
	}

	@Override
	public ASTNode visitVariable(VariableContext ctx) {
		return validate(var(ctx.Identifier().getText()), ctx);
	}

	@Override
	public ASTNode visitWildcard(WildcardContext ctx) {
		return validate(var(ctx.Wildcard().getText()), ctx);
	}

	@Override
	public ASTNode visitValueset(ValuesetContext ctx) {
		Set<Constant> values = ctx.terms().term().stream()
			.map(term -> term.accept(this))
			.filter(node -> node instanceof Constant)
			.map(node -> (Constant) node)
			.collect(toSet());
		return validate(set(values), ctx);
	}

	@Override
	public ASTNode visitValuelist(ValuelistContext ctx) {
		List<Constant> values = ctx.terms().term().stream()
			.map(term -> term.accept(this))
			.filter(node -> node instanceof Constant)
			.map(node -> (Constant) node)
			.collect(toList());
		return validate(list(values), ctx);
	}
	
	@Override
	public ASTNode visitSet(SetContext ctx) {
		Query query = (Query) ctx.query().accept(this);
		return validate(new SetBuilder(query), ctx);
	}

	@Override
	public ASTNode visitFunction(FunctionContext ctx) {
		String functionName = resolve(ctx.localname().getText());
		Function function = new Function(functionName);
		for (TermContext argNode : ctx.terms().term()) {
			function.addArgument((Term) argNode.accept(this));
		}
		return validate(function, ctx);
	}

	@Override
	public ASTNode visitAggregate(AggregateContext ctx) {
		String functionName = resolve(ctx.localname().getText());
		Query query = (Query) ctx.query().accept(this);
		return validate(new AggregateBuilder(functionName, query), ctx);
	}

	public void assertThat(Class<? extends ParserRuleContext> clazz, ASTNodeValidator validator) {
		assertions.put(clazz, validator);
	}

	private ASTNode validate(ASTNode node, ParserRuleContext ctx) {
		ASTNodeValidator validator = assertions.get(ctx.getClass());
		if (validator == null) {
			return node;
		} else {
			validator.validate(node, ctx);
			return node;
		}
	}

}
