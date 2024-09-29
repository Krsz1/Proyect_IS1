package backend.proyect_doctic_is1.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel,String>{

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<PublicationsModel> findAllByTitle(String title);

}