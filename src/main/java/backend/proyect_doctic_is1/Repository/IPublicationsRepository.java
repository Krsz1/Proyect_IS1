package backend.proyect_doctic_is1.Repository;

import java.util.Optional; 
import org.springframework.data.mongodb.repository.Query; 

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    List<PublicationsModel> findAll();

    List<PublicationsModel> findByTitleOrDescription(String title, String description);

}

//:)