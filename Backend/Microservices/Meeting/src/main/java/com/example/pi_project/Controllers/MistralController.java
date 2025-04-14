package com.example.pi_project.Controllers;

import com.example.pi_project.Services.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mistral")
@CrossOrigin(origins = "http://localhost:4200")
public class MistralController {

    @Autowired
    private OllamaService ollamaService;

    @PostMapping("/ask")
    public ResponseEntity<String> askMistral(@RequestBody Map<String, String> request) {
        String response = ollamaService.askMistral(request.get("prompt"));
        return ResponseEntity.ok(response);
    }
}