package com.mailchimp.markdowncompiler.parser.nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private int tokenCount;

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }

    public abstract void accept(Visitor visitor);
}