/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import scanner.Token;
import java.io.IOException;
import java.io.PrintWriter;
import lowlevel.CodeItem;

/**
 *
 * @author jvolante
 */
public abstract class Declaration {
    protected DeclarationType decType;
    protected DataType type;

    public static enum DataType {
        INT, VOID
    }
    
    public enum DeclarationType{
        FUNCTION_DECLARATION, VARIABLE_DECLARATION
    }
    
    public abstract CodeItem genCode();
    
    public abstract void print(PrintWriter writer, String tabs) throws IOException;
}
