package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsServiceImpl;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsServiceImpl newsService;

    @Autowired
    public NewsController(
            NewsServiceImpl newsService
    ) {
        this.newsService = newsService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 1)
    public Page<NewsDtoResponse> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        Page<NewsDtoResponse> news = newsService.readAll(pageable);

        return news;
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
    @CommandHandler(operation = 2)
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CommandHandler(operation = 3)
    public NewsDtoResponse create(@RequestBody NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 4)
    public NewsDtoResponse update(@RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CommandHandler(operation = 5)
    public boolean deleteById(@PathVariable Long id) {
        return newsService.deleteById(id);
    }

    @CommandHandler(operation = 23)
    @GetMapping("/one-news")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsByParams(
            @RequestParam(required = false) NewsQueryParams newsQueryParams
    ) {
        return newsService.readByQueryParams(newsQueryParams);
    }
}
