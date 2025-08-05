package org.bcliu.controller;

import org.bcliu.service.serviceImpl.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private AIService aiService;

    @GetMapping("/test-ai")
    public String testAI(@RequestParam String prompt){
        return aiService.generateText(prompt);
    }
}
