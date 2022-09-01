package br.com.ecore.team.controller;

import br.com.ecore.exception.EcoreBadRequestException;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.exception.EcoreNotFoundException;
import br.com.ecore.team.json.TeamMemberNewRole;
import br.com.ecore.user.json.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TeamControllerIntegrationTest {

    private static final String EXISTING_TEAM_ID = "83e58f56-0ecf-4ea9-bdff-81ded3342061";

    private static final String EXISTING_USER_ID = "133d4ab1-f80b-4c37-9c94-2cd9c5cf070b";

    @Autowired
    private TeamController teamController;

    @Test
    public void givenTeamIdIsValid_whenGetTeamMembersFromTeamIdIsExecuted_teamMembersAreSuccessfullyFetched() throws EcoreException {
        ResponseEntity<List<User>> teamMembersFromTeam = teamController.getTeamMembersFromTeam(EXISTING_TEAM_ID);
        Assertions.assertThat(teamMembersFromTeam).isNotNull();
        Assertions.assertThat(teamMembersFromTeam.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(teamMembersFromTeam.hasBody()).isTrue();
        Assertions.assertThat(teamMembersFromTeam.getBody()).isNotNull();
        Assertions.assertThat(teamMembersFromTeam.getBody()).isNotEmpty();
    }

    @Test
    public void givenTeamIdIsInvalid_whenGetTeamMembersFromTeamIdIsExecuted_teamMembersAreSuccessfullyFetched() {
        Assertions.assertThatThrownBy(() -> {
            teamController.getTeamMembersFromTeam("X");
        }).isInstanceOf(EcoreNotFoundException.class)
        .message().isEqualTo("Team not found with id: X");
    }

    @Test
    public void givenUserFromExistingTeam_whenNewRoleAssignmentIsAttempted_thenNewRoleIsSuccessfullyAssigned() throws EcoreException {
        TeamMemberNewRole newRole = new TeamMemberNewRole();
        newRole.setNewRole("PRODUCT_OWNER");
        newRole.setTeamMemberId(EXISTING_USER_ID);
        ResponseEntity responseEntity = teamController.assignRoleToUser(EXISTING_TEAM_ID, newRole);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenUserFromNonExistingTeam_whenNewRoleAssignmentIsAttempted_thenNotFoundExceptionIsThrown() {
        Assertions.assertThatThrownBy(() -> {
            TeamMemberNewRole newRole = new TeamMemberNewRole();
            newRole.setNewRole("PRODUCT_OWNER");
            newRole.setTeamMemberId("O");
            teamController.assignRoleToUser(EXISTING_TEAM_ID, newRole);
        }).isInstanceOf(EcoreNotFoundException.class)
        .message().isEqualTo("User with id: O does not exist");
    }

    @Test
    public void givenNonExistingUserFromTeam_whenExistingNewRoleAssignmentIsAttempted_thenNewRoleIsAssignedToUser() throws EcoreException {
        Assertions.assertThatThrownBy(() -> {
            TeamMemberNewRole newRole = new TeamMemberNewRole();
            newRole.setNewRole("TESTER");
            newRole.setTeamMemberId("5fb79654-49a5-4ae4-92ef-75867aa53826");
            teamController.assignRoleToUser(EXISTING_TEAM_ID, newRole);
        }).isInstanceOf(EcoreBadRequestException.class)
        .message().isEqualTo("User with id: 5fb79654-49a5-4ae4-92ef-75867aa53826 does not seem to belong to belong to team: 83e58f56-0ecf-4ea9-bdff-81ded3342061");
    }

    @Test
    public void givenUserFromExistingTeam_whenNonExistingNewRoleAssignmentIsAttempted_thenBadRequestExceptionIsThrown() {
        Assertions.assertThatThrownBy(() -> {
            TeamMemberNewRole newRole = new TeamMemberNewRole();
            newRole.setNewRole("SOFTWARE_ARCHITECT");
            newRole.setTeamMemberId(EXISTING_USER_ID);
            teamController.assignRoleToUser(EXISTING_TEAM_ID, newRole);
        }).isInstanceOf(EcoreNotFoundException.class)
        .message().isEqualTo("Role SOFTWARE_ARCHITECT does not exist");
    }

}