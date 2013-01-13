package IC.Parser;

%%

%cup
%class Lexer
%public
%function next_token
%type Token
%line
%column
%scanerror LexicalError

%{
	public int getCurrentLine() { return (yyline + 1); }
%}

%{
	public int getCurrentColumn() { return (yycolumn); }
%}

%eofval{
    return new Token(sym.EOF, getCurrentLine());
%eofval}

WHITESPACE=[" "\n\t\r]
ALPHA=[a-zA-Z_]
UPPER=[A-Z]
LOWER=[a-z]
DIGIT=[0-9]
NONZERO=[1-9]
ALPHA_NUMERIC={ALPHA}|{DIGIT}
ID={LOWER}{ALPHA_NUMERIC}*
CLASS_ID={UPPER}{ALPHA_NUMERIC}*
STRING_TEXT=([\x20-\x21\x23-\x5b\x5d-\x7e]|\\[\\nt\"])*
COMMENT_TEXT=([^\*]|\*[^/])*\*?

%%

// rule to ignore spaces
{WHITESPACE}	{ }

// rules for language keywords
class	{ return new Token(sym.CLASS,getCurrentLine()); }
extends	{ return new Token(sym.EXTENDS,getCurrentLine()); }
static	{ return new Token(sym.STATIC,getCurrentLine()); }
void	{ return new Token(sym.VOID,getCurrentLine()); }
int	{ return new Token(sym.INT,getCurrentLine()); }
boolean	{ return new Token(sym.BOOLEAN,getCurrentLine()); }
string	{ return new Token(sym.STRING,getCurrentLine()); }
return	{ return new Token(sym.RETURN,getCurrentLine()); }
if	{ return new Token(sym.IF,getCurrentLine()); }
else	{ return new Token(sym.ELSE,getCurrentLine()); }
while	{ return new Token(sym.WHILE,getCurrentLine()); }
break	{ return new Token(sym.BREAK,getCurrentLine()); }
continue	{ return new Token(sym.CONTINUE,getCurrentLine()); }
this	{ return new Token(sym.THIS,getCurrentLine()); }
new	{ return new Token(sym.NEW,getCurrentLine()); }
length	{ return new Token(sym.LENGTH,getCurrentLine()); }
true	{ return new Token(sym.TRUE,getCurrentLine()); }
false	{ return new Token(sym.FALSE,getCurrentLine()); }
null	{ return new Token(sym.NULL,getCurrentLine()); }

// rules for identifier IDs
{CLASS_ID}	{ return new Token(sym.CLASS_ID,getCurrentLine(),yytext()); }
{ID}	{ return new Token(sym.ID, getCurrentLine(), getCurrentColumn(), yytext()); }

// rules for numbers: illegal numbers and afterwards legal numbers
0+{NONZERO}{DIGIT}*				{ throw new LexicalError(yytext(), getCurrentLine(), getCurrentLine() + ": Lexical error: illegal integer format in token '" + yytext() + "'"); }
0+|({NONZERO}{DIGIT}*)	{ return new Token(sym.INTEGER,getCurrentLine(),yytext()); }

// rules for parentheses and punctuation
"("	{ return new Token(sym.LP,getCurrentLine()); }
")"	{ return new Token(sym.RP,getCurrentLine()); }
"{"	{ return new Token(sym.LCBR,getCurrentLine()); }
"}"	{ return new Token(sym.RCBR,getCurrentLine()); }
"["	{ return new Token(sym.LB,getCurrentLine()); }
"]"	{ return new Token(sym.RB,getCurrentLine()); }
","	{ return new Token(sym.COMMA,getCurrentLine()); }
"."	{ return new Token(sym.DOT,getCurrentLine()); }
";"	{ return new Token(sym.SEMI,getCurrentLine()); }

// rules for strings and comments
\"{STRING_TEXT}\"	{ return new Token(sym.QUOTE,getCurrentLine(),yytext()); }
\"					{ throw new LexicalError(yytext(), getCurrentLine(), getCurrentLine() + ": Lexical error: illegal string literal"); }
"//".*	{ }
"/*"{COMMENT_TEXT}"*/"	{ }
"/*" 				{ throw new LexicalError(yytext(), getCurrentLine(), getCurrentLine() + ": Lexical error: Unclosed multi-line comment"); }

// rules for operators: boolean and arithmetic
"="	{ return new Token(sym.ASSIGN,getCurrentLine()); }
"=="	{ return new Token(sym.EQUAL,getCurrentLine()); }
">"	{ return new Token(sym.GT,getCurrentLine()); }
"<"	{ return new Token(sym.LT,getCurrentLine()); }
">="	{ return new Token(sym.GTE,getCurrentLine()); }
"<="	{ return new Token(sym.LTE,getCurrentLine()); }
"!="	{ return new Token(sym.NEQUAL,getCurrentLine()); }
"&&"	{ return new Token(sym.LAND,getCurrentLine()); }
"||"	{ return new Token(sym.LOR,getCurrentLine()); }
"!"	{ return new Token(sym.LNEG,getCurrentLine()); }
"+"	{ return new Token(sym.PLUS,getCurrentLine()); }
"-"	{ return new Token(sym.MINUS,getCurrentLine()); }
"*"	{ return new Token(sym.MULTIPLY,getCurrentLine()); }
"/"	{ return new Token(sym.DIVIDE,getCurrentLine()); }
"%"	{ return new Token(sym.MOD,getCurrentLine()); }

// cleanup rule: reject all other tokens
.	{ throw new LexicalError(yytext(), getCurrentLine(), getCurrentLine() + ": Lexical error: illegal character '" + yytext() + "'"); }
