package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 6)
    public List<AuthorDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return authorService.readAll(page, size, sortBy);
    }

    private Sort parseSort(String[] sort) {
        if (sort.length == 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Direction.fromString(direction), property);
        }
        return Sort.unsorted();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 7)
    public AuthorDtoResponse readById(@PathVariable Long id) {
        return authorService.readById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 8)
    public AuthorDtoResponse create(@RequestBody AuthorDtoRequest createRequest) {
        return authorService.create(createRequest);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 9)
    public AuthorDtoResponse update(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse patch(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 10)
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 21)
    public AuthorDtoResponse readByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
}
