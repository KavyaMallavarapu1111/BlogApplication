package com.blogapplication.service.Impl;

import com.blogapplication.Entity.Category;
import com.blogapplication.Entity.Post;
import com.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.payload.CategoryDto;
import com.blogapplication.repository.CategoryRepository;
import com.blogapplication.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    //Autowired annotation is not required bcz whenever there is a single parameterized constructor, spring bean will automatically inject the dependencies.

    @Autowired
    public CategoryServiceImpl (CategoryRepository categoryRepository,ModelMapper modelMapper)
    {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Category> cates = categoryRepository.findAll(pageable);
        List<Category> categories = cates.getContent();
        List<CategoryDto> categoryDtos = categories.stream().map((category -> modelMapper.map(category,CategoryDto.class))).toList();
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto category) {
        Category fetchCategory = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        fetchCategory.setName(category.getName());
        fetchCategory.setDescription(category.getDescription());
        Category cats = categoryRepository.save(fetchCategory);
        return modelMapper.map(cats,CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long id) {
        Category fetchCategory = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        categoryRepository.delete(fetchCategory);
        return "Deleted Successfully....";
    }
}
