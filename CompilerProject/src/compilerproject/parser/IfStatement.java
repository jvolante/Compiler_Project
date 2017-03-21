/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.parser;

import java.io.BufferedWriter;
import java.io.IOException;

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
    public void print(BufferedWriter writer, String tabs) throws IOException {
        writer.write(tabs+"if");
        expr.print(writer, tabs+"    ");
        thenStmt.print(writer, tabs+"    ");
        elseStmt.print(writer, tabs+"    ");
    }
}
