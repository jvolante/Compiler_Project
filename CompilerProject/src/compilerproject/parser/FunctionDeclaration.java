/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jvolante
 */
public class FunctionDeclaration extends Declaration {
    protected String identifier;
    protected List<Parameter> params = new ArrayList<>();
    CompoundStatement compoundStatement;
    
    public FunctionDeclaration(String id, List<Parameter> p, CompoundStatement cs){
        decType = DeclarationType.FUNCTION_DECLARATION;
        identifier = id;
        params = p;
        compoundStatement = cs;
    }
}
