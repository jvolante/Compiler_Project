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
import java.io.BufferedWriter;
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
    
    private List<VariableDeclaration> parseLocalDeclarations(){
        List<VariableDeclaration> declarations = new ArrayList<>();
        while(current.getTokenType() == TokenType.INT){
            declarations.add(parseVariableDeclaration());
        }
        
        return declarations;
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
    
    private void advanceToken() throws IOException{
        current = scanner.getNextToken();
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
    private Expression parseFactor () {
        switch (currentToken.getTokenType()) {
            case LPAREN:
                advanceToken();
                Expression returnExpr = parseBinaryExpression ();
                matchToken(TokenType.RPAREN);
                return returnExpr;
            case IDENTIFIER:
                Token oldToken = advanceToken();
                return createIdentExpr(oldToken);
            case NUMBER:
                Token oldToken = advanceToken();
                return createNumExpr(oldToken);
            default:
                logParseError();
                return null;
        }
    }
    
    // Given in PPT
    private Expression parseBinaryExpression () {

        Expression lhs = parseTerm();

        while (isAddop (currentToken.getTokenType())) {
            Token oldToken = advanceToken();
            Expression rhs = parseTerm();
                // make lhs the result, so set up for next iter
            lhs = createBinopExpr(oldToken.getTokenType(), lhs, rhs);               
        }

        return lhs;
    }

    private Expression parseTerm() {
        Expression lhs = parseFactor();

        while (isMulop (currentToken.getTokenType())) {
            Token oldToken = advanceToken();
            Expression rhs = parseFactor();
                // make lhs the result, so set up for next iter
            lhs = createBinopExpr (oldToken.getTokenType(), lhs, rhs);               
        }

        return lhs;   
    }

    private Statement parseStatement() {
        switch (currentToken.getTokenType()) {
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
                logParseError();
                return null;
        }        
    }

    // Given in PPT
    private Statement parseIfStatement () {
        matchToken (TokenType.IF);
        matchToken (TokenType.LPAREN);
        Expression ifExpr = parseExpression();
        matchToken(TokenType.RPAREN);
        Statement thenStmt = parseStatement();
        Statement elseStmt = null;    

        if (currentToken.getTokenType() == TokenType.ELSE) {
            AdvanceToken();
            elseStmt = parseStatement();
        }

        Statement returnStmt = new IfStatement(ifExpr, thenStmt, elseStmt);
        return returnStmt;
    }
    
    private Statement parseExprStatement() {
        Expression exprStmt = parseExpression();
        matchToken (TokenType.SEMICOLON);
        Statement stmt = new ExpressionStatement(exprStmt);
        return stmt;
    }
    
    private Statement parseIterationStatement() {
        matchToken (TokenType.WHILE);
        matchToken (TokenType.LPAREN);       
        Expression whileExpr = parseExpression();
        matchToken(TokenType.RPAREN);
        Statement doStmt = parseStatement();    
        Statement whileStmt = new ItterationStatement(whileExpr, doStmt);
        return whileStmt;
    }    

    private Statement parseReturnStatement() {
        Statement stmt;
        matchToken (TokenType.RETURN);
        if (currentToken.getTokenType() == TokenType.NUMBER |
                currentToken.getTokenType() == TokenType.LPAREN |
                currentToken.getTokenType() == TokenType.IDENTIFIER) {
            Expression retExpr = parseExpression();
            AdvanceToken();
            stmt = new ReturnStatement(retExpr);            
        } else if (currentToken.getTokenType() == TokenType.SEMICOLON) {
            stmt = new ReturntStatement(); 
        } else {
            return logParseError();
        }
        matchToken (TokenType.SEMICOLON);
        return stmt;    
    }

    private Expression parseExpression() {
        switch (currentToken.getTokenType()) {
            case NUMBER:
                Expression simpleExpresion = parseSimpleExpression ();
                return simpleExpression;
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
                logParseError();
                return null;
        }    
    }

    private Expression parseSimpleExpression() {
        Expression addExpression = parseAdditiveExpressionPrime();
        if(currentToken.getTokenType() == TokenType.RELOP){
            
        }
        return addExpression;
    }

    private Expression parseExpressionPrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
