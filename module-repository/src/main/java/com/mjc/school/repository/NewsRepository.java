package com.mjc.school.repository;

import com.mjc.school.repository.model.News;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends BaseRepository<News, Long> {

    List<News> readBySearchParams(NewsSearchQueryParams searchQueryParams);
}
