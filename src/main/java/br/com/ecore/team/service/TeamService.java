package br.com.ecore.team.service;

import br.com.ecore.common.AbstractValidator;
import br.com.ecore.common.ExistingApiService;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.team.json.Team;
import br.com.ecore.team.json.TeamMemberNewRole;
import br.com.ecore.team.repository.TeamRepository;
import br.com.ecore.user.json.User;
import br.com.ecore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.ecore.utils.CommonUtils.getOrThrowNotFoundException;
import static java.text.MessageFormat.format;

@Service
public class TeamService extends AbstractValidator {

    @Autowired
    private ExistingApiService existingApiService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    public void assignRoleToTeamMember(String teamId, TeamMemberNewRole newRole) throws EcoreException {
        Optional<Team> optionalTeam = existingApiService.getTeamByTeamId(teamId);
        Optional<User> optionalUser = existingApiService.getUserByUserId(newRole.getTeamMemberId());
        userService.validateUser(optionalUser, newRole.getTeamMemberId());
        validateTeam(optionalTeam, teamId);

        // If team or user does not exist in existing API Exception is thrown
        User user = getOrThrowNotFoundException(optionalUser, format("User with id: {0} does not exist", newRole.getTeamMemberId()));
        Team team = getOrThrowNotFoundException(optionalTeam, format("Team with id: {0} does not exist", teamId));

        createTeamIfNotExists(team);
        userService.findUserOrCreateIfNotExists(user, team);
        userService.assignRoleToUser(newRole.getTeamMemberId(), newRole.getNewRole());
    }

    public void createTeamIfNotExists(Team team) {
        teamRepository.findTeamByTeamIdCreateIfNotExists(team);
    }

    public void validateTeam(Optional<Team> team, String teamId) {
        if (team.isPresent()) {
            return;
        }

        teamRepository.findTeamById(teamId).ifPresent(teamEntity -> {
            userService.unAssignTeamFromUsers(teamId);
            teamRepository.deleteTeam(teamEntity);
        });
    }

    public List<User> findUsersByTeamId(String teamId) throws EcoreException {
        // Existing API
        Optional<Team> optionalTeam = existingApiService.getTeamByTeamId(teamId);
        validateTeam(optionalTeam, teamId);
        // If team does not exist in existing API Exception is thrown
        Team team = getOrThrowNotFoundException(optionalTeam, format("Team not found with id: {0}", teamId));

        // Creating team in DB
        createTeamIfNotExists(team);
        return userService.findAllUsersCreateIfNotExistsByTeamId(team);
    }
}
