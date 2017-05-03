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
 * @author jtdeane
 */
public class IfStatement extends Statement {

    Expression expr;
    Statement thenStmt;
    Statement elseStmt;

    public IfStatement (Expression express, Statement stmt) {
        this (express, stmt, null);
    }

    public IfStatement (Expression express, Statement stmt1, Statement stmt2) {
        expr = express;
        thenStmt = stmt1;
        elseStmt = stmt2;
    }

    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+"if\n");
        expr.print(writer, tabs+"    ");
        thenStmt.print(writer, tabs+"    ");
        
        if(elseStmt != null){
            elseStmt.print(writer, tabs+"    ");
        }
    }

    @Override
    public void genCode(Function f) {
        // make blocks that we need
        BasicBlock thenBlock = new BasicBlock(f);
        BasicBlock elseBlock = new BasicBlock(f);
        BasicBlock postBlock = new BasicBlock(f);
        
        expr.genCode(f);
        
        BasicBlock currBlock = f.getCurrBlock();
        
        // conditional branch
        Operation o = new Operation(Operation.OperationType.BNE, currBlock);
        
        o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, expr.getRegNum()));
        o.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 0));
        o.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, 
                elseStmt == null ? postBlock.getBlockNum() : elseBlock.getBlockNum()));
        
        
        f.appendToCurrentBlock(thenBlock);
        f.setCurrBlock(thenBlock);
        thenStmt.genCode(f);
        
        f.appendToCurrentBlock(postBlock);
        
        if(elseStmt != null){
            
            f.setCurrBlock(elseBlock);
            elseStmt.genCode(f);
            o = new Operation(Operation.OperationType.JMP, elseBlock);
            o.setSrcOperand(0, new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()));
            elseBlock.appendOper(o);
            
            f.appendUnconnectedBlock(elseBlock);
        }
        
        f.setCurrBlock(postBlock);
    }
}
