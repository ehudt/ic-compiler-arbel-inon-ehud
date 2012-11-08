package IC.Parser;

%%

%cup
%class Lexer
%public
%function next_token
%type Token
%line
%scanerror LexicalError

%eofval{
    return new Token(sym.EOF, yyline);
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
class	{ return new Token(sym.CLASS,yyline); }
extends	{ return new Token(sym.EXTENDS,yyline); }
static	{ return new Token(sym.STATIC,yyline); }
void	{ return new Token(sym.VOID,yyline); }
int	{ return new Token(sym.INT,yyline); }
boolean	{ return new Token(sym.BOOLEAN,yyline); }
string	{ return new Token(sym.STRING,yyline); }
return	{ return new Token(sym.RETURN,yyline); }
if	{ return new Token(sym.IF,yyline); }
else	{ return new Token(sym.ELSE,yyline); }
while	{ return new Token(sym.WHILE,yyline); }
break	{ return new Token(sym.BREAK,yyline); }
continue	{ return new Token(sym.CONTINUE,yyline); }
this	{ return new Token(sym.THIS,yyline); }
new	{ return new Token(sym.NEW,yyline); }
length	{ return new Token(sym.LENGTH,yyline); }
true	{ return new Token(sym.TRUE,yyline); }
false	{ return new Token(sym.FALSE,yyline); }
null	{ return new Token(sym.NULL,yyline); }

// rules for identifier IDs
{CLASS_ID}*	{ return new Token(sym.CLASS_ID,yyline,yytext()); }
{ID}*	{ return new Token(sym.ID,yyline,yytext()); }

// rules for numbers: illegal numbers and afterwards legal numbers
0+{DIGIT}+				{ throw new LexicalError(yytext(), yyline, "Error: Illegal token: " + yytext() + " in line " + yyline + "."); }
0|({NONZERO}{DIGIT}*)	{ return new Token(sym.INTEGER,yyline,yytext()); }

// rules for parentheses and punctuation
"("	{ return new Token(sym.LP,yyline); }
")"	{ return new Token(sym.RP,yyline); }
"{"	{ return new Token(sym.LCBR,yyline); }
"}"	{ return new Token(sym.RCBR,yyline); }
"["	{ return new Token(sym.LB,yyline); }
"]"	{ return new Token(sym.RB,yyline); }
","	{ return new Token(sym.COMMA,yyline); }
"."	{ return new Token(sym.DOT,yyline); }
";"	{ return new Token(sym.SEMI,yyline); }

// rule for strings
\"{STRING_TEXT}\"	{ return new Token(sym.QUOTE,yyline,yytext()); }
// rules for comments: single-line comment followed by multi-line comment
"//".*	{ }
"/*"{COMMENT_TEXT}"*/"	{ }

// rules for operators (boolean and arithmetic)
"="	{ return new Token(sym.ASSIGN,yyline); }
"=="	{ return new Token(sym.EQUAL,yyline); }
">"	{ return new Token(sym.GT,yyline); }
"<"	{ return new Token(sym.LT,yyline); }
">="	{ return new Token(sym.GTE,yyline); }
"<="	{ return new Token(sym.LTE,yyline); }
"!="	{ return new Token(sym.NEQUAL,yyline); }
"&&"	{ return new Token(sym.LAND,yyline); }
"||"	{ return new Token(sym.LOR,yyline); }
"!"	{ return new Token(sym.LNEG,yyline); }
"+"	{ return new Token(sym.PLUS,yyline); }
"-"	{ return new Token(sym.MINUS,yyline); }
"*"	{ return new Token(sym.MULTIPLY,yyline); }
"/"	{ return new Token(sym.DIVIDE,yyline); }
"%"	{ return new Token(sym.MOD,yyline); }

// cleanup rule: reject all other tokens
.	{ throw new LexicalError(yytext(), yyline, "Error: Illegal token: " + yytext() + " in line " + yyline + "."); }
