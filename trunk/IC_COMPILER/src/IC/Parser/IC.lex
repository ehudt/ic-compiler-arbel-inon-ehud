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
    return new Token(sym.EOF, (yyline + 1));
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
class	{ return new Token(sym.CLASS,(yyline + 1)); }
extends	{ return new Token(sym.EXTENDS,(yyline + 1)); }
static	{ return new Token(sym.STATIC,(yyline + 1)); }
void	{ return new Token(sym.VOID,(yyline + 1)); }
int	{ return new Token(sym.INT,(yyline + 1)); }
boolean	{ return new Token(sym.BOOLEAN,(yyline + 1)); }
string	{ return new Token(sym.STRING,(yyline + 1)); }
return	{ return new Token(sym.RETURN,(yyline + 1)); }
if	{ return new Token(sym.IF,(yyline + 1)); }
else	{ return new Token(sym.ELSE,(yyline + 1)); }
while	{ return new Token(sym.WHILE,(yyline + 1)); }
break	{ return new Token(sym.BREAK,(yyline + 1)); }
continue	{ return new Token(sym.CONTINUE,(yyline + 1)); }
this	{ return new Token(sym.THIS,(yyline + 1)); }
new	{ return new Token(sym.NEW,(yyline + 1)); }
length	{ return new Token(sym.LENGTH,(yyline + 1)); }
true	{ return new Token(sym.TRUE,(yyline + 1)); }
false	{ return new Token(sym.FALSE,(yyline + 1)); }
null	{ return new Token(sym.NULL,(yyline + 1)); }

// rules for identifier IDs
{CLASS_ID}	{ return new Token(sym.CLASS_ID,(yyline + 1),yytext()); }
{ID}	{ return new Token(sym.ID,(yyline + 1),yytext()); }

// rules for numbers: illegal numbers and afterwards legal numbers
0+{NONZERO}{DIGIT}*				{ throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: illegal integer format in token '" + yytext() + "'"); }
0+|({NONZERO}{DIGIT}*)	{ return new Token(sym.INTEGER,(yyline + 1),yytext()); }

// rules for parentheses and punctuation
"("	{ return new Token(sym.LP,(yyline + 1)); }
")"	{ return new Token(sym.RP,(yyline + 1)); }
"{"	{ return new Token(sym.LCBR,(yyline + 1)); }
"}"	{ return new Token(sym.RCBR,(yyline + 1)); }
"["	{ return new Token(sym.LB,(yyline + 1)); }
"]"	{ return new Token(sym.RB,(yyline + 1)); }
","	{ return new Token(sym.COMMA,(yyline + 1)); }
"."	{ return new Token(sym.DOT,(yyline + 1)); }
";"	{ return new Token(sym.SEMI,(yyline + 1)); }

// rules for strings and comments
\"{STRING_TEXT}\"	{ return new Token(sym.QUOTE,(yyline + 1),yytext()); }
\"					{ throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: illegal string literal"); }
"//".*	{ }
"/*"{COMMENT_TEXT}"*/"	{ }
"/*" 				{ throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: Unclosed multi-line comment"); }

// rules for operators: boolean and arithmetic
"="	{ return new Token(sym.ASSIGN,(yyline + 1)); }
"=="	{ return new Token(sym.EQUAL,(yyline + 1)); }
">"	{ return new Token(sym.GT,(yyline + 1)); }
"<"	{ return new Token(sym.LT,(yyline + 1)); }
">="	{ return new Token(sym.GTE,(yyline + 1)); }
"<="	{ return new Token(sym.LTE,(yyline + 1)); }
"!="	{ return new Token(sym.NEQUAL,(yyline + 1)); }
"&&"	{ return new Token(sym.LAND,(yyline + 1)); }
"||"	{ return new Token(sym.LOR,(yyline + 1)); }
"!"	{ return new Token(sym.LNEG,(yyline + 1)); }
"+"	{ return new Token(sym.PLUS,(yyline + 1)); }
"-"	{ return new Token(sym.MINUS,(yyline + 1)); }
"*"	{ return new Token(sym.MULTIPLY,(yyline + 1)); }
"/"	{ return new Token(sym.DIVIDE,(yyline + 1)); }
"%"	{ return new Token(sym.MOD,(yyline + 1)); }

// cleanup rule: reject all other tokens
.	{ throw new LexicalError(yytext(), (yyline + 1), (yyline + 1) + ": Lexical error: illegal character '" + yytext() + "'"); }
