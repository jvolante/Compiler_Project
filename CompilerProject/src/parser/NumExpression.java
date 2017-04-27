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
public class NumExpression extends Expression{
    int value;
    
    public NumExpression (int value){
        this.value = value;
    }
    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+value+"\n");
    }
    
}
