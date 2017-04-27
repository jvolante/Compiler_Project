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
import java.util.HashMap;
import java.util.List;
import lowlevel.BasicBlock;
import lowlevel.Function;

/**
 *
 * @author jvolante
 */
public class CompoundStatement extends Statement{
    List<VariableDeclaration> localDeclarations = new ArrayList<>();
    List<Statement> statements = new ArrayList<>();
    
    public CompoundStatement(List<VariableDeclaration> varDec, List<Statement> stmts){
        type = StatementType.COMPOUND;
        localDeclarations = varDec;
        statements = stmts;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"{\n");
        for(VariableDeclaration vd : localDeclarations){
            vd.print(writer, tabs+"    ");
        }
        for(Statement s : statements){
            s.print(writer, tabs+"    ");
        }
        writer.write(tabs + "}\n");
    }

    @Override
    public void genCode(Function f) {
        HashMap st = f.getTable();
        for(VariableDeclaration d : localDeclarations){
            if(!st.containsKey(d.identifier)){
                st.put(d.identifier, null);
            } else {
                throw new CompileError(d.identifier + " already defined in function " + f.getName());
            }
        }
        
        for(Statement s : statements){
            s.genCode(f);
        }
    }
    
}
