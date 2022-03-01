package com.mailchimp.markdowncompiler.service.impl;

import com.mailchimp.markdowncompiler.generator.HtmlGenerator;
import com.mailchimp.markdowncompiler.parser.nodes.*;
import com.mailchimp.markdowncompiler.service.HtmlService;
import com.mailchimp.markdowncompiler.parser.Parser;
import com.mailchimp.markdowncompiler.tokenizer.Token;
import com.mailchimp.markdowncompiler.tokenizer.Tokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HtmlServiceImpl implements HtmlService {

    @Autowired
    private HtmlGenerator htmlGenerator;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    private Parser parser;

    @Override
    public String markdownToHtml(String markdown) {
        List<Token> tokens = tokenizer.tokenize(markdown);
        htmlGenerator.visit((Document) parser.parse(tokens));
        htmlGenerator.removeEmptyTags();
        return htmlGenerator.getSb().toString();
    }
}
