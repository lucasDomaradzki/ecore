package br.com.ecore.team.entity;

import br.com.ecore.user.entity.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @Column(name = "team_id")
    private String id;
    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @OneToOne
    @JoinColumn(name = "team_lead")
    private UserEntity teamLead;

    @Column(name = "active")
    private boolean active;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public UserEntity getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(UserEntity teamLead) {
        this.teamLead = teamLead;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
