package br.com.ecore.role.service;

import br.com.ecore.exception.EcoreAlreadyReportedException;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.role.entity.RoleEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService service;

    @Test
    public void testMinimalPredefinedRoles() {
        Assertions.assertThat(service.findRoleByRoleName("DEVELOPER")).isPresent();
        Assertions.assertThat(service.findRoleByRoleName("PRODUCT_OWNER")).isPresent();
        Assertions.assertThat(service.findRoleByRoleName("TESTER")).isPresent();
    }

    @Test
    public void givenExistingRoleIsProvided_whenNewRoleIsAttemptedToBeCreated_EcoreAlreadyReportedExceptionIsThrown() {
        Assertions.assertThatThrownBy(() -> {
            service.createNewRole("DEVELOPER");
        }).isInstanceOf(EcoreAlreadyReportedException.class);
    }

    @Test
    public void givenNonExistingRoleIsProvided_whenNewRoleIsAttemptedToBeCreated_RoleIsPersisted() throws EcoreException {
        String createdRoleId = service.createNewRole("SCRUM_MASTER");
        Optional<RoleEntity> newRole = service.findRoleEntityByRoleName("SCRUM_MASTER");
        Assertions.assertThat(createdRoleId).isNotNull();
        Assertions.assertThat(newRole).isPresent();
        Assertions.assertThat(createdRoleId).isEqualTo(newRole.get().getId().toString());
    }

}