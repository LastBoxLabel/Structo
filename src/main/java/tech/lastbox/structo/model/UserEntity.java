package tech.lastbox.structo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    @OneToMany(mappedBy = "user")
    private List<ProjectEntity> projects;

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
