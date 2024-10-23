package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
    //crear Publicacion
    String guardarPublicacion(PublicationsModel publicacion);

    // Listar todas las publicaciones
    List<PublicationsModel> listAll();

    // Buscar por tutulo o palabra clave
    List<PublicationsModel> searchPublicationsByTitleOrDescription(String searchTerm);

    // Método para filtrar publicaciones
    List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryName, String keyword, String description);


    // Metodo para buscar los metadatos de la publicacion
    PublicationMetadatos findByIdMetadatos (ObjectId id);

    //Metodo para buscar las publicaciones de un autor
    List<PublicationsModel> findByAuthor(String username);

    // Metodo para buscar la publicacion por id
    PublicationsModel findPublicationsByid(String id);

    // Metodo para buscar las publicas 
    List<PublicationsModel> getAllPublicPublications();

    List<PublicationsModel> sortMostValuedPublications();

    List<PublicationsModel> sortMostSeenPublications();

    List<PublicationsModel> getPublicationsByUserId(String id);

    boolean deletePublication(String publicationId, String userId);

    List<PublicationsModel> findByUserId(String idUser);
    
    }//end class

    

//Krs