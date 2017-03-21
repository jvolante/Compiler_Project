/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author jvolante
 */
public class CallExpression extends Expression{
    String id;
    List<Expression> args;
    
    public CallExpression(String id, List<Expression> args){
        this.id = id;
        this.args = args;
    }
    
    @Override
    public void print(BufferedWriter writer, String tabs) throws IOException {
        writer.write(tabs+id);
        for (Expression arg : args){
            arg.print(writer, tabs+"    ");
        }
    }
    
}
