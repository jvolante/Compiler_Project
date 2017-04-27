/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import lowlevel.Function;

/**
 *
 * @author jvolante
 */
public abstract class Statement {
    protected StatementType type;
    
    public enum StatementType{
        EXPRESSION, COMPOUND, SELECTION, ITERATION, RETURN
    }
    
    public abstract void print(PrintWriter writer, String tabs) throws IOException;
    
    public abstract void genCode(Function f);
}
