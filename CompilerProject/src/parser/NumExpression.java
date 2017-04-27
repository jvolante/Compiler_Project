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
public class NumExpression extends Expression{
    int value;
    
    public NumExpression (int value){
        this.value = value;
    }
    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+value+"\n");
    }

    @Override
    public void genCode(Function f) {
        regNum = f.getNewRegNum();
        BasicBlock currBlock = f.getCurrBlock();
        Operation o = new Operation(Operation.OperationType.ASSIGN, currBlock);
        o.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, regNum));
        o.setSrcOperand(0, new Operand(Operand.OperandType.INTEGER, value));
        currBlock.appendOper(o);
    }
    
}
