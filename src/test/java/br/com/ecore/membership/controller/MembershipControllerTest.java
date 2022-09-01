package br.com.ecore.membership.controller;

import br.com.ecore.exception.EcoreBadRequestException;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.membership.json.Membership;
import br.com.ecore.user.json.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MembershipControllerTest {

    @Autowired
    private MembershipController controller;

    private static final String EXISTING_TEAM_ID = "22da4abb-6453-43f8-84eb-ccec123c3df1";

    private static final String EXISTING_USER_ID = "9529dc24-e68b-41b8-b421-4420f13b8e20";

    @Test
    public void givenExistingUserFromExistingTeam_whenLookUpRoleForMembershipIsAttempted_membershipDetailsFromUserIsReturned() throws EcoreException {
        ResponseEntity<User> userResponseEntity = controller.lookUpRoleForMembership(EXISTING_USER_ID, EXISTING_TEAM_ID);
        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userResponseEntity.hasBody()).isTrue();
        Assertions.assertThat(userResponseEntity.getBody()).isInstanceOf(User.class);
        User body = userResponseEntity.getBody();
        Assertions.assertThat(body.getId()).isEqualTo(EXISTING_USER_ID);
    }

    @Test
    public void givenExistingUserNotBelongingToExistingTeam_whenLookUpRoleForMembershipIsAttempted_() throws EcoreException {
        Assertions.assertThatThrownBy(() -> {
           controller.lookUpRoleForMembership("6f079ae0-d89f-4071-a756-0dfaba427784", EXISTING_TEAM_ID);
        }).isInstanceOf(EcoreBadRequestException.class)
        .message().isEqualTo("User with id: 6f079ae0-d89f-4071-a756-0dfaba427784 does not seem to belong to belong to team: 22da4abb-6453-43f8-84eb-ccec123c3df1");
    }

    @Test
    public void testLookUpMembershipForRole() throws EcoreException {
        ResponseEntity<List<Membership>> developer = controller.lookUpMembershipsForRole("DEVELOPER");
        Assertions.assertThat(developer).isNotNull();
        Assertions.assertThat(developer.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(developer.getBody()).isNotNull();
        Assertions.assertThat(developer.getBody()).isNotEmpty();
    }

}