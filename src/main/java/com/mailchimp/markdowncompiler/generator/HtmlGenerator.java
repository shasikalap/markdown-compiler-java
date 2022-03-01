package com.mailchimp.markdowncompiler.generator;

import com.mailchimp.markdowncompiler.parser.nodes.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class HtmlGenerator extends Visitor {

    private StringBuffer sb = new StringBuffer();

    public StringBuffer getSb() {
        return sb;
    }

    public void setSb(StringBuffer sb) {
        this.sb = sb;
    }

    @Override
    public void visit(Document document) {
        visitChildNodes(document, true);
    }

    @Override
    public void visit(Heading heading) {
        sb.append("<h" + heading.getHeadingLevel() + ">");
        visitChildNodes(heading, false);
        sb.append("</h" + heading.getHeadingLevel() + ">");
    }

    @Override
    public void visit(Text text) {
        sb.append(text.getLiteral());
    }

    @Override
    public void visit(Paragraph paragraph) {
        sb.append("<p>");
        visitChildNodes(paragraph, false);
        sb.append("</p>");
    }

    @Override
    public void visit(Link link) {
        sb.append("<a href=\"" + link.getHref() +"\">" + link.getTitle() + "</a>");
    }

    private void visitChildNodes(Node node, boolean insertLine) {
        int lastChildIndex = node.getChildren().size();
        for (int i = 0; i < lastChildIndex; i++) {
            node.getChildren().get(i).accept(this);
            if (insertLine && i != lastChildIndex-1) {
                sb.append('\n');
            }
        }
    }

    public void removeEmptyTags() {
        Pattern p = Pattern.compile("<p></p>");
        replaceAll(sb, p, "");
        p = Pattern.compile("</p>\n<p>");
        replaceAll(sb, p, "\n");
    }

    private void replaceAll(StringBuffer sb, Pattern pattern, String replacement) {
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }
}
