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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvolante
 */
public class CMinusParser implements Parser{

    Scanner scanner;
    Token current;
    
    public CMinusParser(Scanner s){
        
    }
    
    @Override
    public Program parse() throws IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    case LBRACE:
                        matchToken(TokenType.LBRACE);
                        d = new VariableDeclaration(true, Long.parseLong(matchToken(TokenType.NUMBER)), id);
                        matchToken(TokenType.RBRACE);
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
        
        if(current.getTokenType() != TokenType.IDENTIFIER){
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
        if(current.getTokenType() == TokenType.LBRACE){
            matchToken(TokenType.LBRACE);
            matchToken(TokenType.RBRACE);
            isArray = true;
        }
        
        return new Parameter(isArray, id);
    }
    
    private CompoundStatement parseCompoundStatement() throws ParseException, IOException{
        matchToken(TokenType.LSQBRACE);
        List<VariableDeclaration> localDeclarations = parseLocalDeclarations();
        List<Statement> statements = parseStatementList();
        matchToken(TokenType.RSQBRACE);
        
        return new CompoundStatement(localDeclarations, statements);
    }
    
    private List<VariableDeclaration> parseLocalDeclarations() throws ParseException, IOException{
        List<VariableDeclaration> declarations = new ArrayList<>();
        while(current.getTokenType() == TokenType.INT){
            declarations.add(parseVariableDeclaration());
        }
        
        return declarations;
    }
    
    private List<Statement> parseStatementList(){
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
        if(current.getTokenType() == TokenType.LBRACE){
            matchToken(TokenType.LBRACE);
            isArray = true;
            numElements = Integer.parseInt(matchToken(TokenType.NUMBER));
            matchToken(TokenType.RBRACE);
        }
        
        return new VariableDeclaration(isArray, numElements, id);
    }
    
    public static void main(String[] args){
        FileReader in = null;
        try {
            String filename = "C:\\Users\\jvolante\\Downloads\\selectionSort.cpp";
            in = new FileReader(filename);
            
            Scanner scanner = new CMinusLexer(in);
            Parser parser = new CMinusParser(scanner);
            
            parser.parse().print(new PrintWriter(System.out), "");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMinusScanner.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CMinusScanner.class.getName())
                    .log(Level.SEVERE, null, ex);
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
        current = scanner.getNextToken();
        return old;
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
                return new IdentifierExpression(matchToken(TokenType.IDENTIFIER));
            case NUMBER:
                return new NumExpression(Integer.parseInt(matchToken(TokenType.NUMBER)));
            default:
                throw new ParseException("Got unexpected token while parsing factor: " + current);
        }
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
            advanceToken();
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
        switch (current.getTokenType()) {
            case NUMBER:
                return parseSimpleExpression ();
            case LPAREN:
                matchToken (TokenType.LPAREN);
                parseExpression();
                matchToken (TokenType.RPAREN);
                Expression simpleExpression = parseSimpleExpression ();
                return simpleExpression;
            case IDENTIFIER:
                Expression expressionPrime = parseExpressionPrime ();
                return expressionPrime;
            default:
                throw new ParseException("Unexpected token, " + current);
        }    
    }

    private Expression parseSimpleExpression() throws IOException {
        Expression addExpression = parseAdditiveExpressionPrime();
        
        if(current.getTokenType().isInGroup(TokenType.Group.RELOP)){
            advanceToken();
            Expression addExp = parseAdditiveExpression();
        }
        return addExpression;
    }

    private Expression parseExpressionPrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Expression parseAdditiveExpression() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
