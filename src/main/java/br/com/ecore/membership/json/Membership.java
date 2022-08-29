package br.com.ecore.membership.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Membership implements Serializable {

    private static final long serialVersionUID = -8029083122271557747L;

    private String teamId;
    private String teamName;
    private List<String> teamMemberIds;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getTeamMemberIds() {
        return teamMemberIds;
    }

    public void setTeamMemberIds(List<String> teamMemberIds) {
        this.teamMemberIds = teamMemberIds;
    }
}
