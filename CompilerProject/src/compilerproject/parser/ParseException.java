/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

/**
 *
 * @author jvolante
 */
public class ParseException extends Exception{
    String message;
    public ParseException(String message){
        this.message = message;
    }
    
    public String toString(){
        return message;
    }
}