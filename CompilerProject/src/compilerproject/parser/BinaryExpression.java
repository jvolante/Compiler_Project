/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import compilerproject.scanner.Token;
import java.io.BufferedWriter;
import java.io.IOException;

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
    
    public void print(BufferedWriter writer, String tabs) throws IOException{
        writer.write(tabs+getString(op));
        left.print(writer, tabs+"    ");
        right.print(writer, tabs+ "    ");
    }
    
    private String getString(OpType op){
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
