package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsDBRepository extends AbstractDBRepository<News, Long> implements NewsRepository {
    @Override
    public List<News> readBySearchParams(NewsSearchQueryParams searchQueryParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = criteriaBuilder.createQuery(News.class);
        Root<News> root = query.from(News.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchQueryParams.tagNames() != null || searchQueryParams.tagIds() != null) {
            Join<Tag, News> newsTags = root.join("tags");
            if (searchQueryParams.tagNames() != null) {
                predicates.add(newsTags.get("name").in(searchQueryParams.tagNames()));
            }
            if (searchQueryParams.tagIds() != null) {
                predicates.add(newsTags.get("id").in(searchQueryParams.tagIds()));
            }
        }

        if (searchQueryParams.authorName() != null) {
            Join<Author, News> newsAuthor = root.join("authors");
            predicates.add(criteriaBuilder.equal(newsAuthor.get("name"), searchQueryParams.authorName()));
        }

        if (searchQueryParams.content() != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + searchQueryParams.content() + "%"));
        }

        if (searchQueryParams.title() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchQueryParams.title() + "%"));
        }

        query.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    void update(News prevState, News nextState) {
        prevState.setTitle(nextState.getTitle());
        prevState.setContent(nextState.getContent());
        prevState.setAuthor(nextState.getAuthor());
        List<Tag> newTags = new ArrayList<>(nextState.getTags());
        prevState.setTags(newTags);
        List<Comment> newComments = new ArrayList<>(nextState.getComments());
        prevState.setComments(newComments);
    }
}
