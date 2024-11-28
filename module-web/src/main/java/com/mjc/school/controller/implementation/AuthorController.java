package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.implementation.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping

    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 6)
    public Page<AuthorDtoResponse> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<AuthorDtoResponse> authors = authorService.readAll(pageable);

        return authors;
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


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 9)
    public AuthorDtoResponse update(@RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 10)
    public boolean deleteById(@PathVariable Long id) {
        return authorService.deleteById(id);
    }
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 21)
    public AuthorDtoResponse readByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
}
