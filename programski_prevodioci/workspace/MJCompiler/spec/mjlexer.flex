package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"const"     { return new_symbol(sym.CONST, yytext()); }
"new"       { return new_symbol(sym.NEW, yytext()); }
"read"      { return new_symbol(sym.READ, yytext()); }

"do"      	{ return new_symbol(sym.DO, yytext()); }
"while"     { return new_symbol(sym.WHILE, yytext()); }
"for"		{ return new_symbol(sym.FOR, yytext()); }
"extends"   { return new_symbol(sym.EXTENDS, yytext()); }
"continue"  { return new_symbol(sym.CONTINUE, yytext()); }
"break"     { return new_symbol(sym.BREAK, yytext()); }
"class"     { return new_symbol(sym.CLASS, yytext()); }
"interface" { return new_symbol(sym.INTERFACE, yytext()); }



"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }
"==" 		{ return new_symbol(sym.IS_EQ, yytext()); }
"!=" 		{ return new_symbol(sym.IS_NOT_EQ, yytext()); }
">" 		{ return new_symbol(sym.LARGER, yytext()); }
">=" 		{ return new_symbol(sym.LARGER_OR_EQ, yytext()); }
"<" 		{ return new_symbol(sym.LESSER, yytext()); }
"<=" 		{ return new_symbol(sym.LESSER_OR_EQ, yytext()); }
"&&" 		{ return new_symbol(sym.AND, yytext()); }
"||" 		{ return new_symbol(sym.OR, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
"++" 		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPARENT, yytext()); }
")" 		{ return new_symbol(sym.RPARENT, yytext()); }
"[" 		{ return new_symbol(sym.LBRACKET, yytext()); }
"]" 		{ return new_symbol(sym.RBRACKET, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext());}



<YYINITIAL> "//" 		    { yybegin(COMMENT); }
<COMMENT> .      			{ yybegin(COMMENT); }
<COMMENT> "\r\n" 			{ yybegin(YYINITIAL); }

"'"[a-z]"'"|"'"[A-Z]"'" 	{return new_symbol(sym.CHAR_CONST, new Character(yytext().charAt(1)));}
[0-9]+  					{ return new_symbol(sym.NUM_CONST, new Integer (yytext())); }
("true"|"false")			{return new_symbol(sym.BOOL_CONST, yytext());}

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) +" u koloni " +yycolumn); }





