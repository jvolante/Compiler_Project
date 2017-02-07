/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject.scanner;

/**
 *
 * @author Jackson Volante
 */
public enum CMinusScannerState {
    START,
    FIRST_FORWARD_SLASH,
    COMMENT_FIRST_STAR,
    COMMENT_INTERIOR,
    COMMENT_SECOND_STAR,
    COMMENT_END_FORWARD_SLASH,
    DIGIT,
    IDENTIFIER,
    FOUND_GREATER_THAN,
    FOUND_LESS_THAN,
    FOUND_EQ,
    FOUND_BANG
}
