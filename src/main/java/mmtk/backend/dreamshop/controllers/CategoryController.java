package mmtk.backend.dreamshop.controllers;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.exceptions.AlreadyExistsException;
import mmtk.backend.dreamshop.exceptions.ResourceNotFoundException;
import mmtk.backend.dreamshop.models.Category;
import mmtk.backend.dreamshop.responses.ApiResponse;

import mmtk.backend.dreamshop.services.category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ICategoryService iCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try{
            List<Category> categories = iCategoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try{
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added category", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try{
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found category", category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found category", category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try{
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted category", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try{
            Category updatedCategory = iCategoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Updated category", updatedCategory));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
