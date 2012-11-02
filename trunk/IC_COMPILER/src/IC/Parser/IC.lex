package IC.Parser;

%%

%class Lexer
%public
%function next_token
%type Token
%line
%scanerror LexicalError

WHITESPACE=[" "\n\t\r]
NONNEWLINE_WHITESPACE=[" "\t]
ALPHA=[a-zA-Z_]
UPPER=[A-Z]
LOWER=[a-z]
DIGIT=[0-9]
NONZERO=[1-9]
STRING_TEXT=([\x20-\x21\x23-\x5b\x5d-\x7e]|\\[\\nt\"])*
COMMENT_TEXT=([^\*]|\*[^/])*\*?

%%

<YYINITIAL>	class	{ return new Token(sym.CLASS,yyline,yytext()); }
<YYINITIAL>	extends	{ return new Token(sym.EXTENDS,yyline,yytext()); }
<YYINITIAL>	static	{ return new Token(sym.STATIC,yyline,yytext()); }
<YYINITIAL>	void	{ return new Token(sym.VOID,yyline,yytext()); }
<YYINITIAL>	int	{ return new Token(sym.INT,yyline,yytext()); }
<YYINITIAL>	boolean	{ return new Token(sym.BOOL,yyline,yytext()); }
<YYINITIAL>	string	{ return new Token(sym.STR,yyline,yytext()); }
<YYINITIAL>	return	{ return new Token(sym.RET,yyline,yytext()); }
<YYINITIAL>	if	{ return new Token(sym.IF,yyline,yytext()); }
<YYINITIAL>	else	{ return new Token(sym.ELSE,yyline,yytext()); }
<YYINITIAL>	while	{ return new Token(sym.WHILE,yyline,yytext()); }
<YYINITIAL>	break	{ return new Token(sym.BREAK,yyline,yytext()); }
<YYINITIAL>	continue	{ return new Token(sym.CONTINUE,yyline,yytext()); }
<YYINITIAL>	this	{ return new Token(sym.THIS,yyline,yytext()); }
<YYINITIAL>	new	{ return new Token(sym.NEW,yyline,yytext()); }
<YYINITIAL>	length	{ return new Token(sym.LENGTH,yyline,yytext()); }
<YYINITIAL>	true	{ return new Token(sym.TRUE,yyline,yytext()); }
<YYINITIAL>	false	{ return new Token(sym.FALSE,yyline,yytext()); }
<YYINITIAL>	null	{ return new Token(sym.NULL,yyline,yytext()); }
<YYINITIAL>	{UPPER}{ALPHA|DIGIT}*	{ return new Token(sym.CLASSID,yyline,yytext()); }
<YYINITIAL>	{LOWER}{ALPHA|DIGIT}*	{ return new Token(sym.ID,yyline,yytext()); }
<YYINITIAL>	0|(-?{NONZERO}{DIGIT}*)	{ return new Token(sym.INT,yyline,yytext()); }
<YYINITIAL>	{WHITESPACE}	{ }
<YYINITIAL>	"("	{ return new Token(sym.LP,yyline,yytext()); }
<YYINITIAL>	")"	{ return new Token(sym.RP,yyline,yytext()); }
<YYINITIAL>	"{"	{ return new Token(sym.L_CURLY,yyline,yytext()); }
<YYINITIAL>	"}"	{ return new Token(sym.R_CURLY,yyline,yytext()); }
<YYINITIAL>	"/*"	{ return new Token(sym.L_COM,yyline,yytext()); }
<YYINITIAL>	"*/"	{ return new Token(sym.R_COM,yyline,yytext()); }
<YYINITIAL>	"["	{ return new Token(sym.L_BRACK,yyline,yytext()); }
<YYINITIAL>	"]"	{ return new Token(sym.R_BRACK,yyline,yytext()); }
<YYINITIAL>	\"	{ return new Token(sym.DOUBLEQU,yyline,yytext()); }
<YYINITIAL>	","	{ return new Token(sym.COMMA,yyline,yytext()); }
<YYINITIAL>	//.*	{ return new Token(sym.COMMENT,yyline,yytext()); }
<YYINITIAL>	"."	{ return new Token(sym.DOT,yyline,yytext()); }
<YYINITIAL>	";"	{ return new Token(sym.SEMI,yyline,yytext()); }
<YYINITIAL>	\"{STRING_TEXT}\"	{ return new Token(sym.STRING,yyline,yytext()); }
<YYINITIAL>	\"{STRING_TEXT}	{ return new Token(sym.STRING_ERROR,yyline,yytext()); }
<YYINITIAL>	/\*{COMMENT_TEXT}\*/	{ return new Token(sym.COMMENT,yyline,yytext()); }
<YYINITIAL>	/\*{COMMENT_TEXT}	{ return new Token(sym.COMMENT_ERROR,yyline,yytext()); }
<YYINITIAL>	"="	{ return new Token(sym.ASSIGN,yyline,yytext()); }
<YYINITIAL>	"=="	{ return new Token(sym.EQ,yyline,yytext()); }
<YYINITIAL>	">"	{ return new Token(sym.BIGGER,yyline,yytext()); }
<YYINITIAL>	"<"	{ return new Token(sym.SMALLER,yyline,yytext()); }
<YYINITIAL>	">="	{ return new Token(sym.BIGEQ,yyline,yytext()); }
<YYINITIAL>	"<="	{ return new Token(sym.SMALLEQ,yyline,yytext()); }
<YYINITIAL>	"!="	{ return new Token(sym.NOTEQ,yyline,yytext()); }
<YYINITIAL>	"&&"	{ return new Token(sym.AND,yyline,yytext()); }
<YYINITIAL>	"||"	{ return new Token(sym.OR,yyline,yytext()); }
<YYINITIAL>	"!"	{ return new Token(sym.NOT,yyline,yytext()); }
<YYINITIAL>	"+"	{ return new Token(sym.ADD,yyline,yytext()); }
<YYINITIAL>	"-"	{ return new Token(sym.SUBTRACT,yyline,yytext()); }
<YYINITIAL>	"*"	{ return new Token(sym.MULT,yyline,yytext()); }
<YYINITIAL>	"/"	{ return new Token(sym.DIV,yyline,yytext()); }
<YYINITIAL>	"%"	{ return new Token(sym.MOD,yyline,yytext()); }
<YYINITIAL>	.	{ return new Token(sym.ILLEGAL_CHAR,yyline,yytext()); }
