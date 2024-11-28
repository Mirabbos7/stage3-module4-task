package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractDBRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;
    private final Class<K> idClass;

    abstract void update(T prevState, T nextState);

    protected AbstractDBRepository() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        idClass = (Class<K>) type.getActualTypeArguments()[1];
    }

    @Override
    public Page<T> readAll(Pageable pageable) {
        // query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        //sort
        if (pageable.getSort() != null) {
            List<Order> orders = new ArrayList<>();
            pageable.getSort().forEach(order -> {
                Path<Object> path = root.get(order.getProperty());
                if (order.isAscending()) {
                    orders.add(criteriaBuilder.asc(path));
                } else {
                    orders.add(criteriaBuilder.desc(path));
                }
            });
            query.orderBy(orders);
        }

        //pagination
        TypedQuery<T> typedQuery = entityManager.createQuery(query);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        typedQuery.setFirstResult(pageNumber * pageSize);
        typedQuery.setMaxResults(pageSize);

        //list per page
        List<T> content = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(countRoot));
        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        //Page object with results
        return new PageImpl<>(content, pageable, totalElements);
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return readById(entity.getId()).map(existingEntity -> {
            update(existingEntity, entity);
            T updated = entityManager.merge(existingEntity);
            // flush is needed for auditable entities to get actual value of @LastModifiedDate field
            entityManager.flush();
            return updated;
        }).orElse(null);
    }

    @Override
    public boolean deleteById(K id) {
        if (id == null) {
            return false;
        }

        T entityRef = getReference(id);
        entityManager.remove(entityRef);
        return true;
    }

    @Override
    public boolean existById(K id) {
        EntityType<T> entityType = entityManager.getMetamodel().entity(entityClass);
        String idFieldName = entityType.getId(idClass).getName();

        Query query = entityManager
                .createQuery("SELECT COUNT(*) FROM " + entityClass.getSimpleName() + " WHERE " + idFieldName + " = ?1")
                .setParameter(1, id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public T getReference(K id) {
        return entityManager.getReference(this.entityClass, id);
    }
}
