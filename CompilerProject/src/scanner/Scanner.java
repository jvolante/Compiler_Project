/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Interface for Scanners which retrieve tokens to be parsed
 * @author Jackson Volante
 */
public interface Scanner {
    
    /**
     * Returns the next accessible token.
     * @return: The next accessible token.
     */
    public Token getNextToken() throws IOException;
    
    /**
     * Returns the next accessible token without consuming it.
     * @return: The next accessible token.
     */
    public Token viewNextToken();
}
