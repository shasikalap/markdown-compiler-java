package com.mailchimp.markdowncompiler.parser;

import com.mailchimp.markdowncompiler.parser.nodes.*;
import com.mailchimp.markdowncompiler.tokenizer.Token;
import com.mailchimp.markdowncompiler.tokenizer.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Parser {
    private Node block;
    private int headingLevel;

    public Node parse(List<Token> tokens) {
        return parseDocument(tokens);
    }

    private Node parseDocument(List<Token> tokens) {
        Document document = new Document();
        while (tokens != null && tokens.size() > 0) {
            List<Node> children = new ArrayList<>();
            parseBlock(tokens, children);
            document.addChild(block);
        }
        return document;
    }

    private void parseBlock(List<Token> tokens, List<Node> children) {
        int tokensConsumed = 1;
        Token token = tokens.get(0);
        if(token.getType() == TokenType.EOF || token.getType() == TokenType.NEWLINE) {
            block = (headingLevel > 0) ? new Heading(headingLevel) : new Paragraph();
            headingLevel = 0;
            block.setChildren(children);
            tokens.remove(0);
            return;
        } else if (token.getType() == TokenType.TEXT) {
            block = new Text(token.getValue());
        } else if (token.getType() == TokenType.HEADING) {
            headingLevel = Integer.valueOf(token.getValue());
        } else if (token.getType() == TokenType.LINK) {
            block = new Link(token.getLinkRef(), token.getValue());
        }

        if (token.getType() != TokenType.HEADING) {
            children.add(block);
        }

        tokens.subList(0, tokensConsumed).clear();
        parseBlock(tokens, children);
    }
}