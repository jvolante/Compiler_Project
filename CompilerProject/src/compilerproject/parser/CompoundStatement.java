/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

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
}
