/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.CMinusLexer;
import compilerproject.scanner.CMinusScanner;
import compilerproject.scanner.Scanner;
import compilerproject.scanner.Token;
import compilerproject.scanner.TokenType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvolante
 */
public class CMinusParser implements Parser{

    Scanner scanner;
    Token current;
    Stack<Token> pushback = new Stack<>();
    
    public CMinusParser(Scanner s) throws IOException{
        scanner = s;
        current = s.getNextToken();
    }
    
    @Override
    public Program parse() throws IOException, ParseException{
        List<Declaration> decs = new ArrayList<>();
        while(current != null){
            decs.add(parseDeclaration());
        }
        return new Program(decs);
    }
    
    private Declaration parseDeclaration() throws ParseException, IOException{
        Declaration d;
        switch(current.getTokenType()){
            case VOID:
                d = parseVoidFunctionDeclaration();
                break;
            case INT:
                matchToken(TokenType.INT);
                String id = matchToken(TokenType.IDENTIFIER);
                switch(current.getTokenType()){
                    case LSQBRACE:
                        matchToken(TokenType.LSQBRACE);
                        d = new VariableDeclaration(true, Long.parseLong(matchToken(TokenType.NUMBER)), id);
                        matchToken(TokenType.RSQBRACE);
                        matchToken(TokenType.SEMICOLON);
                        break;
                    case SEMICOLON:
                        matchToken(TokenType.SEMICOLON);
                        d = new VariableDeclaration(false, 0, id);
                        break;
                    case LPAREN:
                        matchToken(TokenType.LPAREN);
                        List<Parameter> params = parseParameters();
                        matchToken(TokenType.RPAREN);
                        CompoundStatement cs = parseCompoundStatement();
                        
                        d = new FunctionDeclaration(Declaration.DataType.INT, id, params, cs);
                        break;
                    default:
                        throw new ParseException("Unexpected Token while parsing declaration: " + current);
                }
                break;
            default:
                throw new ParseException("Unexpected Token while parsing declaration: " + current);
        }
        
        return d;
    }
    
    private FunctionDeclaration parseVoidFunctionDeclaration() throws ParseException, IOException{
        matchToken(TokenType.VOID);
        String id = matchToken(TokenType.IDENTIFIER);
        matchToken(TokenType.LPAREN);
        List<Parameter> params = parseParameters();
        matchToken(TokenType.RPAREN);
        CompoundStatement cs = parseCompoundStatement();
        
        return new FunctionDeclaration(Declaration.DataType.VOID, id, params, cs);
    }
    
    private List<Parameter> parseParameters() throws ParseException, IOException{
        List<Parameter> params = new ArrayList<>();
        
        if(current.getTokenType() == TokenType.INT){
            params.add(parseParameter());
            while(current.getTokenType() == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                params.add(parseParameter());
            }
        }
        
        return params;
    }
    
    private Parameter parseParameter() throws ParseException, IOException{
        matchToken(TokenType.INT);
        String id = matchToken(TokenType.IDENTIFIER);
        
        boolean isArray = false;
        if(current.getTokenType() == TokenType.LSQBRACE){
            matchToken(TokenType.LSQBRACE);
            matchToken(TokenType.RSQBRACE);
            isArray = true;
        }
        
        return new Parameter(isArray, id);
    }
    
    private CompoundStatement parseCompoundStatement() throws ParseException, IOException{
        matchToken(TokenType.LBRACE);
        List<VariableDeclaration> localDeclarations = parseLocalDeclarations();
        List<Statement> statements = parseStatementList();
        matchToken(TokenType.RBRACE);
        
        return new CompoundStatement(localDeclarations, statements);
    }
    
    private List<VariableDeclaration> parseLocalDeclarations() throws ParseException, IOException{
        List<VariableDeclaration> declarations = new ArrayList<>();
        while(current.getTokenType() == TokenType.INT){
            declarations.add(parseVariableDeclaration());
        }
        
        return declarations;
    }
    
