package com.mailchimp.markdowncompiler.parser.nodes;

public abstract class Visitor {
    public abstract void visit(Document document);
    public abstract void visit(Heading heading);
    public abstract void visit(Text text);
    public abstract void visit(Paragraph paragraph);
    public abstract void visit(Link link);
}
