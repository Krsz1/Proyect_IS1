package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
    // Listar todas las publicaciones
    List<PublicationsModel> listAll();

    // Buscar por tutulo o palabra clave
    List<PublicationsModel> searchPublicationsByTitleOrDescription(String searchTerm);

    // Método para filtrar publicaciones
    List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryId, String keyword, String description);

    // Método para visualizar una publicación por su ID
    Optional<PublicationsModel> viewPublication(String idDocument);

    // Encontrar metadatos de la publicación por Id
    Optional<PublicationsModel> findByIdMetadatos(String id);
    
    List<PublicationsModel> getPublicationsByAuthor(String authorId);
    }

