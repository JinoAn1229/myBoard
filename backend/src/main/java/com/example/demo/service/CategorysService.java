package com.example.demo.service;

import com.example.demo.mapper.CategorysMapper;
import com.example.demo.vo.CategorysVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorysService  {
    @Autowired
    private CategorysMapper dao;


    public List<CategorysVo> getCategorysList() {
        return dao.getCategorysList();
    }
}
