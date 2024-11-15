package backend.proyect_doctic_is1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.proyect_doctic_is1.Model.CategoriesModel;
import backend.proyect_doctic_is1.Service.ICategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoriesModel>> getAllCategories(){
        List<CategoriesModel> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
