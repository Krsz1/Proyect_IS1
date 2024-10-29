package backend.proyect_doctic_is1.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
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

    // Metodo para crear una publicacion
    PublicationsModel createPublication(MultipartFile file, PublicationsModel publication)throws IOException;

    // Metodo para modificar una publicacion
    String updatePublication(PublicationsModel publication,String id);


    // Metodo para descargar
    Optional<PublicationsModel> descargar (String id)throws IOException;

    boolean deletePublication(String publicationId, String userId);

    }

    

//Krs