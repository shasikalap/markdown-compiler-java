package com.mailchimp.markdowncompiler.parser.nodes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Link extends Node{
    private String href;
    private String title;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
