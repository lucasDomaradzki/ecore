package br.com.ecore.role.service;

import br.com.ecore.exception.EcoreAlreadyReportedException;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.role.entity.RoleEntity;
import br.com.ecore.role.json.Role;
import br.com.ecore.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<String> getAllRoles() {
        return repository.findAllRoles();
    }

    public String createNewRole(String role) throws EcoreException {
        Optional<RoleEntity> optionalRole = repository.findRoleByRoleName(role);
        if (optionalRole.isEmpty()) {
            RoleEntity newRole = repository.createNewRole(role);
            return String.valueOf(newRole.getId());
        }

        throw new EcoreAlreadyReportedException(MessageFormat.format("Role {0} can not be created as role already exists", role));
    }

    public Optional<Role> findRoleByRoleName(String roleName) {
        Optional<RoleEntity> optionalRoleEntity = repository.findRoleByRoleName(roleName);
        if (optionalRoleEntity.isEmpty()) {
            return Optional.empty();
        }

        RoleEntity roleEntity = optionalRoleEntity.get();
        return Optional.of(new Role(roleEntity.getId(), roleEntity.getName()));
    }

    public Optional<RoleEntity> findRoleEntityByRoleName(String roleName) {
        return repository.findRoleByRoleName(roleName);
    }
}
