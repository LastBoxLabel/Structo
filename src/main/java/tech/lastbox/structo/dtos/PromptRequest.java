package tech.lastbox.structo.dtos;

public record PromptRequest(String model,
                            String prompt,
                            String format,
                            boolean stream) {
}
