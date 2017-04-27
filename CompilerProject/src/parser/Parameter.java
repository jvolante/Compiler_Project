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
public class Parameter {
    boolean isArray;
    String id;
    
    public Parameter(boolean isArray, String id){
        this.id = id;
        this.isArray = isArray;
    }

    void print(PrintWriter writer, String tabs) throws IOException{
        writer.write(tabs + "int " + id);
        if(isArray){
            writer.write(" [  ]");
        }
        writer.write("\n");
    }
}
