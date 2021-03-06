package com.mailchimp.markdowncompiler.controller;

import com.mailchimp.markdowncompiler.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MarkdownController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private HtmlService htmlService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @PostMapping("/view")
    public ModelAndView view(@RequestParam String markdown) {
        ModelAndView modelAndView = new ModelAndView("view");
        String htmlContent = htmlService.markdownToHtml(markdown);
        modelAndView.addObject("markdown", markdown);
        modelAndView.addObject("htmlContent", htmlContent);
        return modelAndView;
    }
}
