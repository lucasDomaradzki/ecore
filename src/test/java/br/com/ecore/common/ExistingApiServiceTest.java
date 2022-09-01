package br.com.ecore.common;

import br.com.ecore.exception.EcoreException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExistingApiServiceTest {

    @Autowired
    private ExistingApiService service;

    @Test
    public void givenExistingUserIdIsProvided_whenExistingApiIsCalled_userIsSuccessfullyFetched() throws EcoreException {
        String userId = "aa569071-6ade-4ff6-b567-6466fcbae74a";
        Assertions.assertThat(service.getUserByUserId(userId)).isPresent();
    }

    @Test
    public void givenExistingTeamIdIsProvided_whenExistingApiIsCalled_teamIsSuccessfullyFetched() throws EcoreException {
        String teamId = "de01d852-c519-4c54-b95a-80c5b6fa0157";
        Assertions.assertThat(service.getTeamByTeamId(teamId)).isPresent();
    }

    @Test
    public void givenNonExistingUserIdIsProvided_whenExistingApiIsCalled_userIsSuccessfullyFetched() throws EcoreException {
        String userId = "x";
        Assertions.assertThat(service.getUserByUserId(userId)).isEmpty();
    }

    @Test
    public void givenNonExistingTeamIdIsProvided_whenExistingApiIsCalled_teamIsSuccessfullyFetched() throws EcoreException {
        String teamId = "y";
        Assertions.assertThat(service.getTeamByTeamId(teamId)).isEmpty();
    }


}