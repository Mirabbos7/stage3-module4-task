package com.mjc.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll(int page, int size, String sortBy);

    R readById(K id);

    R create(T createRequest);

    R update(Long id, T updateRequest);

    R patch(Long id, T patchRequest);

    void deleteById(K id);
}
