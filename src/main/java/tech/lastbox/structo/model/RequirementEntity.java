package tech.lastbox.structo.model;

import jakarta.persistence.*;

@Entity
public class RequirementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="unity_id", nullable = false)
    private UnityEntity unity;
}
