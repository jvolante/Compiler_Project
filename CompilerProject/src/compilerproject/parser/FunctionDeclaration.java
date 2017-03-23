/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
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
    
    public FunctionDeclaration(String id, List<Parameter> p, CompoundStatement cs){
        decType = DeclarationType.FUNCTION_DECLARATION;
        identifier = id;
        params = p;
        compoundStatement = cs;
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
