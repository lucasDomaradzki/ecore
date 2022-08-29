package br.com.ecore.membership.service;

import br.com.ecore.common.AbstractValidator;
import br.com.ecore.common.ExistingApiService;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.membership.json.Membership;
import br.com.ecore.team.json.Team;
import br.com.ecore.team.service.TeamService;
import br.com.ecore.user.json.User;
import br.com.ecore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.ecore.utils.CommonUtils.getOrThrowNotFoundException;
import static java.text.MessageFormat.format;

@Service
public class MembershipService extends AbstractValidator {

    @Autowired
    private ExistingApiService existingApiService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    public User lookUpForRoleForMembership(String userId, String teamId) throws EcoreException {
        Optional<Team> optionalTeam = existingApiService.getTeamByTeamId(teamId);
        Optional<User> optionalUser = existingApiService.getUserByUserId(userId);
        userService.validateUser(optionalUser, userId);
        teamService.validateTeam(optionalTeam, teamId);

        // If team or user does not exist in existing API Exception is thrown
        User user = getOrThrowNotFoundException(optionalUser, format("User with id: {0} does not exist", userId));
        Team team = getOrThrowNotFoundException(optionalTeam, format("Team with id: {0} does not exist", teamId));

        teamService.createTeamIfNotExists(team);
        validateUserBelongToTeam(team, user);
        userService.findUserOrCreateIfNotExists(user, team);
        String role = userService.findRoleByUserId(userId);
        user.setRole(role);
        return user;
    }

    public List<Membership> lookUpForMembershipsForRole(String role) throws EcoreException {
        return userService.findMembershipsForRole(role);
    }
}
