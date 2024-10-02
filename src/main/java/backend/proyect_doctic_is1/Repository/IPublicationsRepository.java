package backend.proyect_doctic_is1.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    List<PublicationsModel> findAll();

    @Query("{ $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<PublicationsModel> searchByTitleOrDescription(String searchTerm);
    
    // Usar @Query para manejar la lógica de búsqueda
    @Query("{ $and: [ "
            + " { 'publicationDate': { $gte: ?0, $lte: ?1 } }, "
            + " { $or: [ { 'categories.idCategoria': ?2 }, { '?2': { $exists: false } } ] }, "
            + " { $or: [ { 'title': { $regex: ?3, $options: 'i' } }, { 'description': { $regex: ?4, $options: 'i' } }, "
            + " { '?3': { $exists: false } } ] } ] }")
    List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryId, String keyword, String description);

    Optional<PublicationsModel> findById(String idDocument);

    // Buscar metadatos de la publicación por Id
    @Query(value = "{idDocument:'?0'}", fields = "{'publicationDate':1 , 'authors':1 , 'description':1, 'categories':1}")
    Optional<PublicationsModel> findByIdMetadatos(String idDocument);

    List<PublicationsModel> findByAuthorsIdUser(String authorId);

}