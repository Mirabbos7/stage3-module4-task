package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 11)
    public List<TagDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return tagService.readAll(page, size, sortBy);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 12)
    public TagDtoResponse readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 13)
    public TagDtoResponse create(@RequestBody TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 14)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 14)
    public TagDtoResponse patch(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 15)
    public boolean deleteById(@PathVariable Long id) {
        return tagService.deleteById(id);
    }

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 24)
    public List<TagDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return tagService.readByNewsId(newsId);
    }
}
