/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

/**
 *
 * @author jvolante
 */
public class ExpressionStatement extends Statement {
    Expression expr;
    
    public ExpressionStatement(){
        type = StatementType.EXPRESSION;
    }
}
