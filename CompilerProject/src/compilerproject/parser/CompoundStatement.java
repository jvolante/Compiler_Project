/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jvolante
 */
class CompoundStatement extends Statement{
    List<VariableDeclaration> localDeclarations = new ArrayList<>();
    List<Statement> statements = new ArrayList<>();
    
    public CompoundStatement(){
        type = StatementType.COMPOUND;
    }

    @Override
    public void print(BufferedWriter writer, String tabs) throws IOException {
        writer.write(tabs+"{\n");
        for(VariableDeclaration vd : localDeclarations){
            vd.print(writer, tabs+"    ");
        }
        for(Statement s : statements){
            s.print(writer, tabs+"    ");
        }
        writer.write(tabs + "}\n");
    }
}
