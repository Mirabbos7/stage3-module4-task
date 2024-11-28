package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    @Override
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 16)
    public Page<CommentDtoResponse> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<CommentDtoResponse> comments = commentService.readAll(pageable);

        return comments;
    }

    private Sort parseSort(String[] sort) {
        if (sort.length == 2) {
            String property = sort[0];
            String direction = sort[1];
            return Sort.by(Sort.Direction.fromString(direction), property);
        }
        return Sort.unsorted();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 17)
    public CommentDtoResponse readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 18)
    public CommentDtoResponse create(@RequestBody CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 19)
    public CommentDtoResponse update(@RequestBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 20)
    public boolean deleteById(@PathVariable Long id) {
        return commentService.deleteById(id);
    }

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 22)
    public List<CommentDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return commentService.readByNewsId(newsId);
    }
}
