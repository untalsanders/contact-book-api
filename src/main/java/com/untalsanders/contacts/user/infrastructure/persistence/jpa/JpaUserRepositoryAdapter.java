package com.untalsanders.contacts.user.infrastructure.persistence.jpa;

import com.untalsanders.contacts.user.domain.exception.UserNotFoundException;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import com.untalsanders.contacts.user.infrastructure.persistence.entity.UserEntity;
import com.untalsanders.contacts.user.infrastructure.persistence.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JpaUserRepositoryAdapter implements UserRepository {

    private final EntityManager entityManager;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(String id) {
        return findBy("id", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findBy("email", email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findBy("username", username);
    }

    @Override
    public boolean existsById(String id) {
        // This could be optimized to a COUNT query, but it's acceptable for now.
        return findById(id).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public Set<User> findAll() {
        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class);
        return query.getResultStream()
                .map(userMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        entity.setEnabled(true);
        entityManager.persist(entity);
        log.info("User saved with id: {}", entity.getId());
        return userMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public User update(String id, User user) {
        UserEntity existing = entityManager.find(UserEntity.class, UUID.fromString(id));
        if (existing == null) {
            throw new UserNotFoundException(id);
        }
        UserEntity updated = userMapper.toEntity(user);
        updated.setId(UUID.fromString(id));
        updated.setEnabled(existing.getEnabled());
        UserEntity merged = entityManager.merge(updated);
        log.info("User updated with id: {}", merged.getId());
        return userMapper.toDomain(merged);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        UserEntity entity = entityManager.find(UserEntity.class, UUID.fromString(id));
        if (entity != null) {
            entityManager.remove(entity);
            log.info("User deleted with id: {}", id);
            return true;
        }
        log.warn("User isn't found for deletion with id: {}", id);
        return false;
    }

    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        return findByEmail(email).map(user -> {
            deleteById(user.id().value().toString());
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) {
        return findByUsername(username).map(user -> {
            deleteById(user.id().value().toString());
            return true;
        }).orElse(false);
    }

    private Optional<User> findBy(String attribute, String value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> user = cq.from(UserEntity.class);
        Predicate predicate = cb.equal(user.get(attribute), value);
        cq.where(predicate);
        TypedQuery<UserEntity> query = entityManager.createQuery(cq);
        try {
            return Optional.of(query.getSingleResult()).map(userMapper::toDomain);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private boolean existsBy(String attribute, String value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<UserEntity> user = cq.from(UserEntity.class);
        Predicate predicate = cb.equal(user.get(attribute), value);
        cq.where(predicate);
        cq.select(cb.count(user));
        TypedQuery<Long> query = entityManager.createQuery(cq);
        return query.getSingleResult() > 0;
    }
}
