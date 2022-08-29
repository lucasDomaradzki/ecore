package br.com.ecore.role.controller;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.role.service.RoleService;
import br.com.ecore.common.EcoreResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Role", tags = "Role")
@RestController
@RequestMapping(path = "/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @ApiOperation(value = "Get all existing roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found roles"),
            @ApiResponse(code = 400, message = "Unexpected data sent in request"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllRoles() {
        return ResponseEntity.status(OK).body(service.getAllRoles());
    }

    @ApiOperation(value = "Creates new role")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New role created successfully"),
            @ApiResponse(code = 204, message = "Role already exists"),
            @ApiResponse(code = 400, message = "Unexpected data sent in request"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @PostMapping(path = "/{role}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EcoreResponse> createNewRole(@PathVariable(name = "role") String role) throws EcoreException {
        String newRoleId = service.createNewRole(role);
        return ResponseEntity.status(CREATED)
                .body(new EcoreResponse(CREATED.value(), newRoleId, "Role successfully created"));
    }

}
