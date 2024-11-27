package tech.lastbox.structo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UnityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column (nullable = false)
    private String type;

    @ElementCollection
    private List<String> requirements;

    @ManyToOne
    @JoinColumn(name="context_id", nullable = false)
    private ContextEntity context;
}
