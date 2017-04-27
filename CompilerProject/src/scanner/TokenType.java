/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 *
 * @author Jackson Volante
 */
public enum TokenType {
    IF,
    ELSE,
    INT,
    VOID,
    RETURN,
    WHILE,
    ASSIGN,
    PLUS (Group.ADDOP),
    MINUS (Group.ADDOP),
    MULTIPLY (Group.MULOP),
    DIVIDE (Group.MULOP),
    EQUALS (Group.RELOP),
    LESSTHAN (Group.RELOP),
    GREATERTHAN (Group.RELOP),
    LESSTHANEQ (Group.RELOP),
    GREATERTHANEQ (Group.RELOP),
    NOTEQ (Group.RELOP),
    RPAREN,
    LPAREN,
    RSQBRACE,
    LSQBRACE,
    RBRACE,
    LBRACE,
    SEMICOLON,
    COMMA,
    NUMBER,
    IDENTIFIER,
    COMMENT,
    ERROR;

    private Group group;

    TokenType(Group group) {
        this.group = group;
    }
    
    TokenType() {
        this.group = null;
    }
    
    public boolean isInGroup(Group group) {
        return this.group == group;
    }

    public enum Group {
        RELOP,
        ADDOP,
        MULOP;
    }
}