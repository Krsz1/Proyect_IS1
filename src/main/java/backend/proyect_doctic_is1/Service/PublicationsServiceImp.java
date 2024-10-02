package backend.proyect_doctic_is1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Exception.NoFoundException;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Model.UsersModel;
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

    // service Invocado S1
    @Override
    public List<PublicationsModel> getPublicationsByAuthor(String authorId) {
        return publicationsRepository.findByAuthors_IdUser(authorId);
    }

    @Override
    public Optional<PublicationsModel> findPublicationsByid(String idDocument) {
        return publicationsRepository.findById(idDocument);
    }

    @Override
    public Optional<PublicationsModel> findByIdMetadatos(String idDocument) {
        return publicationsRepository.findByIdMetadatos(idDocument);
    }

    @Override
    public List<UsersModel> findAuthorByPublicationId(String publicationId2) {

        // ejemplo
        PublicationsModel usuario = publicationsRepository.findByPublicationId(publicationId2).orElseThrow(() -> new NoFoundException("No se encuentra"));

    return (List<UsersModel>) usuario;

        
    }

}

// :)