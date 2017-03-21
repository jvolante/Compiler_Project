/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;

/**
 *
 * @author jvolante
 */
class VariableDeclaration extends Declaration{
    protected boolean isArray;
    protected long numElements;
    protected Token identifier;
    
    public VariableDeclaration(){
        decType = DeclarationType.VARIABLE_DECLARATION;
    }
}
