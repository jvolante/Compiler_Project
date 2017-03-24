/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author jvolante
 */
public class AssignExpression extends Expression {
    IdentifierExpression id;
    Expression value;
    
    public AssignExpression(String ident, Expression v){
        this(new IdentifierExpression(ident), v);
    }
    
    public AssignExpression(IdentifierExpression ident, Expression v){
        if(ident == null || v == null){
            throw new InvalidParameterError();
        }
        id = ident;
        value = v;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"=\n");
        writer.write(tabs+"    "+id+"\n");
        value.print(writer, "    "+tabs);
    }   
    
}
