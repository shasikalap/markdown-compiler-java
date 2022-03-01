package com.mailchimp.markdowncompiler.tokenizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class Tokenizer {
    private static Map<Character, TokenType> rules =  new HashMap<>() {{
        put('\n', TokenType.NEWLINE);
        put('\r', TokenType.CARRIAGE_RETURN);
    }};

    private static Map<Character, TokenType> textRules =  new HashMap<>() {{
        put('[', TokenType.OPEN_SQUARE_BRACE);
    }};

    private List<Token> tokens = new ArrayList<>();
    private boolean specialCharacterIsText = false;

    public ArrayList<Token> tokenize(String markdown) {
        scan(markdown);
        return (ArrayList<Token>) tokens;
    }

    private void scan(String markdown) {
        Token nextToken = null;
        if(markdown == null || markdown.length() == 0) {
            tokens.add(new Token(TokenType.EOF, "", "", 1));
            return;
        } else {
            char ch = markdown.charAt(0);
            if(ch == '\r') {
                markdown = markdown.substring(1);
            } else if(ch == '#') {
                nextToken = headingToken(markdown);
            } else if(ch == '[') {
                nextToken = linkToken(markdown);
            } else if(rules.containsKey(ch)) {
                nextToken = new Token(rules.get(ch), String.valueOf(ch), "", 1);
            }

            if (nextToken == null) {
                specialCharacterIsText = textRules.containsKey(ch)? true: false;
                nextToken = textToken(markdown);
            }

            tokens.add(nextToken);
            scan(markdown.substring(nextToken.getConsumedChars()));
        }
    }

    private Token linkToken(String markdown) {
        Token link = null;
        int pos=1;
        while(pos < markdown.length() && markdown.charAt(pos) != '[' && markdown.charAt(pos) != ']'
                && markdown.charAt(pos) != '\n' ) {
            pos++;
        }
        if(markdown.length()-1 > pos && markdown.charAt(pos) == ']' && markdown.charAt(++pos) == '(') {
            String linkTitle = markdown.substring(1, pos-1);
            int j = pos+1;
            while(pos < markdown.length() && markdown.charAt(pos) != ')' && markdown.charAt(pos) != '\n') {
                pos++;
            }
            if(markdown.charAt(pos) == ')') {
                String href = markdown.substring(j, pos);
                link = new Token(TokenType.LINK, linkTitle,  href, pos+1);
            }
        }
        return link;
    }

    private Token textToken(String markdown) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(i < markdown.length() && !rules.containsKey(markdown.charAt(i)) &&
                (!textRules.containsKey(markdown.charAt(i)) || specialCharacterIsText)) {
            sb.append(markdown.charAt(i));
            i++;
            specialCharacterIsText = false;
        }
        return new Token(TokenType.TEXT, sb.toString(), "", sb.length());
    }

    private Token headingToken(String markdown) {
        Token heading = null;
        int i = 1;
        int headingLevel;
        while(markdown.charAt(i) == '#') {
            i++;
        }
        if (markdown.charAt(i) == ' ' && i <= 6) {
            //strip off spaces in text
            while(markdown.charAt(i) == ' ') {
                i++;
            }
            headingLevel = i-1;
            heading = new Token(TokenType.HEADING, String.valueOf(headingLevel), "", i);
        }
        return heading;
    }
}