    private List<Statement> parseStatementList() throws ParseException, IOException{
        List<Statement> statements = new ArrayList<>();
        while(current.getTokenType() == TokenType.SEMICOLON ||
              current.getTokenType() == TokenType.LPAREN ||
              current.getTokenType() == TokenType.NUMBER ||
              current.getTokenType() == TokenType.IDENTIFIER ||
              current.getTokenType() == TokenType.IF ||
              current.getTokenType() == TokenType.WHILE ||
              current.getTokenType() == TokenType.RETURN){
            
            statements.add(parseStatement());
        }
        return statements;
    }
    
    private VariableDeclaration parseVariableDeclaration() throws ParseException, IOException{
        matchToken(TokenType.INT);
        String id = matchToken(TokenType.IDENTIFIER);
        boolean isArray = false;
        int numElements = 0;
        if(current.getTokenType() == TokenType.LSQBRACE){
            matchToken(TokenType.LSQBRACE);
            isArray = true;
            numElements = Integer.parseInt(matchToken(TokenType.NUMBER));
            matchToken(TokenType.RSQBRACE);
        }
        matchToken(TokenType.SEMICOLON);
        
        return new VariableDeclaration(isArray, numElements, id);
    }
    
    public static void main(String[] args){
        FileReader in = null;
        try {
            String filename = "C:\\Users\\jvolante\\Downloads\\selectionSort.cpp";
            in = new FileReader(filename);
            
            Scanner scanner = new CMinusLexer(in);
            Parser parser = new CMinusParser(scanner);
            
            PrintWriter p = new PrintWriter(System.out);
            parser.parse().print(p, "");
            p.flush();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMinusScanner.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CMinusScanner.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CMinusParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(CMinusScanner.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private Token advanceToken() throws IOException{
        Token old = current;
        current = !pushback.isEmpty() ? pushback.pop() : scanner.getNextToken();
        System.out.println(current);
        return old;
    }
    
    private void rollbackToken(Token t){
        pushback.push(current);
        current = t;
    }
    
    private String matchToken(TokenType tt) throws ParseException, IOException{
        if(current.getTokenType() != tt){
            throw new ParseException("Expected Token " + tt + " got " + current);
        }
        String data = current.getData();
        advanceToken();
        return data;
    }
    // Given in PPT
    private Expression parseFactor () throws ParseException, IOException {
        switch (current.getTokenType()) {
            case LPAREN:
                advanceToken();
                Expression returnExpr = parseBinaryExpression ();
                matchToken(TokenType.RPAREN);
                return returnExpr;
            case IDENTIFIER:
                String id = matchToken(TokenType.IDENTIFIER);
                switch(current.getTokenType()){
                    case LSQBRACE:
                        matchToken(TokenType.LSQBRACE);
                        Expression e = parseExpression();
                        matchToken(TokenType.RSQBRACE);
                        return new IdentifierExpression(id, e);
                    case LPAREN:
                        matchToken(TokenType.LPAREN);
                        List<Expression> args = parseArgs();
                        matchToken(TokenType.RPAREN);
                        return new CallExpression(id, args);
                    default:
                        return new IdentifierExpression(id);
                }
            case NUMBER:
                return new NumExpression(Integer.parseInt(matchToken(TokenType.NUMBER)));
            default:
                throw new ParseException("Got unexpected token while parsing factor: " + current);
        }
    }
    
    private List<Expression> parseArgs() throws ParseException, IOException{
        List<Expression> args = new ArrayList<>();
        if(current.getTokenType() == TokenType.NUMBER ||
           current.getTokenType() == TokenType.LPAREN ||
           current.getTokenType() == TokenType.IDENTIFIER){
           args.add(parseExpression());
        }
        while(current.getTokenType() == TokenType.COMMA){
            matchToken(TokenType.COMMA);
            args.add(parseExpression());
        }
        return args;
    }
    
    // Given in PPT
    private Expression parseBinaryExpression () throws IOException, ParseException {

        Expression lhs = parseTerm();

        while (current.getTokenType().isInGroup(TokenType.Group.ADDOP)) {
            Token oldToken = advanceToken();
            Expression rhs = parseTerm();
                // make lhs the result, so set up for next iter
            lhs = new BinaryExpression(lhs, rhs, oldToken.getTokenType());               
        }

        return lhs;
    }

    private Expression parseTerm() throws IOException, ParseException {
        Expression lhs = parseFactor();

        while (current.getTokenType().isInGroup(TokenType.Group.MULOP)) {
            Token oldToken = advanceToken();
            Expression rhs = parseFactor();
                // make lhs the result, so set up for next iter
            lhs = new BinaryExpression(lhs, rhs, oldToken.getTokenType());               
        }

        return lhs;   
    }

    private Statement parseStatement() throws ParseException, IOException {
        switch (current.getTokenType()) {
            case NUMBER:
            case LPAREN:
            case IDENTIFIER:
                Statement exprStatement = parseExprStatement ();
                return exprStatement;
            case IF:
                Statement ifStatement = parseIfStatement ();
                return ifStatement;
            case WHILE:
                Statement whileStatement = parseIterationStatement ();
                return whileStatement;
            case RETURN:
                Statement returnStatement = parseReturnStatement ();
                return returnStatement;
            case LBRACE:
                return parseCompoundStatement();
            default:
                throw new ParseException("Unexpected token, " + current);
        }        
    }

    // Given in PPT
    private Statement parseIfStatement () throws ParseException, IOException {
        matchToken (TokenType.IF);
        matchToken (TokenType.LPAREN);
        Expression ifExpr = parseExpression();
        matchToken(TokenType.RPAREN);
        Statement thenStmt = parseStatement();
        Statement elseStmt = null;    

        if (current.getTokenType() == TokenType.ELSE) {
            advanceToken();
            elseStmt = parseStatement();
        }

        Statement returnStmt = new IfStatement(ifExpr, thenStmt, elseStmt);
        return returnStmt;
    }
    
    private Statement parseExprStatement() throws ParseException, IOException {
        Expression exprStmt = parseExpression();
        matchToken (TokenType.SEMICOLON);
        Statement stmt = new ExpressionStatement(exprStmt);
        return stmt;
    }
    
    private Statement parseIterationStatement() throws ParseException, IOException {
        matchToken (TokenType.WHILE);
        matchToken (TokenType.LPAREN);       
        Expression whileExpr = parseExpression();
        matchToken(TokenType.RPAREN);
        Statement doStmt = parseStatement();    
        Statement whileStmt = new ItterationStatement(whileExpr, doStmt);
        return whileStmt;
    }    

    private Statement parseReturnStatement() throws ParseException, IOException {
        Statement stmt;
        matchToken (TokenType.RETURN);
        if (current.getTokenType() == TokenType.NUMBER ||
                current.getTokenType() == TokenType.LPAREN ||
                current.getTokenType() == TokenType.IDENTIFIER) {
            Expression retExpr = parseExpression();
            stmt = new ReturnStatement(retExpr);            
        } else if (current.getTokenType() == TokenType.SEMICOLON) {
            stmt = new ReturnStatement(); 
        } else {
            throw new ParseException("Unexpected token, " + current);
        }
        matchToken (TokenType.SEMICOLON);
        return stmt;    
    }

    private Expression parseExpression() throws ParseException, IOException {
        Expression e = null;
        switch (current.getTokenType()) {
            case NUMBER:
            case LPAREN:
                e = parseSimpleExpression();
                break;
            case IDENTIFIER:
                // expression`
                Token idToken = current;
                String id = matchToken(TokenType.IDENTIFIER);
                switch(current.getTokenType()){
                    case ASSIGN:
                        matchToken(TokenType.ASSIGN);
                        e = new AssignExpression(id, parseExpression());
                        break;
                    case LPAREN:
                        rollbackToken(idToken);
                        e = parseFactor();
                        break;
                    case LSQBRACE:
                        matchToken(TokenType.LSQBRACE);
                        IdentifierExpression ie = new IdentifierExpression(id, parseExpression());
                        matchToken(TokenType.RSQBRACE);
                        
                        // expression``
                        if(current.getTokenType() == TokenType.ASSIGN){
                            matchToken(TokenType.ASSIGN);
                            e = new AssignExpression(ie, parseExpression());
                        } else if (current.getTokenType().isInGroup(TokenType.Group.RELOP) ||
                                   current.getTokenType().isInGroup(TokenType.Group.ADDOP) ||
                                   current.getTokenType().isInGroup(TokenType.Group.MULOP)){
                            
                            e = parseSimpleExpressionPrime(ie);
                        } else if (current.getTokenType() == TokenType.SEMICOLON){
                            //Do nothing and just leave
                        }else {
                            throw new ParseException("Unexpected Token: " + current);
                        }
                    default:
                        if(current.getTokenType().isInGroup(TokenType.Group.RELOP) ||
                           current.getTokenType().isInGroup(TokenType.Group.ADDOP) ||
                           current.getTokenType().isInGroup(TokenType.Group.MULOP)){
                            parseSimpleExpressionPrime(new IdentifierExpression(id));
                        }
                        
                }
                break;
            default:
                throw new ParseException("Unexpected token, " + current);
        }    
        
        return e;
    }
    
    private Expression parseSimpleExpressionPrime(IdentifierExpression ie) throws IOException, ParseException{
        Expression e = null;
        if(current.getTokenType().isInGroup(TokenType.Group.RELOP)){
            TokenType op = current.getTokenType();
            advanceToken();
            e = new BinaryExpression(ie, parseAdditiveExpression(), op);
        } else if (current.getTokenType().isInGroup(TokenType.Group.ADDOP) ||
                   current.getTokenType().isInGroup(TokenType.Group.MULOP)){
            e = parseAdditiveExpressionPrime(ie);
        } else {
            throw new ParseException("Unexpected token, " + current);
        }
        return e;
    }
    
    private Expression parseAdditiveExpressionPrime(IdentifierExpression ie) throws IOException, ParseException{
        Expression lhs = ie;

        if(current.getTokenType().isInGroup(TokenType.Group.ADDOP)){
            while (current.getTokenType().isInGroup(TokenType.Group.ADDOP)) {
                Token oldToken = advanceToken();
                Expression rhs = parseTerm();
                    // make lhs the result, so set up for next iter
                lhs = new BinaryExpression(lhs, rhs, oldToken.getTokenType());               
            }    
        } else if (current.getTokenType().isInGroup(TokenType.Group.MULOP)){
            lhs = parseTermPrime(ie);
        }
        

        return lhs;
    }
    
    private Expression parseTermPrime(IdentifierExpression ie) throws IOException, ParseException{
        Expression lhs = ie;

        while (current.getTokenType().isInGroup(TokenType.Group.MULOP)) {
            Token oldToken = advanceToken();
            Expression rhs = parseFactor();
                // make lhs the result, so set up for next iter
            lhs = new BinaryExpression(lhs, rhs, oldToken.getTokenType());               
        }

        return lhs; 
    }
    
    private Expression parseSimpleExpression() throws IOException, ParseException{
        Expression lhs = parseAdditiveExpression();
        if(current.getTokenType().isInGroup(TokenType.Group.RELOP)){
            TokenType op = current.getTokenType();
            advanceToken();
            lhs = new BinaryExpression(lhs, parseAdditiveExpression(), op);
        }
        return lhs;
    }
    
    private Expression parseAdditiveExpression() throws IOException, ParseException{
        Expression lhs = parseTerm();

        while (current.getTokenType().isInGroup(TokenType.Group.ADDOP)) {
            Token oldToken = advanceToken();
            Expression rhs = parseTerm();
                // make lhs the result, so set up for next iter
            lhs = new BinaryExpression(lhs, rhs, oldToken.getTokenType());               
        }

        return lhs;   
    }

}
