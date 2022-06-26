package com.example.demo.controller;

import com.example.demo.service.CategorysService;
import com.example.demo.vo.CategorysVo;
import com.example.demo.vo.CommentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategorysController {

    @Autowired
    CategorysService categorysService;

    @GetMapping("/categorys")
    public ResponseEntity<Map<String,Object>> commentsList() {

        List<CategorysVo> categorysList = categorysService.getCategorysList();

        Map<String, Object> result = new HashMap<>();
        result.put("list", categorysList);

        return ResponseEntity.ok().body(result);
    }
}
