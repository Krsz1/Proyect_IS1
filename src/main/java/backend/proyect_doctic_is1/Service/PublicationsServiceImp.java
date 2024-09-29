package backend.proyect_doctic_is1.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Repository.IPublicationsRepository;

@Service
public class PublicationsServiceImp implements IPublicationsService{
    @Autowired
    IPublicationsRepository publicationsRepository;

    @Override
    public List<PublicationsModel> findAllbyTitle(String title) {
        
        return publicationsRepository.findAllByTitle(title);
    }

    @Override
    public List<PublicationsModel> listAll() {
        return publicationsRepository.findAll();
    }


}

