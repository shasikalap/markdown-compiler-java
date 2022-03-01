package com.mailchimp.markdowncompiler.tokenizer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Token {
    private TokenType type;
    private String value;
    private String linkRef;
    private int consumedChars;

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLinkRef() {
        return linkRef;
    }

    public void setLinkRef(String linkRef) {
        this.linkRef = linkRef;
    }

    public int getConsumedChars() {
        return consumedChars;
    }

    public void setConsumedChars(int consumedChars) {
        this.consumedChars = consumedChars;
    }
}
