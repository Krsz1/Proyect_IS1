package backend.proyect_doctic_is1.Repository;

import java.util.Optional; 
import org.springframework.data.mongodb.repository.Query; 

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    // Buscar publicaciones por título
    List<PublicationsModel> findAllByTitle(String title);

    // Buscar publicaciones por el Id de un autor en la lista de autores
    List<PublicationsModel> findByAuthors_IdUser(String idUser);

    // Filtrar publicaciones por criterios como fecha, categoría, subcategoría, palabras clave
    @Query("{ $and: [ { 'publicationDate': { $gte: ?0, $lte: ?1 } }, { 'categories.idCategoria': ?2 }, { 'title': { $regex: ?3, $options: 'i' } }, { 'description': { $regex: ?4, $options: 'i' } } ] }")
    List<PublicationsModel> filterPublications(LocalDate startDate, String categoryId, String keyword, String description);

    // Buscar metadatos de la publicación por Id
    @Query(value = "{idDocument:'?0'}", fields = "{'publicationDate':1 , 'authors':1 , 'description':1, 'categories':1}")
    Optional<PublicationsModel> findByIdMetadatos(String idDocument);
}

//:)