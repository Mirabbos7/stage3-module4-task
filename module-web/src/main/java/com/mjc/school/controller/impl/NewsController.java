package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long>{
    private final NewsService newsService;

    @Autowired
    public NewsController(
            NewsService newsService
    ) {
        this.newsService = newsService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 1)
    public List<NewsDtoResponse> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return newsService.readAll(page, size, sortBy);
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
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @CommandHandler(operation = 4)
    public NewsDtoResponse patch(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
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
