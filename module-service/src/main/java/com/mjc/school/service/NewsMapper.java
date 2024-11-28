package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, TagMapper.class, CommentMapper.class})
public abstract class NewsMapper {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected CommentRepository commentRepository;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<News> newsList);

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "tags", target = "tagsDto")
    @Mapping(source = "comments", target = "commentsDto")
    public abstract NewsDtoResponse modelToDto(News model);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "author", expression =
            "java(authorRepository.getReference(dto.authorId()))")
    @Mapping(target = "tags", expression =
            "java(dto.tagsIds().stream().map(tagId -> tagRepository.getReference(tagId)).toList())")
    @Mapping(target = "comments", expression =
            "java(dto.commentsIds().stream().map(commentId -> commentRepository.getReference(commentId)).toList())")
    public abstract News dtoToModel(NewsDtoRequest dto);
}
