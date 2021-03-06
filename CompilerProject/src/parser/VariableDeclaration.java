/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import lowlevel.CodeItem;
import lowlevel.Data;

/**
 *
 * @author jvolante
 */
public class VariableDeclaration extends Declaration{
    protected boolean isArray;
    protected long numElements;
    protected String identifier;
    
    public VariableDeclaration(boolean isArray, long numElements, String id){
        decType = DeclarationType.VARIABLE_DECLARATION;
        this.isArray = isArray;
        this.numElements = numElements;
        this.identifier = id;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException{
        writer.write(tabs + identifier);
        if(isArray){
            writer.write(" [ " + numElements + " ]");
        }
        writer.write("\n");
    }

    @Override
    public CodeItem genCode() {
        if(isArray){
            throw new UnsupportedOperationException("Arrays not supported");
        } else {
            CMinusCompiler.globalHash.put(identifier, type);
            return new Data(Data.TYPE_INT, identifier);
        }
    }
}
