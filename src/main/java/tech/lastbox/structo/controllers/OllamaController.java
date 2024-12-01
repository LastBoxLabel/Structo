package tech.lastbox.structo.controllers;

import org.springframework.web.bind.annotation.*;
import tech.lastbox.structo.services.OllamaService;


@RestController
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }
/*
    @PostMapping("/structure")
    public ResponseEntity<?> generateStructure(@RequestBody String structureDescription) {
        return ollamaService.sendPrompt(Generate.STRUCTURE.data(structureDescription));
    }

    @PostMapping("/task")
    public ResponseEntity<?> generateTasks(@RequestBody String taskDescription) {
        return ollamaService.sendPrompt(Generate.TASK.data(taskDescription));
    }

    @PostMapping("/diagram")
    public ResponseEntity<?> generateDiagrams(@RequestBody String diagramDescription) {
        return ollamaService.sendPrompt(Generate.DIAGRAM.data(diagramDescription));
    }*/
}

