package src;

import src.TokenType;


class Token {
  final TokenType type;
  final String lexeme;
  final Object literal;  // Living runtime object converted from a lexeme that is a Literal
  final int line; 

  Token(TokenType type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line; 
  }

  public String toString() {
    return type + " " + lexeme + " " + literal;
  }
}
