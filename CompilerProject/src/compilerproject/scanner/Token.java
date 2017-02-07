/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.scanner;

/**
 *
 * @author hiker
 */
public class Token {
    private TokenType type;
    private String data;
    
    public Token(TokenType type, String data){
        this.type = type;
        this.data = data;
    }
    
    public TokenType getTokenType(){
        return type;
    }
    
    public String getData(){
        return data;
    }
    
    @Override
    public String toString(){
        return type.toString();
    }
}
