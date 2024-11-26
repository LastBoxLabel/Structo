package tech.lastbox.structo.model;

import jakarta.persistence.*;

import java.util.List;

@Table
@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @OneToMany (mappedBy = "project")
    private List<ContextEntity> contexts;

    @Embedded
    private DiagramsEmbeddable diagrams;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private UserEntity user;
}
