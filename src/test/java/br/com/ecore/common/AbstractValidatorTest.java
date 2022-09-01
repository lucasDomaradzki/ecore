package br.com.ecore.common;

import br.com.ecore.exception.EcoreBadRequestException;
import br.com.ecore.team.json.Team;
import br.com.ecore.user.json.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AbstractValidatorTest extends AbstractValidator {

    private Team team;
    private User userThree;
    private User userTwo;

    @BeforeEach
    public void setUp() {
        Team tempTeam = new Team();
        tempTeam.setId("teamId");
        tempTeam.setName("teamName");
        tempTeam.setTeamLeadId("teamLeadId");
        tempTeam.setTeamMemberIds(Arrays.asList("userId1", "userId2"));
        team = tempTeam;

        userThree = new User("userId3", "User Three", "DEVELOPER");
        userTwo = new User("userId2", "User Two", "TESTER");
    }

    @Test
    public void givenUserNoBelongToTeam_whenUserIdIsProvided_badRequestExceptionIsThrown() {
        Assertions.assertThrows(EcoreBadRequestException.class, () -> validateUserBelongToTeam(team, userThree));
    }

    @Test
    public void givenUserNoBelongToTeam_whenUserIdIsProvided_badRequestExceptionIsNotThrown() {
        Assertions.assertDoesNotThrow(() -> validateUserBelongToTeam(team, userTwo));
    }

}