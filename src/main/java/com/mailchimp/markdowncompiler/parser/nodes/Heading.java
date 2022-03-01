package com.mailchimp.markdowncompiler.parser.nodes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Heading extends Node{
    int headingLevel;

    public int getHeadingLevel() {
        return headingLevel;
    }

    public void setHeadingLevel(int headingLevel) {
        this.headingLevel = headingLevel;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
