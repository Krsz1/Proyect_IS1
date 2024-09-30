package backend.proyect_doctic_is1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Repository.IPublicationsRepository;

@Service
public class PublicationsServiceImp implements IPublicationsService {

    @Autowired
    private IPublicationsRepository publicationsRepository;

    @Override
    public List<PublicationsModel> findAllbyTitle(String title) {
        return publicationsRepository.findAllByTitle(title);
    }

    @Override
    public List<PublicationsModel> listAll() {
        return publicationsRepository.findAll();
    }

    @Override
    public List<PublicationsModel> getPublicationsByAuthor(String idUser) {
        return publicationsRepository.findByAuthors_IdUser(idUser);
    }

    @Override
    public Optional<PublicationsModel> findPublicationsByid(String idDocument) {
        return publicationsRepository.findById(idDocument);
    }

    @Override
    public Optional<PublicationsModel> findByIdMetadatos(String idDocument) {
        return publicationsRepository.findByIdMetadatos(idDocument);
    }
}

//:) 