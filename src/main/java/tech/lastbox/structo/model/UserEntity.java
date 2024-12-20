package tech.lastbox.structo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tech.lastbox.lastshield.security.core.annotations.Password;
import tech.lastbox.lastshield.security.core.annotations.Username;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(unique=true, nullable=false)
    @Username
    private String email;

    @Column(nullable = false)
    @Password
    @JsonIgnore
    private String password;

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectEntity> projects = new ArrayList<>();

    @JsonIgnore
    private String role = "USER";

    public UserEntity() {}

    public UserEntity(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserEntity addProject(ProjectEntity project) {
        projects.add(project);
        return this;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserEntity that)) return false;
        return id == that.id || Objects.equals(username, that.username) || Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}
