package br.com.ecore.common;

import br.com.ecore.exception.EcoreBadRequestException;
import br.com.ecore.exception.EcoreException;
import br.com.ecore.team.json.Team;
import br.com.ecore.user.json.User;
import org.apache.commons.lang3.StringUtils;

import static java.text.MessageFormat.format;

public abstract class AbstractValidator {

    protected void validateUserBelongToTeam(Team team, User user) throws EcoreException {
        team.getTeamMemberIds().stream()
                .filter(userId -> StringUtils.equals(user.getId(), userId))
                .findFirst()
                .orElseThrow(() -> new EcoreBadRequestException(
                        format("User with id: {0} does not seem to belong to belong to team: {1}", user.getId(), team.getId())
                ));
    }

}
