/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author jvolante
 */
public class AssignExpression extends Expression {
    IdentifierExpression id;
    Expression value;
    
    public AssignExpression(String ident, Expression v){
        this(new IdentifierExpression(ident), v);
    }
    
    public AssignExpression(IdentifierExpression ident, Expression v){
        if(ident == null || v == null){
            throw new InvalidParameterError();
        }
        id = ident;
        value = v;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"=\n");
        id.print(writer, tabs);
        value.print(writer, "    "+tabs);
    }   

    @Override
    public void genCode(Function f) {
        Operand dest = null;
        Operation o = null;
        BasicBlock currBlock = f.getCurrBlock();
        
        value.genCode(f);
        
        if(!id.isGlobal(f)){
            id.genCode(f);
            o = new Operation(Operation.OperationType.ASSIGN, currBlock);
            dest = new Operand(Operand.OperandType.REGISTER, id.getRegNum());
            o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, value.getRegNum()));
            o.setDestOperand(0, dest);
            regNum = id.getRegNum();
        } else {
            o = new Operation(Operation.OperationType.STORE_I, currBlock);
            dest = new Operand(Operand.OperandType.STRING, id.id);
            o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, value.getRegNum()));
            o.setSrcOperand(1, dest);
            regNum = value.getRegNum();
        }
        
        currBlock.appendOper(o);
    }
    
}
