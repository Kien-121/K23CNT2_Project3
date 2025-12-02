package com.giadungmart.service;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();
    T findById(Integer id);
    T save(T obj);
    void delete(Integer id);
}
