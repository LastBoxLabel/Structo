package tech.lastbox.structo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lastbox.structo.services.OllamaService;
import tech.lastbox.structo.services.Prompt.Generate;


@RestController
public class OllamaController {

    OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/structure")
    public ResponseEntity<?> generateStructure(@RequestBody String structureDescription) {
        return ollamaService.sendPrompt(Generate.STRUCTURE.value(structureDescription));
    }

    @PostMapping("/task")
    public ResponseEntity<?> generateTasks(@RequestBody String taskDescription) {
        return ollamaService.sendPrompt(Generate.TASK.value(taskDescription));
    }

    @PostMapping("/diagram")
    public ResponseEntity<?> generateDiagrams(@RequestBody String diagramDescription) {
        return ollamaService.sendPrompt(Generate.DIAGRAM.value(diagramDescription));
    }
}

