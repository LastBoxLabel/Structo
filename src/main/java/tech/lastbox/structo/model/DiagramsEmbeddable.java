package tech.lastbox.structo.model;

import jakarta.persistence.*;

@Embeddable
public class DiagramsEmbeddable {

    @Column(nullable = false)
    private String mindmap;

    @Column(nullable = false)
    private String classDiagram;

    @Column(nullable = false)
    private String fluxogram;
}
