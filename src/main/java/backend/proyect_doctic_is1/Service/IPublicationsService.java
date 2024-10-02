package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
    // Listar todas las publicaciones
    List<PublicationsModel> listAll();

    // Buscar por tutulo o palabra clave
    List<PublicationsModel> searchPublicationsByTitleOrDescription(String searchTerm);

    // Método para filtrar publicaciones
    List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryId, String keyword, String description);

    }

