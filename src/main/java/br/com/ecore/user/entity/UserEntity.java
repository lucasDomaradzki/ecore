package br.com.ecore.user.entity;

import br.com.ecore.role.entity.RoleEntity;
import br.com.ecore.team.entity.TeamEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_issued")
    private LocalDateTime dateIssued;
    @Column(name = "date_update")
    private LocalDateTime dateUpdate;
    @OneToOne
    @JoinColumn(name = "role")
    private RoleEntity role;

    @Column(name ="active")
    private boolean active;
    @OneToOne
    @JoinColumn(name = "team")
    private TeamEntity team;

    @PrePersist
    public void beforePersist() {
        this.dateIssued = LocalDateTime.now();
        this.dateUpdate = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.dateUpdate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
