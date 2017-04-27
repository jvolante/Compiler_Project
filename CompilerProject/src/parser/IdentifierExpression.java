/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author jtdeane
 */
public class IdentifierExpression extends Expression{
    String id;
    Expression element;
    
    public IdentifierExpression(String ident, Expression e){
        id = ident;
        element = e;
    }
    
    public IdentifierExpression(String ident){
        this(ident, null);
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs + id + "\n");
        if(element != null){
            writer.write(tabs + "[\n");
            element.print(writer, tabs+"    ");
            writer.write(tabs + "]\n");
        }
    }
}
