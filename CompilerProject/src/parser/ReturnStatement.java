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
public class ReturnStatement extends Statement {
    Expression returnExpression;
    
    public ReturnStatement(Expression e){
        type = StatementType.RETURN;
        returnExpression = e;
    }
    
    public ReturnStatement(){
        this(null);
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"return\n");
        if(returnExpression != null){
            returnExpression.print(writer, tabs+"    ");
        }
    }

    @Override
    public void genCode(Function f) {
        BasicBlock currBlock = f.getCurrBlock();
        Operation o;
        
        if(returnExpression != null){
            returnExpression.genCode(f);
            
            o = new Operation(Operation.OperationType.ASSIGN, currBlock);
            o.setDestOperand(0, new Operand(Operand.OperandType.MACRO, "RetReg"));
            o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, returnExpression.getRegNum()));
            f.getCurrBlock().appendOper(o);
        }
        
        o = new Operation(Operation.OperationType.JMP, currBlock);
        o.setSrcOperand(0, new Operand(Operand.OperandType.BLOCK, f.getReturnBlock().getBlockNum()));
        
        currBlock.appendOper(o);
    }
}
