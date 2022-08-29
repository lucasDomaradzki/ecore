package br.com.ecore.membership.controller;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.membership.json.Membership;
import br.com.ecore.membership.service.MembershipService;
import br.com.ecore.user.json.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Membership", tags = "Membership")
@RestController
@RequestMapping(path = "/memberships")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @ApiOperation(value = "Look up a role for a membership - Return user along with his/her assigned role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Membership (User) found"),
            @ApiResponse(code = 400, message = "Unexpected data sent in request"),
            @ApiResponse(code = 404, message = "Team/user do not exist or membership is not valid"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping(path = "/user/{userId}/team/{teamId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> lookUpRoleForMembership(@PathVariable(name = "userId") String userId,
                                                        @PathVariable(name = "teamId") String teamId) throws EcoreException {
        User user = membershipService.lookUpForRoleForMembership(userId, teamId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @ApiOperation(value = "Look up for memberships for a role - Return list of memberships")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found memberships where users are assigned by certain role"),
            @ApiResponse(code = 400, message = "Unexpected data sent in request"),
            @ApiResponse(code = 404, message = "Role does not exist or no memberships found for role"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping(path = "/role/{role}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Membership>> lookUpMembershipsForRole(@PathVariable(name = "role") String role) throws EcoreException {
        List<Membership> memberships = membershipService.lookUpForMembershipsForRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(memberships);
    }

}
