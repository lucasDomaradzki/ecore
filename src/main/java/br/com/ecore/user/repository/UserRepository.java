package br.com.ecore.user.repository;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.membership.json.Membership;
import br.com.ecore.role.entity.RoleEntity;
import br.com.ecore.team.entity.TeamEntity;
import br.com.ecore.user.entity.UserEntity;
import br.com.ecore.user.json.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final String USER_ID = "userId";
    private static final String TEAM_ID = "teamId";

    @Autowired
    private EntityManager entityManager;


    public Optional<User> findUserByUserId(String userId) {
        try {
            String jqpl = "FROM UserEntity WHERE id = :id";
            TypedQuery<UserEntity> query = this.entityManager.createQuery(jqpl, UserEntity.class);
            query.setParameter("id", userId);
            UserEntity userEntity = query.getSingleResult();
            String role = userEntity.getRole() != null ? userEntity.getRole().getName() : "";
            return Optional.of(new User(userEntity.getId(), userEntity.getName(), role));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional(rollbackOn = EcoreException.class)
    public void createNewUser(User user, Optional<TeamEntity> teamEntity, Optional<RoleEntity> role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getDisplayName());
        userEntity.setActive(true);
        teamEntity.ifPresent(userEntity::setTeam);
        role.ifPresent(userEntity::setRole);
        this.entityManager.persist(userEntity);
    }

    public Optional<String> findRoleByUserId(String userId) {
        try {
            String jpql = "SELECT user.role.name FROM UserEntity user WHERE user.id = :userId";
            TypedQuery<String> query = this.entityManager.createQuery(jpql, String.class);
            query.setParameter(USER_ID, userId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Transactional(rollbackOn = EcoreException.class)
    public void createNewUserIfNotExists(User user, Optional<RoleEntity> role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getDisplayName());
        userEntity.setActive(true);
        role.ifPresent(userEntity::setRole);
        this.entityManager.persist(userEntity);
    }

    @Transactional(rollbackOn = EcoreException.class)
    public void persist(UserEntity entity) {
        this.entityManager.persist(entity);
    }

    @Transactional(rollbackOn = EcoreException.class)
    public void deleteUserIfItExists(String userId) {
        String sql = "delete from user where user_id = :userId";
        Query query = this.entityManager.createNativeQuery(sql);
        query.setParameter(USER_ID, userId);
        query.executeUpdate();
    }

    @Transactional
    public void unAssignTeamFromUsers(String teamId) {
        String sql = "update user set team = :team where team = :teamId";
        Query nativeQuery = this.entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("team", null);
        nativeQuery.setParameter(TEAM_ID, teamId);
        nativeQuery.executeUpdate();
    }

    @Transactional
    public void assignTeamToUser(String teamId, String userId) {
        String sql = "update user set team = :teamId where user_id = :userId";
        Query nativeQuery = this.entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(TEAM_ID, teamId);
        nativeQuery.setParameter(USER_ID, userId);
        nativeQuery.executeUpdate();
    }

    @Transactional
    public void assignRoleToUser(String userId, Long roleId) {
        String sql = "update user set role = :roleId where user_id = :userId";
        Query query = this.entityManager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);
        query.setParameter(USER_ID, userId);
        query.executeUpdate();
    }

    public List<User> findAllUsersByTeamId(String teamId) {
        String jpql = "FROM UserEntity WHERE team.id = :teamId";
        TypedQuery<UserEntity> query = this.entityManager.createQuery(jpql, UserEntity.class);
        query.setParameter(TEAM_ID, teamId);
        List<UserEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }

        return resultList.stream()
                .map(userEntity -> new User(userEntity.getId(), userEntity.getName(), userEntity.getRole().getName()))
                .collect(Collectors.toList());
    }

    public List<Membership> findMembershipsForRole(Long roleId) {
        String jpql = "FROM UserEntity WHERE role.id = :roleId";
        TypedQuery<UserEntity> query = this.entityManager.createQuery(jpql, UserEntity.class);
        query.setParameter("roleId", roleId);

        List<UserEntity> resultList = query.getResultList();

        Map<TeamEntity, List<UserEntity>> collect = resultList
                .stream().filter(userEntity -> userEntity.getTeam() != null)
                .collect(Collectors.groupingBy(UserEntity::getTeam));

        return collect.keySet().stream().map(teamEntity -> {
            Membership membership = new Membership();
            membership.setTeamId(teamEntity.getId());
            membership.setTeamName(teamEntity.getName());
            membership.setTeamMemberIds(collect.get(teamEntity).stream().map(UserEntity::getId).collect(Collectors.toList()));
            return membership;
        }).collect(Collectors.toList());
    }
}
