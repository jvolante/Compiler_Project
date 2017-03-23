/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author jvolante
 */
public abstract class Statement {
    protected StatementType type;
    
    public enum StatementType{
        EXPRESSION, COMPOUND, SELECTION, ITERATION, RETURN
    }
    
    public Statement(){
        
    }
    
    public abstract void print(BufferedWriter writer, String tabs) throws IOException;
}
