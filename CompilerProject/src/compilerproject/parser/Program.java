/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jvolante
 */
public class Program {
    List<Declaration> declarations = new ArrayList<>();
    
    public Program(List<Declaration> decl){
        declarations = decl;
    }
    
    public void print(PrintWriter writer, String tabs) throws IOException{
        for(Declaration d : declarations){
            d.print(writer, tabs);
        }
    }
}
