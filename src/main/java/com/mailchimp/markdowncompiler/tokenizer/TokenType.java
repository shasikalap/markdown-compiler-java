package com.mailchimp.markdowncompiler.tokenizer;

public enum TokenType {
    NEWLINE,
    TEXT,
    EOF,
    CARRIAGE_RETURN,
    LINK,
    HEADING,
    OPEN_SQUARE_BRACE;
}
