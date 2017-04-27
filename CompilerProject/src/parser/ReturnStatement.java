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
 * @author jvolante
 */
public class ReturnStatement extends Statement {
    Expression returnExpression;
    
    public ReturnStatement(Expression e){
        type = StatementType.RETURN;
        returnExpression = e;
    }
    
    public ReturnStatement(){
        this(null);
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"return\n");
        returnExpression.print(writer, tabs+"    ");
    }
}
