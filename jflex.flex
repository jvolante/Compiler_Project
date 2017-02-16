%%

%class CMinusLexer
%unicode
%cup
%line
%column

%{
  StringBuilder string = new StringBuilder();
%}

LineTerminator = \r|\n|\r\n
Whitespace     = {LineTerminator} | [ \t\f]
Digit          = [0-9]
Letter         = [a-zA-Z]

<YYINITIAL> "if"     { return new Token(TokenType.IF, string.toString()); }
<YYINITIAL> "else"   { return new Token(TokenType.ELSE, string.toString()); }
<YYINITIAL> "int"    { return new Token(TokenType.INT, string.toString()); }
<YYINITIAL> "void"   { return new Token(TokenType.VOID, string.toString()); }
<YYINITIAL> "return" { return new Token(TokenType.RETURN, string.toString()); }
<YYINITIAL> "while"  {return new Token(TokenType.WHILE, string.toString()); }
<YYINITIAL> {
              {Letter} {Letter}* { return new Token(TokenType.IDENTIFIER, string.toString()); }
              {Digit} {Digit}*   { return new Token(TokenType.DIGIT, string.toString()); }

              "="                { return new Token(TokenType.ASSIGN, string.toString()); }
              "=="               { return new Token(TokenType.EQUALS, string.toString()); }
              "<="               { return new Token(TokenType.LESSTHANEQ, string.toString()); }
              ">="               { return new Token(TokenType.GREATERTHANEQ, string.toString()); }
              "!="               { return new Token(TokenType.NOTEQ, string.toString()); }
              "+"                { return new Token(TokenType.PLUS, string.toString()); }
              "-"                { return new Token(TokenType.Minus, string.toString()); }
              "*"                { return new Token(TokenType.Multiply, string.toString()); }
              "/"                { return new Token(TokenType.Divide, string.toString()); }
              "("                { return new Token(TokenType.LPAREN, string.toString()); }
              ")"                { return new Token(TokenType.RPAREN, string.toString()); }
              "{"                { return new Token(TokenType.LBRACE, string.toString()); }
              "}"                { return new Token(TokenType.RBRACE, string.toString()); }
              "["                { return new Token(TokenType.LSQBRACE, string.toString()); }
              "]"                { return new Token(TokenType.RSQBRACE, string.toString()); }
            }

<STRING> {
