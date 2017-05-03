/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import scanner.Token;
import scanner.TokenType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

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
    
    public CodeItem genCode(){
        Function f = new Function(type == DataType.INT ? Data.TYPE_INT : Data.TYPE_VOID, identifier);
        
        FuncParam first = null;
        FuncParam current = null;
        
        for(Parameter p : params){
            if(first == null){
                current = first = new FuncParam(Data.TYPE_INT, p.id);
            } else{
                current.setNextParam(new FuncParam(Data.TYPE_INT, p.id));
                current = current.getNextParam();
                f.getTable().put(p.id, f.getNewRegNum());
            }
            
            f.getTable().put(p.id, f.getNewRegNum());
        }
        
        f.setFirstParam(first);
        f.createBlock0();
        
        BasicBlock block1 = new BasicBlock(f);
        
        f.appendBlock(block1);
        
        f.setCurrBlock(block1);
        
        compoundStatement.genCode(f);
        
        f.appendBlock(f.getReturnBlock());
        
        if(f.getFirstUnconnectedBlock() != null){
            f.appendBlock(f.getFirstUnconnectedBlock());
        }
        
        return f;
    }
}
