package com.mailchimp.markdowncompiler.tokenizer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TokenizerTest {

    @Autowired
    Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void nullMarkdownReturnsEOFToken() {
        List<Token> tokens = tokenizer.tokenize(null);
        assertNotNull(tokens);
        assertEquals(tokens.size(), 1);
        assertTrue(tokens.get(0).getType().equals(TokenType.EOF));
    }

    @Test
    void emptyMarkdownReturnsEOFToken() {
        List<Token> tokens = tokenizer.tokenize("");
        assertNotNull(tokens);
        assertEquals(tokens.size(), 1);
        assertTrue(tokens.get(0).getType().equals(TokenType.EOF));
    }
}