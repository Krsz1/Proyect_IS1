package backend.proyect_doctic_is1.Service;

import java.util.List;


import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
    
    // Listar todas las publicaciones
    List<PublicationsModel> listAll();

    // Buscar por tutulo o palabra clave
    List<PublicationsModel> searchPublicationsByTitleOrKeyWord(String searchTerm);

}

//:)