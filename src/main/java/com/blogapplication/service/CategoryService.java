package com.blogapplication.service;

import com.blogapplication.payload.CategoryDto;
import java.util.List;
public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories(int pageNo,int pageSize,String sortBy,String sortDir);
    CategoryDto updateCategory(Long id,CategoryDto category);
    String deleteCategory(Long id);
}
