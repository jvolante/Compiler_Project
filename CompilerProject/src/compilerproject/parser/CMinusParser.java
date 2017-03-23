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
    
    private void matchToken(TokenType tt) throws ParseException, IOException{
        if(current.getTokenType() != tt){
            throw new ParseException("Expected Token " + tt + " got " + current);
        }
        advanceToken();
    }
}
