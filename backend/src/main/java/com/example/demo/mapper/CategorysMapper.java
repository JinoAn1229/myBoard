package com.example.demo.mapper;

import com.example.demo.vo.CategorysVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategorysMapper {

    List<CategorysVo> getCategorysList();
}
