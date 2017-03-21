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
public class ItterationStatement extends Statement {
    Expression loopCondition;
    Statement statement;
    
    public ItterationStatement(){
        type = StatementType.ITERATION;
    }

    @Override
    public void print(BufferedWriter writer, String tabs) throws IOException {
        writer.write(tabs+"while");
        loopCondition.print(writer, tabs+"    ");
        statement.print(writer, tabs+"    ");
    }
}
