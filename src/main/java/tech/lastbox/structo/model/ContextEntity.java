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
    private String mindmap;

    @OneToMany
    private List<RequirementEntity> requirements;

    @OneToMany
    private List<UnityEntity> unities;

    @Enumerated(EnumType.STRING)
    private ContextType contextType;
}
