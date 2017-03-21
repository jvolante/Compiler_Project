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
public class IdentifierExpression extends Expression{
    String id;

    @Override
    public void print(BufferedWriter writer, String tabs) throws IOException {
        writer.write(tabs+id);
    }
}
