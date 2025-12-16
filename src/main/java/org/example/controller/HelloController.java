package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello! Welcome to Currency Converter!";
    }

    @GetMapping("/")
    public String home() {
        return """
                <div stryle="font-family:Arial; padding:20px;">
                    <h1>Currency Converter API</h1>
                    <p>Available endpoints:</p>
                    <ul>
                        <li><a href="/hello">/hello</a>-Welcome message</li>
                        <li><a href="/health">/health</a>-Check if service is running</li>
                    </ul>
                </div>""";
    }
    @GetMapping("/health")
    public String health() {
        return "{\"status\":\"UP\",\"service\":\"Currency Converter\"}";
    }
}
