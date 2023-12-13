package com.blogapplication.controllers;


import com.blogapplication.payload.CategoryDto;
import com.blogapplication.service.CategoryService;
import com.blogapplication.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //build addCategory restapi
    @PostMapping("/addcategory")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Category REST API" ,
            description = "Create Category REST API is used to create category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto)
    {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/getcategory/{id}")
    @Operation(
            summary = "Get Category REST API" ,
            description = "Get Category REST API is used to get category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }


    @GetMapping("/getallcategories")
    @Operation(
            summary = "Get Categories REST API" ,
            description = "Get Categories REST API is used to get categories from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) int pageNo,
                                                              @RequestParam(value = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE ,required = false) int pageSize ,
                                                              @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                              @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
    {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNo,pageSize,sortBy,sortDir));
    }

    @PutMapping("/updatecategorybyid/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update Category REST API" ,
            description = "Update Category REST API is used to update category in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id,@RequestBody CategoryDto category)
    {
        return ResponseEntity.ok(categoryService.updateCategory(id,category));
    }

    @DeleteMapping("/deletebyid/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete Category REST API" ,
            description = "Delete Category REST API is used to delete category in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
