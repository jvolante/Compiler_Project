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
public class ItterationStatement extends Statement {
    Expression loopCondition;
    Statement statement;
    
    public ItterationStatement(Expression e, Statement s){
        type = StatementType.ITERATION;
        loopCondition = e;
        statement = s;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"while\n");
        loopCondition.print(writer, tabs+"    ");
        statement.print(writer, tabs+"    ");
    }

    @Override
    public void genCode(Function f) {
        BasicBlock whileBlock = new BasicBlock(f);
        BasicBlock postBlock = new BasicBlock(f);
        BasicBlock currBlock = f.getCurrBlock();
        
        loopCondition.genCode(f);
        
        Operation o = new Operation(Operation.OperationType.BEQ, currBlock);
        o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, loopCondition.getRegNum()));
        o.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 0));
        o.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()));
        
        currBlock.appendOper(o);
        
        f.appendToCurrentBlock(whileBlock);
        f.setCurrBlock(whileBlock);
        
        statement.genCode(f);
        
        loopCondition.genCode(f);
        o = new Operation(Operation.OperationType.BNE, whileBlock);
        o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, loopCondition.getRegNum()));
        o.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 0));
        o.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, whileBlock.getBlockNum()));
        whileBlock.appendOper(o);
        
        f.appendToCurrentBlock(postBlock);
        f.setCurrBlock(postBlock);
    }
}
