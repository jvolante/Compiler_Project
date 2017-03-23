/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
import java.io.BufferedWriter;

/**
 *
 * @author jvolante
 */
class VariableDeclaration extends Declaration{
    protected boolean isArray;
    protected long numElements;
    protected String identifier;
    
    public VariableDeclaration(boolean isArray, long numElements, String id){
        decType = DeclarationType.VARIABLE_DECLARATION;
        this.isArray = isArray;
        this.numElements = numElements;
        this.identifier = id;
    }

    void print(BufferedWriter writer, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
