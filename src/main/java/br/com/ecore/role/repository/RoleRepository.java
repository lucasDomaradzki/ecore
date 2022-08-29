package br.com.ecore.role.repository;

import br.com.ecore.exception.EcoreException;
import br.com.ecore.role.entity.RoleEntity;
import br.com.ecore.utils.StringsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository {

    @Autowired
    private EntityManager entityManager;

    public List<String> findAllRoles() {
        String jpql = "SELECT role.name FROM RoleEntity role ORDER BY role.name";
        final TypedQuery<String> query = this.entityManager.createQuery(jpql, String.class);
        return query.getResultList();
    }

    public Optional<RoleEntity> findRoleByRoleName(String role) {
        try {
            String jpql = "FROM RoleEntity role WHERE role.name = :roleName ORDER BY NAME";
            final TypedQuery<RoleEntity> query = this.entityManager.createQuery(jpql, RoleEntity.class);
            query.setParameter("roleName", StringsUtils.prepareRoleName(role));
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional(rollbackOn = EcoreException.class)
    public RoleEntity createNewRole(String role) {
        RoleEntity entity = new RoleEntity();
        entity.setName(role);
        entityManager.persist(entity);
        return entity;
    }
}
