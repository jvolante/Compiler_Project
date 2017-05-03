/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiler.CMinusCompiler;
import java.io.IOException;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author jtdeane
 */
public class IdentifierExpression extends Expression{
    String id;
    Expression element;
    
    public IdentifierExpression(String ident, Expression e){
        id = ident;
        element = e;
    }
    
    public IdentifierExpression(String ident){
        this(ident, null);
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs + id + "\n");
        if(element != null){
            writer.write(tabs + "[\n");
            element.print(writer, tabs+"    ");
            writer.write(tabs + "]\n");
        }
    }

    @Override
    public void genCode(Function f) {
        BasicBlock currBlock = f.getCurrBlock();
        
        if(f.getTable().containsKey(id)){
            regNum = (Integer)f.getTable().get(id);
        } else if (CMinusCompiler.globalHash.containsKey(id)){
            regNum = f.getNewRegNum();
            Operation o = new Operation(Operation.OperationType.LOAD_I, currBlock);
            o.setSrcOperand(0, new Operand(Operand.OperandType.STRING, id));
            o.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, regNum));
            currBlock.appendOper(o);
            
        } else {
            throw new Error("No such var " + id);
        }
    }
    
    public boolean isGlobal(Function f){
        if(!f.getTable().containsKey(id) && !CMinusCompiler.globalHash.containsKey(id)){
            throw new Error("No such var " + id);
        }
        return !f.getTable().containsKey(id) && CMinusCompiler.globalHash.containsKey(id);
    }
}
