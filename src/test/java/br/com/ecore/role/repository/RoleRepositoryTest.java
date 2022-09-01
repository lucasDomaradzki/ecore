package br.com.ecore.role.repository;

import br.com.ecore.role.entity.RoleEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    public void testFindAllRoles() {
        List<String> allRoles = repository.findAllRoles();
        Assertions.assertThat(allRoles).isNotNull();
        Assertions.assertThat(allRoles).isNotEmpty();
    }

    @Test
    public void testFindRoleByRoleName() {
        Assertions.assertThat(repository.findRoleByRoleName("")).isEmpty();
        Assertions.assertThat(repository.findRoleByRoleName("CEO")).isEmpty();
        Assertions.assertThat(repository.findRoleByRoleName("TESTER")).isNotEmpty();
    }

    @Test
    public void testCreateRole() {
        String roleName = "CEO";
        RoleEntity ceoEntity = repository.createNewRole(roleName);
        Assertions.assertThat(ceoEntity).isNotNull();
        Assertions.assertThat(ceoEntity.getName().equals(roleName));
    }

}