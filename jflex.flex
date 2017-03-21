package compilerproject.scanner;
import java.io.IOException;

%%

%class CMinusLexer
%type Token
%unicode
%line
%column

%implements Scanner
%public

%{
    private Token nextToken;
    private boolean returnComments = true;
    private boolean returnErrors = true;

    @Override
    public Token getNextToken() throws IOException{
        Token result = nextToken == null ? yylex() : nextToken;
        nextToken = yylex();
        
        return result;
    }

    @Override
    public Token viewNextToken() {
        return nextToken;
    }
%}

Digit          = [0-9]
Letter         = [a-zA-Z]

Comment        = "/*" .* "*/"
Whitespace     = [\r\n\t\f ]

%%

<YYINITIAL> "if"     { return new Token(TokenType.IF, yytext()); }
<YYINITIAL> "else"   { return new Token(TokenType.ELSE, yytext()); }
<YYINITIAL> "int"    { return new Token(TokenType.INT, yytext()); }
<YYINITIAL> "void"   { return new Token(TokenType.VOID, yytext()); }
<YYINITIAL> "return" { return new Token(TokenType.RETURN, yytext()); }
<YYINITIAL> "while"  { return new Token(TokenType.WHILE, yytext()); }
<YYINITIAL> {
              {Letter} {Letter}* { return new Token(TokenType.IDENTIFIER, yytext()); }
              {Digit} {Digit}*   { return new Token(TokenType.NUMBER, yytext()); }

			  {Comment}          { /* ignore */ }
			  {Whitespace}       { /* ignore */ }
			  
              "="                { return new Token(TokenType.ASSIGN, yytext()); }
			  "<"                { return new Token(TokenType.LESSTHAN, yytext()); }
			  ">"                { return new Token(TokenType.GREATERTHAN, yytext()); }
              "=="               { return new Token(TokenType.EQUALS, yytext()); }
              "<="               { return new Token(TokenType.LESSTHANEQ, yytext()); }
              ">="               { return new Token(TokenType.GREATERTHANEQ, yytext()); }
              "!="               { return new Token(TokenType.NOTEQ, yytext()); }
              "+"                { return new Token(TokenType.PLUS, yytext()); }
              "-"                { return new Token(TokenType.MINUS, yytext()); }
              "*"                { return new Token(TokenType.MULTIPLY, yytext()); }
              "/"                { return new Token(TokenType.DIVIDE, yytext()); }
              "("                { return new Token(TokenType.LPAREN, yytext()); }
              ")"                { return new Token(TokenType.RPAREN, yytext()); }
              "{"                { return new Token(TokenType.LBRACE, yytext()); }
              "}"                { return new Token(TokenType.RBRACE, yytext()); }
              "["                { return new Token(TokenType.LSQBRACE, yytext()); }
              "]"                { return new Token(TokenType.RSQBRACE, yytext()); }
			  ","                { return new Token(TokenType.COMMA, yytext()); }
			  ";"                { return new Token(TokenType.SEMICOLON, yytext()); }
			  
			  {Letter} {Letter}* {Digit} {Digit}* { return new Token(TokenType.ERROR, yytext()); }
			  {Digit} {Digit}* {Letter} {Letter}* { return new Token(TokenType.ERROR, yytext()); }
            }
			[^]                  { return new Token(TokenType.ERROR, yytext()); }