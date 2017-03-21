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
public abstract class Declaration {
    protected DeclarationType decType;
    protected Token type;
    
    public enum DeclarationType{
        FUNCTION_DECLARATION, VARIABLE_DECLARATION
    }
}
