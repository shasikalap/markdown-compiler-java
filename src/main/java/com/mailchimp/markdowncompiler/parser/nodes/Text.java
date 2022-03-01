package com.mailchimp.markdowncompiler.parser.nodes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Text extends Node{
    private String literal;

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String text) {
        this.literal = text;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
