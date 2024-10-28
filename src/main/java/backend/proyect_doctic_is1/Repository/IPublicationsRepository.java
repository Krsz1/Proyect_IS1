package backend.proyect_doctic_is1.Repository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    //List<PublicationsModel> findAll();

    @Query("{ $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<PublicationsModel> searchByTitleOrDescription(String searchTerm);
    
    // Usar @Query para manejar la lógica de búsqueda
    @Query("{ $and: [ "
            + " { 'publicationDate': { $gte: ?0, $lte: ?1 } }, "
            + " { $or: [ { 'categories.name': ?2 }, { '?2': { $exists: false } } ] }, "
            + " { $or: [ { 'title': { $regex: ?3, $options: 'i' } }, { 'description': { $regex: ?4, $options: 'i' } }, "
            + " { '?3': { $exists: false } } ] } ] }")
    List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryName, String keyword, String description);


    //buscar los metadatos de la publicacion por id
    @Query(value = "{id:'?0'}", fields = "{'publicationDate':1 , 'authors':1 , 'description':1, 'categories':1, 'docsFilesInfo':1}")
    Optional<PublicationMetadatos> findMetadatosById(ObjectId id);

    //buscar las publicaciones de un autor
    @Query("{'authors.username':?0}")
    List<PublicationsModel> findByAuthor(String username);

    // Consulta para obtener todas las publicaciones públicas
    List<PublicationsModel> findByVisibility(String visibility);

    // Método para encontrar todas las publicaciones de un usuario
    List<PublicationsModel> findByAuthors_idUser(String userId);

    //Metodo para eliminar una publicacion    
    List<PublicationsModel> getPublicationsByAuthor(String authorId);   
           
    // Método para buscar todas las publicaciones por idUser (el autor)     
    List<PublicationsModel> findByIdUser(String idUser);

    // Método personalizado para buscar publicaciones por usuario     
    List<PublicationsModel> findByUserId(Long userId);     
    // Búsqueda por título     
    List<PublicationsModel> findByTitleContainingIgnoreCase(String title);     
    // Búsqueda por autor (filtrando por el ID del usuario autor)     
    List<PublicationsModel> findByAuthorsContains(String idUser);     
    // Búsqueda por categoría     
    List<PublicationsModel> findByCategories_id(String idCategory);     
    // Búsqueda por fecha de publicación (ejemplo de rango)     
    @Query("SELECT p FROM PublicationsModel p WHERE p.publicationDate BETWEEN :startDate AND :endDate")    
    List<PublicationsModel> findByPublicationDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate); 
    

}

//Krs