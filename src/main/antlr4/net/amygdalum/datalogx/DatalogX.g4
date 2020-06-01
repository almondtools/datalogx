grammar DatalogX;

program 
	: (namespace)+
	;

namespace 
	: name '{' (statement)+ '}'
	| (statement)+
	;

statement
	: add
	| remove
	| request
	;

add
	: '+'? (idbrule | edbfact) ';'
	;

remove
	: '-' (idbrule | edbfact) ';'
	;

request
	: '?'? query ';'
	;
	
idbrule
	: head '<-' body 
	;

edbfact
	: head
	;
	
query
	: queryhead '<-' body
	;
	
head
	: conjunction
	;

queryhead
	: anonymousliteral
	;

body
	: disjunction
	;

disjunction
	: conjunction ('|' conjunction)*
	;

conjunction
	: literal ('&' literal)*
	;

literal
	: positiveliteral
	| negativeliteral
	| operatorliteral
	;

negativeliteral
	: '~' localname '(' terms ')'
	;

positiveliteral
	: localname '(' terms ')'
	;

operatorliteral
	: term Operator term
	;

anonymousliteral
	: '(' terms ')'
	;

terms
	: term (',' term)*
	;

term
	: '{' terms '}' # valueset
	| '[' terms ']' # valuelist
	| '{' query '}' # set
	| localname '{' query '}' # aggregate
	| localname '(' terms ')' # function
	| IntegerLiteral # integer
	| DecimalLiteral # decimal
	| StringLiteral # string
	| BooleanLiteral # boolean
	| Identifier # variable
	| Wildcard # wildcard
	;

localname
	: '\\' name #absolutename
	| name #relativename
	;

name
	: Identifier ('.' Identifier)*
	;

Operator
	: '=='
	| '!='
	| '<'
	| '>'
	| '<='
	| '>='
	;

IntegerLiteral
	: '-'? (HEX_LITERAL
	| DEC_LITERAL
	| OCT_LITERAL)
	;

DecimalLiteral
    :   '-'? ('0'..'9')+ '.' ('0'..'9')+
	;

StringLiteral
    :  '\'' ( ESCAPE_SEQUENCE | . )*? '\''
    ;

BooleanLiteral
    : 'true'
    | 'false'
    ;

Identifier
    : LETTER (LETTER|DIGIT)*
    ;

Wildcard
    : ('_' | '*')
    ;

Whitespace
	: (' '|'\t'|'\n'|'\r') -> skip
	;

Comment
    :   '/*' .*? '*/' -> skip
    ;

LineComment
    : '//' ~('\n'|'\r')* ('\r'? '\n' | EOF) -> skip
    ;

fragment HEX_LITERAL
	: '0' ('x'|'X') HEX_DIGIT+
	;

fragment DEC_LITERAL
	: ('0' | '1'..'9' '0'..'9'*)
	;

fragment OCT_LITERAL
	: '0' ('0'..'7')+
	;

fragment ESCAPE_SEQUENCE
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESCAPE
    |   OCTAL_ESCAPE
    ;

fragment OCTAL_ESCAPE
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment UNICODE_ESCAPE
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

fragment EXPONENT
	: ('e'|'E') ('+'|'-')? ('0'..'9')+
	;

fragment LETTER
	: ('a'..'z'	| 'A'..'Z')
	;

fragment DIGIT
	: ('0'..'9')
	;

fragment HEX_DIGIT
	: ('0'..'9'|'a'..'f'|'A'..'F')
	;