package tech.lastbox.structo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lastbox.structo.services.OllamaService;
import tech.lastbox.structo.util.prompt.GENERATE;


@RestController
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }
/*
    @PostMapping("/structure")
    public ResponseEntity<?> generateStructure(@RequestBody String structureDescription) {
        return ollamaService.sendPrompt(GENERATE.STRUCTURE.data(structureDescription));
    }

    @PostMapping("/task")
    public ResponseEntity<?> generateTasks(@RequestBody String taskDescription) {
        return ollamaService.sendPrompt(GENERATE.TASK.data(taskDescription));
    }

    @PostMapping("/diagram")
    public ResponseEntity<?> generateDiagrams(@RequestBody String diagramDescription) {
        return ollamaService.sendPrompt(GENERATE.DIAGRAM.data(diagramDescription));
    }*/
}

