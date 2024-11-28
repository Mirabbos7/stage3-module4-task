package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDBRepository extends AbstractDBRepository<Comment, Long> implements CommentRepository {

    @Override
    public List<Comment> readByNewsId(Long newsId) {
        TypedQuery<Comment> typedQuery = entityManager
                .createQuery("SELECT c FROM Comment c INNER JOIN c.news n WHERE n.id=:newsId", Comment.class)
                .setParameter("newsId", newsId);
        return typedQuery.getResultList();
    }

    @Override
    void update(Comment prevState, Comment nextState) {
        prevState.setContent(nextState.getContent());
        prevState.setNews(nextState.getNews());
    }
}
