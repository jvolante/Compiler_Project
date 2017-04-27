/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author jvolante
 */
public class ParseException extends Error{
    String message;
    public ParseException(String message){
        this.message = message;
    }
    
    public String toString(){
        return message;
    }
}
