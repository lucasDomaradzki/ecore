package br.com.ecore.team.controller;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.exception.EcoreNotFoundException;
import br.com.ecore.team.json.TeamMemberNewRole;
import br.com.ecore.team.service.TeamService;
import br.com.ecore.user.json.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Team", tags = "Team")
@RestController
@RequestMapping(path = "/teams")
public class TeamController {

    @Autowired
    private TeamService service;

    @ApiOperation(value = "Assign new role to team member")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New role created successfully"),
            @ApiResponse(code = 204, message = "Role already exists"),
            @ApiResponse(code = 400, message = "Unexpected data sent in request"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @PutMapping(path = "/{id}/role", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity assignRoleToUser(@PathVariable(name = "id") String teamId,
                                           @RequestBody TeamMemberNewRole newRole) throws EcoreException {
        service.assignRoleToTeamMember(teamId, newRole);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Find all users from team")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Users found successfully"),
            @ApiResponse(code = 400, message = "Unexpected data provided"),
            @ApiResponse(code = 404, message = "No team or users found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping(path = "/{id}/users", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getTeamMembersFromTeam(@PathVariable(name = "id") String teamId) throws EcoreException {
        List<User> users = service.findUsersByTeamId(teamId);

        if (users.isEmpty()) {
            throw new EcoreNotFoundException("No users found for teamId: {0}", teamId);
        }

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
