package com.mailchimp.markdowncompiler.parser.nodes;

public class Paragraph extends Node{

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
