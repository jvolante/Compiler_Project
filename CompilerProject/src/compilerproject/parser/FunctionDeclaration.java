/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
import compilerproject.scanner.TokenType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    public FunctionDeclaration(DataType t, String id, List<Parameter> p, CompoundStatement cs){
        decType = DeclarationType.FUNCTION_DECLARATION;
        identifier = id;
        params = p;
        compoundStatement = cs;
        type = t;
    }
    
    public FunctionDeclaration(Token t, String id, List<Parameter> p, CompoundStatement cs){
        decType = DeclarationType.FUNCTION_DECLARATION;
        identifier = id;
        params = p;
        compoundStatement = cs;
        
        if(t.getTokenType() == TokenType.INT){
            type = DataType.INT;
        } else {
            type = DataType.VOID;
        }
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs + identifier + "\n");
        for(Parameter p : params){
            p.print(writer, tabs + "    ");
        }
        compoundStatement.print(writer, tabs+"    ");
    }
    
}
