package com.mjc.school.repository;

import com.mjc.school.repository.model.Tag;


import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag, Long> {

    List<Tag> readByNewsId(Long newsId);

}
