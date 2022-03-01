package com.mailchimp.markdowncompiler.service.impl;

import com.mailchimp.markdowncompiler.service.HtmlService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HtmlServiceImplTest {

    @Autowired
    HtmlService htmlService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void lineStartingWithHashAndNoSpace() {
        assertEquals("Line starting with a hash but no space after hash is a paragraph",
                htmlService.markdownToHtml("###Heading"), "<p>###Heading</p>");
    }

    @Test
    public void moreThanSixHashesIsParagraph() {
        assertEquals("Line starting with a hash and has more than six hashes is as paragraph",
                htmlService.markdownToHtml("####### Heading"), "<p>####### Heading</p>");
    }

    @Test
    public void paragraphCanHaveMarkupLink() {
        assertEquals("Paragraph can have Markup Link",
                htmlService.markdownToHtml("Paragraph with [link](www.google.com)"), "<p>Paragraph with <a href=\"www.google.com\">link</a></p>");
    }

    @Test
    public void hashInMiddleIsNotHeading() {
        assertEquals("Hash in the middle of line is not a heading",
                htmlService.markdownToHtml("Paragraph with # is not heading"), "<p>Paragraph with # is not heading</p>");
    }

    @Test
    public void headingTextWithHash() {
        assertEquals("Heading can have hashes in it EX:: # Heading##",
                htmlService.markdownToHtml("# Heading##"), "<h1>Heading##</h1>");
    }

    @Test
    public void headingTextWithOpenSquareBrace()  {
        assertEquals("Heading can have open square brace in it EX:: # Heading[1234",
                htmlService.markdownToHtml("# Heading[1234"), "<h1>Heading[1234</h1>");
    }

    @Test
    public void headingTextCanHaveMarkupLink() {
        assertEquals("Heading can have markup link EX: # Heading with [Link](www.google.com)",
                htmlService.markdownToHtml("# Heading with [Link](www.google.com)"), "<h1>Heading with <a href=\"www.google.com\">Link</a></h1>");
    }

    @Test
    public void emptyLinkISAllowed() {
        assertEquals("Empty Link is allowed",
                htmlService.markdownToHtml("[]()"), "<p><a href=\"\"></a></p>");
    }

    @Test
    public void emptyHrefISAllowed() {
        assertEquals("Markup Link can have empty href",
                htmlService.markdownToHtml("[Link]()"), "<p><a href=\"\">Link</a></p>");
    }

    @Test
    public void doubleOpenSquareBracketsShouldWork() {
        assertEquals("Double Open Square braces should work",
                htmlService.markdownToHtml("[[Link](www.google.com)"), "<p>[<a href=\"www.google.com\">Link</a></p>");
    }

    @After
    public void tearDown() throws Exception {
    }
}