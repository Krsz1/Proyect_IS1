package backend.proyect_doctic_is1.Service;

import java.util.List;

import backend.proyect_doctic_is1.Model.PublicationsModel;

public interface IPublicationsService {
   
    List<PublicationsModel> findAllbyTitle(String title);

    List<PublicationsModel> listAll();
}