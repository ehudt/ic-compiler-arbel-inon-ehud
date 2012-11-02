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

class	{ return new Token(sym.CLASS,yyline,yytext()); }
extends	{ return new Token(sym.EXTENDS,yyline,yytext()); }
static	{ return new Token(sym.STATIC,yyline,yytext()); }
void	{ return new Token(sym.VOID,yyline,yytext()); }
int	{ return new Token(sym.INT,yyline,yytext()); }
boolean	{ return new Token(sym.BOOLEAN,yyline,yytext()); }
string	{ return new Token(sym.STRING,yyline,yytext()); }
return	{ return new Token(sym.RETURN,yyline,yytext()); }
if	{ return new Token(sym.IF,yyline,yytext()); }
else	{ return new Token(sym.ELSE,yyline,yytext()); }
while	{ return new Token(sym.WHILE,yyline,yytext()); }
break	{ return new Token(sym.BREAK,yyline,yytext()); }
continue	{ return new Token(sym.CONTINUE,yyline,yytext()); }
this	{ return new Token(sym.THIS,yyline,yytext()); }
new	{ return new Token(sym.NEW,yyline,yytext()); }
length	{ return new Token(sym.LENGTH,yyline,yytext()); }
true	{ return new Token(sym.TRUE,yyline,yytext()); }
false	{ return new Token(sym.FALSE,yyline,yytext()); }
null	{ return new Token(sym.NULL,yyline,yytext()); }
{UPPER}{ALPHA|DIGIT}*	{ return new Token(sym.CLASS_ID,yyline,yytext()); }
{LOWER}{ALPHA|DIGIT}*	{ return new Token(sym.ID,yyline,yytext()); }
0|(-?{NONZERO}{DIGIT}*)	{ return new Token(sym.INTEGER,yyline,yytext()); }
{WHITESPACE}	{ }
"("	{ return new Token(sym.LP,yyline,yytext()); }
")"	{ return new Token(sym.RP,yyline,yytext()); }
"{"	{ return new Token(sym.LCBR,yyline,yytext()); }
"}"	{ return new Token(sym.RCBR,yyline,yytext()); }
"["	{ return new Token(sym.LB,yyline,yytext()); }
"]"	{ return new Token(sym.RB,yyline,yytext()); }
","	{ return new Token(sym.COMMA,yyline,yytext()); }
"."	{ return new Token(sym.DOT,yyline,yytext()); }
";"	{ return new Token(sym.SEMI,yyline,yytext()); }
\"{STRING_TEXT}\"	{ return new Token(sym.QUOTE,yyline,yytext()); }
//.*	{ }
/\*{COMMENT_TEXT}\*/	{ }
"="	{ return new Token(sym.ASSIGN,yyline,yytext()); }
"=="	{ return new Token(sym.EQUAL,yyline,yytext()); }
">"	{ return new Token(sym.GT,yyline,yytext()); }
"<"	{ return new Token(sym.LT,yyline,yytext()); }
">="	{ return new Token(sym.GTE,yyline,yytext()); }
"<="	{ return new Token(sym.LTE,yyline,yytext()); }
"!="	{ return new Token(sym.NEQUAL,yyline,yytext()); }
"&&"	{ return new Token(sym.LAND,yyline,yytext()); }
"||"	{ return new Token(sym.LOR,yyline,yytext()); }
"!"	{ return new Token(sym.LNEG,yyline,yytext()); }
"+"	{ return new Token(sym.PLUS,yyline,yytext()); }
"-"	{ return new Token(sym.MINUS,yyline,yytext()); }
"*"	{ return new Token(sym.MULTIPLY,yyline,yytext()); }
"/"	{ return new Token(sym.DIVIDE,yyline,yytext()); }
"%"	{ return new Token(sym.MOD,yyline,yytext()); }
.	{ }
