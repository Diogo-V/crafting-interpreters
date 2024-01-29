package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.TokenType.*;
import src.Token;
import src.Lox;


class Scanner {
  private final String source;  // Stores the raw source code as a string
  private final List<Token> tokens = new ArrayList<>();
  private int start = 0;  // Points to the first character in the lexeme being scanned
  private int current = 0;  // Points to the character currently being considered
  private int line = 1;  // Tracks which line the current lexeme is on

  private static final Map<String, TokenType> keywords;  // Stores the reserved keywords
  
  static {  // Initializes the keywords map
    keywords = new HashMap<>();
    keywords.put("and",    AND);
    keywords.put("class",  CLASS);
    keywords.put("else",   ELSE);
    keywords.put("false",  FALSE);
    keywords.put("for",    FOR);
    keywords.put("fun",    FUN);
    keywords.put("if",     IF);
    keywords.put("nil",    NIL);
    keywords.put("or",     OR);
    keywords.put("print",  PRINT);
    keywords.put("return", RETURN);
    keywords.put("super",  SUPER);
    keywords.put("this",   THIS);
    keywords.put("true",   TRUE);
    keywords.put("var",    VAR);
    keywords.put("while",  WHILE);
  }

  Scanner (String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    while(!isAtEnd()) {
      // We are at the beginning of the next lexeme
      start = current;
      scanToken();
    }

    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }

  private boolean isAtEnd() {
    return current >= source.length();
  }
  
  private void scanToken() {
    char c = advance();
    switch (c) {

      // Single-character tokens
      case '(': addToken(LEFT_PAREN); break;
      case ')': addToken(RIGHT_PAREN); break;
      case '{': addToken(LEFT_BRACE); break;
      case '}': addToken(RIGHT_BRACE); break;
      case ',': addToken(COMMA); break;
      case '.': addToken(DOT); break;
      case '-': addToken(MINUS); break;
      case '+': addToken(PLUS); break;
      case ';': addToken(SEMICOLON); break;
      case '*': addToken(STAR); break;

      // One or two character tokens
      case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
      case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
      case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
      case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
  
      // Comments and division. Since both share the same initial token, we need to check for the second character.
      // If we are dealing with comments, we also need to ignore everything until the end of the line
      case '/':
        if (match('/')) {  // If we find a comment, we ignore everything until the end of the line
          while (peek() != '\n' && !isAtEnd()) advance();
        } else if (match('*')) {

          // Multiline comment
          while (peek() != '*' && peekNext() != '/' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
          }
          
          // Unterminated comment
          if (isAtEnd()) {
            Lox.error(line, "Unterminated comment.");
            return;
          }

          // The closing */
          advance();
          advance();
        } else {
          addToken(SLASH);
        }
        break;

      // Whitespace, newlines and tabs <- ignored
      case ' ':
      case '\r':
      case '\t':
        break;
      case '\n':
        line++;
        break;

      // String literals
      case '"':
        string();
        break;

      // In case we find a token that is not matched by our syntax
      default:
        if (isDigit(c)) {  // If we find a digit, we are dealing with a number
          number();
        } else if (isAlpha(c)) {
          identifier();
        } else {
          Lox.error(line, "Unexpected character.");
        }
        break;
    }
  }

  private void identifier() {
    while (isAlphaNumeric(peek())) advance();
    
    // See if the identifier is a reserved word
    String text = source.substring(start, current);
    TokenType type = keywords.get(text);
    if (type == null) type = IDENTIFIER;

    addToken(type);
  }

  private boolean isAlpha(char c) {  // Checks if the character is a letter or an underscore
    return (c >= 'a' && c <= 'z') ||
           (c >= 'A' && c <= 'Z') ||
           c == '_';
  }

  private boolean isAlphaNumeric(char c) {  // Checks if the character can be considered part of an identifier
    return isAlpha(c) || isDigit(c);
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private void number() {
    while (isDigit(peek())) advance();

    // Look for a fractional part
    if (peek() == '.' && isDigit(peekNext())) {  // Needs the peekNext() because we don't want to consume the "."
      // Consume the "."
      advance();

      while (isDigit(peek())) advance();
    }

    addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
  }

  private char peekNext() {  // Returns the next's next character in the source file without consuming it
    if (current + 1 >= source.length()) return '\0';
    return source.charAt(current + 1);
  }
  
  private void string() {
    while (peek() != '"' && !isAtEnd()) {
      if (peek() == '\n') line++;  // Support multiline strings
      advance();
    }

    // Unterminated string
    if (isAtEnd()) {
      Lox.error(line, "Unterminated string.");
      return;
    }

    // The closing "
    advance();

    // Trim the surrounding quotes
    String value = source.substring(start + 1, current - 1);
    addToken(STRING, value);
  }

  private char peek() {  // Returns the next character in the source file without consuming it
    if (isAtEnd()) return '\0';
    return source.charAt(current);
  }

  private boolean match(char expected) {  // Checks if the current character is the expected one
    if (isAtEnd()) return false;
    if (source.charAt(current) != expected) return false;

    current++;  // We need to increment the current pointer because we are consuming the character
    return true;
  }

  private char advance() {  // Consumes the next character in the source file and returns it
    current++;
    return source.charAt(current -1);
  }
  
  private void addToken(TokenType type) {  // Adds a token to the list
    addToken(type, null);
  }

  private void addToken(TokenType type, Object literal) {  // Adds a token to the list
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }

}
