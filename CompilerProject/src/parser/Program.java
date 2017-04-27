/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import lowlevel.CodeItem;
import lowlevel.Function;

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
    
    public CodeItem genLLCode(){
        CodeItem result = null;
        CodeItem current = null;
        CodeItem next;
        
        for(Declaration d : declarations){
            next = d.genCode();
            if(current == null){
                current = next;
            } else {
                current.setNextItem(next);
                current = next;
            }
            if(result == null){
                result = current;
            }
        }
        return result;
    }
}
