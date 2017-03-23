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
class Parameter {
    int num;
    int[] numArray;
    boolean isArray;
    String id;
    
    public Parameter(int i, String s){
        num = i;
        id = s;
        isArray = false;
        numArray = null;
    }
    
    public Parameter(int[] array, String s){
        numArray = array;
        id = s;
        isArray = true;
    }

    void print(BufferedWriter writer, String tabs) throws IOException{
        if(isArray){
            writer.write(tabs + "{ ");
            for(int i : numArray){
                writer.write(i + ", ");
            }
            writer.write("}\n");
        } else {
            writer.write(tabs + num + "\n");
        }
    }
}
