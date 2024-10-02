package backend.proyect_doctic_is1.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import backend.proyect_doctic_is1.Model.CategoriesModel;

public interface ICategoriesRepository extends  MongoRepository<CategoriesModel, String> {

    
}
