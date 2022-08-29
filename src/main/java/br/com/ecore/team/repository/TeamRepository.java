package br.com.ecore.team.repository;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.team.entity.TeamEntity;
import br.com.ecore.team.json.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Repository
public class TeamRepository {

    private static final Logger LOGGER = LogManager.getLogger(TeamRepository.class);

    @Autowired
    private EntityManager entityManager;

    @Transactional(rollbackOn = EcoreException.class)
    public Optional<TeamEntity> findTeamByTeamIdCreateIfNotExists(Team team) {
        try {
            String jpql = "FROM TeamEntity WHERE id = :id";
            TypedQuery<TeamEntity> query = this.entityManager.createQuery(jpql, TeamEntity.class);
            query.setParameter("id", team.getId());
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            LOGGER.warn(format("No team found with id: {0}", team.getId()));
            return createTeam(team);
        }
    }

    private Optional<TeamEntity> createTeam(Team team) {
        LOGGER.info(format("Creating new team with id: {0}", team.getId()));

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(team.getId());
        teamEntity.setActive(true);
        teamEntity.setName(team.getName());
        this.entityManager.persist(teamEntity);
        return Optional.of(teamEntity);
    }

    public Optional<String> findTeamLeadIdByTeamId(String teamId) {
        TeamEntity teamEntity = this.entityManager.find(TeamEntity.class, teamId);
        if (teamEntity == null || teamEntity.getTeamLead() == null) {
            return Optional.empty();
        }

        return Optional.of(teamEntity.getTeamLead().getId());
    }

    @Transactional(rollbackOn = EcoreException.class)
    public void assignTeamLeadToTeam(Team team, String teamLeadId) {
        String sql = "update team set team_lead = :teamLeadId where team_id = :id";
        Query query = this.entityManager.createNativeQuery(sql);
        query.setParameter("teamLeadId", teamLeadId);
        query.setParameter("id", team.getId());
        query.executeUpdate();
    }

    public Optional<TeamEntity> findTeamById(String teamId) {
        TeamEntity teamEntity = this.entityManager.find(TeamEntity.class, teamId);
        if (teamEntity == null) {
            return Optional.empty();
        }

        return Optional.of(teamEntity);
    }

    @Transactional
    public void deleteTeam(TeamEntity teamEntity) {
        this.entityManager.remove(teamEntity);
    }
}
