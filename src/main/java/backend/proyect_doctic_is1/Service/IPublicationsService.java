package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
    // Encontrar todas las publicaciones que coincidan con el título
    List<PublicationsModel> findAllbyTitle(String title);

    // Listar todas las publicaciones
    List<PublicationsModel> listAll();

    // Encontrar publicaciones por autor
    List<PublicationsModel> getPublicationsByAuthor(String id);

    // Encontrar una publicación por su Id
    Optional<PublicationsModel> findPublicationsByid(String id);

    // Encontrar metadatos de la publicación por Id
    Optional<PublicationsModel> findByIdMetadatos(String id);

    // Método para filtrar publicaciones
    List<PublicationsModel> filterPublications(LocalDate startDate, String categoryId, String keyword, String description);

    // Método para registrar la descarga de una publicación
    void registerDownload(PublicationsModel publication, String username);

}

//:)