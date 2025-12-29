package com.shopgiadung.service;

import com.shopgiadung.entity.NvkNews;
import com.shopgiadung.repository.NvkNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NvkNewsService {

    @Autowired
    private NvkNewsRepository newsRepository;

    public List<NvkNews> getAllNews() {
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public NvkNews saveNews(NvkNews news) {
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public NvkNews getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("Tin tức không tồn tại"));
    }
}