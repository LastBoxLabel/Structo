package tech.lastbox.structo.model;

import jakarta.persistence.*;
import tech.lastbox.structo.model.types.ContextType;

import java.util.List;

@Entity
public class ContextEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private DiagramsEntity diagram;

    @ElementCollection
    private List<String> requirements;

    @OneToMany
    private List<UnityEntity> unities;

    @Enumerated(EnumType.STRING)
    private ContextType contextType;

    @ManyToOne
    @JoinColumn(name="context_id", nullable=false)
    private ProjectEntity project;
}
