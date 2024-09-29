package backend.proyect_doctic_is1.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel,String>{

}