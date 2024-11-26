package tech.lastbox.structo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UnityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String type;

    @OneToMany(mappedBy = "unity")
    private List<RequirementEntity> requirements;
}
