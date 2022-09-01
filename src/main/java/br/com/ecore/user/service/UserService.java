package br.com.ecore.user.service;

import br.com.ecore.common.ExistingApiService;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.exception.EcoreNotFoundException;
import br.com.ecore.membership.json.Membership;
import br.com.ecore.role.json.Role;
import br.com.ecore.role.service.RoleService;
import br.com.ecore.team.json.Team;
import br.com.ecore.team.repository.TeamRepository;
import br.com.ecore.user.json.User;
import br.com.ecore.user.repository.UserRepository;
import br.com.ecore.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static br.com.ecore.role.enums.PredefinedMinimalRole.DEVELOPER;
import static java.text.MessageFormat.format;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ExistingApiService existingApiService;

    public User assignRoleToUser(String userId, String roleId) throws EcoreNotFoundException {
        Role role = roleService.findRoleByRoleName(roleId)
                .orElseThrow(() -> new EcoreNotFoundException("Role {0} does not exist", roleId));

        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EcoreNotFoundException("No user found with user id: {0}", userId));

        userRepository.assignRoleToUser(userId, role.getId());
        user.setRole(role.getId().toString());
        return user;
    }

    public void findUserOrCreateIfNotExists(User user, Team team) throws EcoreException {
        existingApiService.getUserByUserId(team.getTeamLeadId()).ifPresent(teamLead -> {
            // Creating team lead
            Optional<User> optionalTeamLeadUser = userRepository.findUserByUserId(teamLead.getId());
            if (optionalTeamLeadUser.isEmpty()) {
                userRepository.createNewUserIfNotExists(teamLead, roleService.findRoleEntityByRoleName(DEVELOPER.name()));
            }

            // Having team lead assigned to his/her team
            if (teamRepository.findTeamLeadIdByTeamId(team.getId()).isEmpty()) {
                teamRepository.assignTeamLeadToTeam(team, team.getTeamLeadId());
            }
        });

        Optional<User> optionalUser = findUserByUserId(user.getId());

        if (optionalUser.isPresent()) {
            assignTeamToUser(team.getId(), user.getId());
            return;
        }

        // Creating user
        userRepository
                .createNewUser(user, teamRepository.findTeamById(team.getId()), roleService.findRoleEntityByRoleName(DEVELOPER.name()));
    }

    public void validateUser(Optional<User> optionalUser, String userId) {
        if (optionalUser.isPresent()) {
            return;
        }

        userRepository.deleteUserIfItExists(userId);
    }

    public void unAssignTeamFromUsers(String teamId) {
        userRepository.unAssignTeamFromUsers(teamId);
    }

    private void assignTeamToUser(String teamId, String userId) {
        userRepository.assignTeamToUser(teamId, userId);
    }

    public String findRoleByUserId(String userId) throws EcoreNotFoundException {
        return userRepository.findRoleByUserId(userId)
                .orElseThrow(() -> new EcoreNotFoundException("User id: {0} has no role associated", userId));
    }

    public Optional<User> findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    public List<User> findAllUsersCreateIfNotExistsByTeamId(Team team) throws EcoreException {
        for (String teamMemberId : team.getTeamMemberIds()) {
            Optional<User> userByUserId = existingApiService.getUserByUserId(teamMemberId);
            if(userByUserId.isPresent()) {
                findUserOrCreateIfNotExists(userByUserId.get(), team);
            }
        }

        return userRepository.findAllUsersByTeamId(team.getId());
    }

    public List<Membership> findMembershipsForRole(String roleName) throws EcoreException {
        Role role = CommonUtils.getOrThrowNotFoundException(
                roleService.findRoleByRoleName(roleName),
                MessageFormat.format("Role {0} does not exist", roleName)
        );
        return userRepository.findMembershipsForRole(role.getId());
    }
}
