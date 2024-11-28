package com.mjc.school.repository;

import com.mjc.school.repository.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends BaseRepository<Comment, Long> {

    List<Comment> readByNewsId(Long newsId);
}
