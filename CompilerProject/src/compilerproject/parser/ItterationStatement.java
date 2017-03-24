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
public class ItterationStatement extends Statement {
    Expression loopCondition;
    Statement statement;
    
    public ItterationStatement(Expression e, Statement s){
        type = StatementType.ITERATION;
        loopCondition = e;
        statement = s;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"while\n");
        loopCondition.print(writer, tabs+"    ");
        statement.print(writer, tabs+"    ");
    }
}
