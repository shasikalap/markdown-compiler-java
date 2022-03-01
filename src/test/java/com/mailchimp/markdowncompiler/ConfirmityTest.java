package com.mailchimp.markdowncompiler;

import com.mailchimp.markdowncompiler.generator.HtmlGenerator;
import com.mailchimp.markdowncompiler.parser.Parser;
import com.mailchimp.markdowncompiler.parser.nodes.Document;
import com.mailchimp.markdowncompiler.tokenizer.Token;
import com.mailchimp.markdowncompiler.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConfirmityTest
{
    private static final String   RES   = "/testsuite/";
    private static final String[] TESTS =
            {
                    "mailchimp_test_sample_1", "mailchimp_test_sample_2",
            };

    @Test
    public void test() throws IOException
    {
        for (final String name : TESTS)
        {
            final InputStream txtIn = this.getClass().getResourceAsStream(RES + name + ".txt");
            final InputStream cmpIn = this.getClass().getResourceAsStream(RES + name + ".html");
            Assert.notNull(txtIn, "Text input should not be null");
            Assert.notNull(cmpIn, "HTML input should not be null");

            final String text = new String(txtIn.readAllBytes(), StandardCharsets.UTF_8);
            final String compare = new String(cmpIn.readAllBytes(), StandardCharsets.UTF_8);;

            HtmlGenerator htmlGenerator = new HtmlGenerator();
            List<Token> tokens = new Tokenizer().tokenize(text);
            htmlGenerator.visit((Document) new Parser().parse(tokens));
            htmlGenerator.removeEmptyTags();
            final String processed = htmlGenerator.getSb().toString();

            Assert.isTrue(compare.equals(processed), "Test " + name + " failed");
            System.out.println("Test " + name + " passed");
        }
    }
}
