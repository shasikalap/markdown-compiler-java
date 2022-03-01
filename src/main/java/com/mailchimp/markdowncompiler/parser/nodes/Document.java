package com.mailchimp.markdowncompiler.parser.nodes;

public class Document extends Node{

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
