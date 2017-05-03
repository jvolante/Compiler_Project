/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import lowlevel.Attribute;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author jvolante
 */
public class CallExpression extends Expression{
    String id;
    List<Expression> args;
    
    public CallExpression(String id, List<Expression> args){
        this.id = id;
        this.args = args;
    }
    
    @Override
    public void print(PrintWriter writer, String tabs) throws IOException {
        writer.write(tabs+id+"\n");
        for (Expression arg : args){
            arg.print(writer, tabs+"    ");
        }
    }

    @Override
    public void genCode(Function f) {
        ListIterator<Expression> li = args.listIterator(args.size());
        
        while(li.hasPrevious()){
            li.previous().genCode(f);
        }
        
        BasicBlock currBlock = f.getCurrBlock();
        
        li = args.listIterator(args.size());
        
        int numParams = args.size();
        while(li.hasPrevious()){
            Operation o = new Operation(Operation.OperationType.PASS, currBlock);
            o.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, li.previous().getRegNum()));
            o.addAttribute(new Attribute("PARAM_NUM", Integer.toString(--numParams)));
            
            currBlock.appendOper(o);
        }
        
        Operation o = new Operation(Operation.OperationType.CALL, currBlock);
        o.addAttribute(new Attribute("numParams", Integer.toString(args.size())));
        o.setSrcOperand(0, new Operand(Operand.OperandType.STRING, id));
        
        currBlock.appendOper(o);
        
        regNum = f.getNewRegNum();
        o = new Operation(Operation.OperationType.ASSIGN, currBlock);
        o.setSrcOperand(0, new Operand(Operand.OperandType.MACRO, "RetReg"));
        o.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, regNum));
        
        currBlock.appendOper(o);
    }
    
}
