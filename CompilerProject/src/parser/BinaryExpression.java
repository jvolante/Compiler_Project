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
import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;

/**
 *
 * @author jvolante
 */
public class BinaryExpression extends Expression{
    Expression left;
    Expression right;
    OpType op;
    
    public enum OpType{
        PLUS, MINUS, GT, LT, GTEQ, LTEQ, NEQ, EQ, MUL, DIV
    }
    
    public BinaryExpression(Expression l, Expression r, OpType o){
        left = l;
        right = r;
        op = o;
    }
    
    public BinaryExpression(Expression l, Expression r, TokenType t) throws ParseException{
        this(l, r, getOpType(t));
    }
    
    private static OpType getOpType(TokenType t) throws ParseException{
        switch(t){
            case PLUS:
                return OpType.PLUS;
            case MINUS:
                return OpType.MINUS;
            case GREATERTHAN:
                return OpType.GT;
            case LESSTHAN:
                return OpType.LT;
            case GREATERTHANEQ:
                return OpType.GTEQ;
            case LESSTHANEQ:
                return OpType.LTEQ;
            case NOTEQ:
                return OpType.NEQ;
            case EQUALS:
                return OpType.EQ;
            case MULTIPLY:
                return OpType.MUL;
            case DIVIDE:
                return OpType.DIV;
            default:
                throw new ParseException("Unexpected TokenType: " + t);
        }
    }
    
    @Override
    public void print(PrintWriter writer, String tabs) throws IOException{
        writer.write(tabs+getString(op)+"\n");
        left.print(writer, tabs+"    ");
        right.print(writer, tabs+ "    ");
    }
    
    private static String getString(OpType op){
        switch(op){
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case GT:
                return ">";
            case LT:
                return "<";
            case GTEQ:
                return ">=";
            case LTEQ:
                return "<=";
            case NEQ:
                return "!=";
            case EQ:
                return "==";
            case MUL:
                return "*";
            case DIV:
                return "/";
        }
        return null;
    }
    
        
    @Override
    public void genCode(Function fun){
        regNum = fun.getNewRegNum();
        
        left.genCode(fun);
        right.genCode(fun);
        
        Operation operation = new Operation(getOperationType(op), fun.getCurrBlock());
        operation.setDestOperand(0, new Operand(OperandType.REGISTER, regNum));
        operation.setSrcOperand(0, new Operand(OperandType.REGISTER, left.getRegNum()));
        operation.setSrcOperand(1, new Operand(OperandType.REGISTER, right.getRegNum()));
        
        fun.getCurrBlock().appendOper(operation);
    }
    
    public Operation.OperationType getOperationType(OpType op){
        switch(op){
            case PLUS:
                return Operation.OperationType.ADD_I;
            case MINUS:
                return Operation.OperationType.SUB_I;
            case GT:
                return Operation.OperationType.GT;
            case LT:
                return Operation.OperationType.LT;
            case GTEQ:
                return Operation.OperationType.GTE;
            case LTEQ:
                return Operation.OperationType.LTE;
            case NEQ:
                return Operation.OperationType.NOT_EQUAL;
            case EQ:
                return Operation.OperationType.EQUAL;
            case MUL:
                return Operation.OperationType.MUL_I;
            case DIV:
                return Operation.OperationType.DIV_I;
        }
        return null;
    }
}
