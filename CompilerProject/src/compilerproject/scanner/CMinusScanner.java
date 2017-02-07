/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hiker
 */
public class CMinusScanner implements Scanner{
    
    private final String WHITESPACE = "\n\t ";
    private PushbackReader in;
    private Token nextToken;
    private boolean returnComments;
    private boolean returnErrors;
    
    public CMinusScanner(InputStreamReader in){
        this.in = new PushbackReader(in);
    }
    
    public CMinusScanner(InputStreamReader in, 
                         boolean returnComments,
                         boolean returnErrors){
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
        StringBuffer tokenData = new StringBuffer();
        CMinusScannerState state = CMinusScannerState.START;

        while(tokenNotFound && in.ready()){
            char current = (char)in.read();
            
            //
            // Keep whitespace out of token data
            //
            if(!WHITESPACE.contains(Character.toString(current))){
                tokenData.append(current);
            }

            switch(state){
                case START:
                    if(current == '/'){
                        state = CMinusScannerState.FIRST_FORWARD_SLASH;
                    }
                    break;
                case FIRST_FORWARD_SLASH:
                    if(current == '*'){
                        state = CMinusScannerState.COMMENT_INTERIOR;
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
                            tokenData.delete(0, -1);
                            state = CMinusScannerState.START;
                        }
                    }
                    break;
            }
        }
        
        return result;
    }
}
