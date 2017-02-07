/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson Volante
 */
public class CMinusScanner implements Scanner{
    
    private static final String WHITESPACE = "\r\n\t ";
    private static final String DIGITS = "0123456789";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    private PushbackReader in;
    private Token nextToken;
    private boolean returnComments = true;
    private boolean returnErrors = true;
    
    public CMinusScanner(InputStreamReader in) throws IOException{
        this.in = new PushbackReader(in);
        nextToken = readToken();
    }
    
    public CMinusScanner(InputStreamReader in, 
                         boolean returnComments,
                         boolean returnErrors) throws IOException{
        this(in);
        this.returnComments = returnComments;
        this.returnErrors = returnErrors;
    }

    @Override
    public Token getNextToken() throws IOException{
        Token result = nextToken;
        nextToken = readToken();
        
        return result;
    }

    @Override
    public Token viewNextToken() {
        return nextToken;
    }
    
    private Token readToken() throws IOException{
        Token result = null;
        boolean tokenNotFound = true;
        StringBuilder tokenData = new StringBuilder();
        CMinusScannerState state = CMinusScannerState.START;

        while(tokenNotFound && in.ready()){
            char current = (char)in.read();
            tokenData.append(current);

            switch(state){
                case START:
                    if(current == '/'){
                        state = CMinusScannerState.FIRST_FORWARD_SLASH;
                    } else if(current == '+') {
                        tokenNotFound = false;
                        result = new Token(TokenType.PLUS,
                                           tokenData.toString());
                    } else if (current == '-'){
                        tokenNotFound = false;
                        result = new Token(TokenType.MINUS,
                                           tokenData.toString());
                    } else if (current == '*') {
                        tokenNotFound = false;
                        result = new Token(TokenType.PLUS,
                                           tokenData.toString());
                    } else if (current == '('){
                        tokenNotFound = false;
                        result = new Token(TokenType.LPAREN,
                                           tokenData.toString());
                    } else if (current == ')'){
                        tokenNotFound = false;
                        result = new Token(TokenType.RPAREN,
                                           tokenData.toString());
                    } else if (current == '{') {
                        tokenNotFound = false;
                        result = new Token(TokenType.LBRACE,
                                           tokenData.toString());
                    } else if (current == '}') {
                        tokenNotFound = false;
                        result = new Token(TokenType.RBRACE,
                                           tokenData.toString());
                    } else if (current == '['){
                        tokenNotFound = false;
                        result = new Token(TokenType.LSQBRACE,
                                           tokenData.toString());
                    } else if (current == ']'){
                        tokenNotFound = false;
                        result = new Token(TokenType.RSQBRACE,
                                           tokenData.toString());
                    } else if (current == ','){
                        tokenNotFound = false;
                        result = new Token(TokenType.COMMA,
                                           tokenData.toString());
                    } else if (current == ';'){
                        tokenNotFound = false;
                        result = new Token(TokenType.SEMICOLON,
                                           tokenData.toString());
                    } else if (current == '<') {
                        state = CMinusScannerState.FOUND_LESS_THAN;
                    } else if (current == '>'){
                        state = CMinusScannerState.FOUND_GREATER_THAN;
                    } else if (current == '='){
                        state = CMinusScannerState.FOUND_EQ;
                    } else if (current == '!'){
                        state = CMinusScannerState.FOUND_BANG;
                    }
                    else if (DIGITS.contains(Character.toString(current))){
                        state = CMinusScannerState.DIGIT;
                    } else if (LETTERS.contains(Character.toString(current))){
                        state = CMinusScannerState.IDENTIFIER;
                    } else if(WHITESPACE.contains(Character.toString(current))){
                        // Ignore whitespace
                        tokenData.deleteCharAt(tokenData.length() - 1);
                    } else {
                        if (returnErrors){
                            tokenNotFound = false;
                            result = new Token(TokenType.ERROR,
                                               tokenData.toString());
                        } else {
                            // Clear token data
                            tokenData.delete(0, tokenData.length());
                            state = CMinusScannerState.START;
                        }
                    }
                    break;
                case FIRST_FORWARD_SLASH:
                    if(current == '*'){
                        state = CMinusScannerState.COMMENT_INTERIOR;
                    } else {
                        tokenData.deleteCharAt(tokenData.length() - 1);
                        in.unread(current);
                        
                        tokenNotFound = false;
                        
                        result = new Token(TokenType.DIVIDE, 
                                           tokenData.toString());
                    }
                    break;
                case COMMENT_INTERIOR:
                    if(current == '*'){
                        state = CMinusScannerState.COMMENT_SECOND_STAR;
                    }
                    break;
                case COMMENT_SECOND_STAR:
                    if(current == '/'){
                        if(returnComments){
                            tokenNotFound = false;
                            result = new Token(TokenType.COMMENT, WHITESPACE);
                        } else {
                            // Clear token data
                            tokenData.delete(0, tokenData.length());
                            state = CMinusScannerState.START;
                        }
                    }
                    break;
                case DIGIT:
                    if(!DIGITS.contains(Character.toString(current))){
                        tokenData.deleteCharAt(tokenData.length() - 1);
                        in.unread(current);
                        
                        tokenNotFound = false;
                        
                        result = new Token(TokenType.NUMBER, 
                                           tokenData.toString());
                    }
                    break;
                case IDENTIFIER:
                    if(!LETTERS.contains(Character.toString(current))){
                        
                        tokenData.deleteCharAt(tokenData.length() - 1);
                        in.unread(current);
                        
                        tokenNotFound = false;
                        
                        String tokenString = tokenData.toString();
                        
                        if (tokenString.compareTo("else") == 0){
                            result = new Token(TokenType.ELSE, 
                                               tokenString);
                        } else if (tokenString.compareTo("if") == 0){
                            result = new Token(TokenType.IF, 
                                               tokenString);
                        } else if (tokenString.compareTo("int") == 0){
                            result = new Token(TokenType.INT, 
                                               tokenString);
                        } else if (tokenString.compareTo("return") == 0){
                            result = new Token(TokenType.RETURN, 
                                               tokenString);
                        } else if (tokenString.compareTo("void") == 0){
                            result = new Token(TokenType.VOID, 
                                               tokenString);
                        } else if (tokenString.compareTo("while") == 0){
                            result = new Token(TokenType.WHILE, 
                                               tokenString);
                        } else {
                            result = new Token(TokenType.IDENTIFIER, 
                                               tokenString);
                        }
                    }
                    break;
                case FOUND_BANG:
                    if(current == '='){
                        tokenNotFound = false;
                        
                        result = new Token(TokenType.NOTEQ, 
                                           tokenData.toString());
                    } else {
                        if (returnErrors){
                            tokenNotFound = false;
                            result = new Token(TokenType.ERROR,
                                               tokenData.toString());
                        } else {
                            // Clear token data
                            tokenData.delete(0, tokenData.length());
                            state = CMinusScannerState.START;
                        }
                    }
                    break;
                case FOUND_EQ:
                    switch (current) {
                        case '<':
                            tokenNotFound = false;
                            result = new Token(TokenType.LESSTHANEQ,
                                    tokenData.toString());
                            break;
                        case '>':
                            tokenNotFound = false;
                            result = new Token(TokenType.GREATERTHANEQ,
                                    tokenData.toString());
                            break;
                        case '=':
                            tokenNotFound = false;
                            result = new Token(TokenType.EQUALS,
                                    tokenData.toString());
                            break;
                        default:
                            tokenData.deleteCharAt(tokenData.length() - 1);
                            in.unread(current);
                            tokenNotFound = false;
                            result = new Token(TokenType.ASSIGN,
                                    tokenData.toString());
                            break;
                    }
                    break;
                case FOUND_LESS_THAN:
                    if (current == '='){
                        tokenNotFound = false;
                        result = new Token(TokenType.LESSTHANEQ,
                                           tokenData.toString());
                    } else {
                        tokenData.deleteCharAt(tokenData.length() - 1);
                        in.unread(current);
                        tokenNotFound = false;
                        result = new Token(TokenType.LESSTHAN,
                                           tokenData.toString());
                    }
                    break;
                case FOUND_GREATER_THAN:
                    if (current == '='){
                        tokenNotFound = false;
                        result = new Token(TokenType.GREATERTHANEQ,
                                           tokenData.toString());
                    } else {
                        tokenData.deleteCharAt(tokenData.length() - 1);
                        in.unread(current);
                        tokenNotFound = false;
                        result = new Token(TokenType.GREATERTHAN,
                                           tokenData.toString());
                    }
                    break;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args){
        FileReader in = null;
        try {
            String filename = "C:\\Users\\jvolante\\Downloads\\selectionSort.cpp";
            in = new FileReader(filename);
            
            Scanner scanner = new CMinusScanner(in);
            
            Token next;
            while((next = scanner.getNextToken()) != null){
                System.out.println(next);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(CMinusScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
