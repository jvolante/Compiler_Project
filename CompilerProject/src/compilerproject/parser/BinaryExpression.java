/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
import compilerproject.scanner.TokenType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
        writer.write(tabs+getString(op));
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
}
