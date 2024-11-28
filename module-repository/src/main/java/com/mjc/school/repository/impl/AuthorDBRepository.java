package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AuthorDBRepository extends AbstractDBRepository<Author, Long> implements AuthorRepository {

    @Override
    public Optional<Author> readByNewsId(Long newsId) {
        TypedQuery<Author> typedQuery = entityManager
                .createQuery("SELECT a FROM Author a INNER JOIN a.news n WHERE n.id = :newsId", Author.class)
                .setParameter("newsId", newsId);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    void update(Author prevState, Author nextState) {
        prevState.setName(nextState.getName());
    }
}
