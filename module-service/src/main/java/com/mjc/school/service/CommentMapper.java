package com.mjc.school.service;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    List<CommentDtoResponse> modelListToDtoList(List<Comment> comments);

    CommentDtoResponse modelToDto(Comment comment);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "news", ignore = true)
    Comment dtoToModel(CommentDtoRequest commentDtoResponse);
}
