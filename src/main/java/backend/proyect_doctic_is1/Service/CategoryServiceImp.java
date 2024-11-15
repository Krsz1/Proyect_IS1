package backend.proyect_doctic_is1.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.CategoriesModel;
import backend.proyect_doctic_is1.Repository.ICategoriesRepository;

@Service
public class CategoryServiceImp implements ICategoryService{
    
    @Autowired
    private ICategoriesRepository categoriesRepository;

    @Override
    public List<CategoriesModel> getAllCategories() {
        return categoriesRepository.findAll();
    }

    
}
