package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Repository.IPublicationsRepository;

import backend.proyect_doctic_is1.Model.UsersModel;
import backend.proyect_doctic_is1.Repository.IUsersRepository;


@Service
public class PublicationsServiceImp implements IPublicationsService {

    @Autowired
    private IPublicationsRepository publicationsRepository;
    private IUsersRepository usersRepository;

    @Override
    public List<PublicationsModel> filterPublications(LocalDate startDate, String categoryId, String keyword, String description) {
        return publicationsRepository.filterPublications(startDate, categoryId, keyword, description);
    }

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

    @Override
    public void registerDownload(PublicationsModel publication, String username) {
        UsersModel user = usersRepository.findByUsername(username);
    
        // Sirve para Registrar la descarga
        UsersModel.DownloadedDocs downloadedDoc = new UsersModel.DownloadedDocs(publication.getIdDocument(), publication.getTitle(), LocalDate.now());
        user.getDownloadedDocs().add(downloadedDoc);
    
        usersRepository.save(user);
    }
}

//:)