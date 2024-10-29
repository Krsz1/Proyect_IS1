package backend.proyect_doctic_is1.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Exception.RecursoNoEncontrado;
import backend.proyect_doctic_is1.Model.ENUM.userRoleAuthors;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Repository.IPublicationsRepository;

@Service
public class PublicationsServiceImp implements IPublicationsService {

    @Autowired
    private IPublicationsRepository publicationsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;  // Inyectar MongoTemplate correctamente

    @Override
    public List<PublicationsModel> listAll() {
        return publicationsRepository.findAll();
    }

    @Override
    public List<PublicationsModel> searchPublicationsByTitleOrDescription(String keyword) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
            Criteria.where("title").regex(keyword, "i"),
            Criteria.where("description").regex(keyword, "i")
        ));
        return mongoTemplate.find(query, PublicationsModel.class);
    }

    @Override
    public List<PublicationsModel> filterPublications(LocalDate startDate, LocalDate endDate, String categoryName, String keyword, String description) {
        Query query = new Query();

        if (startDate != null && endDate != null) {
            query.addCriteria(Criteria.where("publicationDate").gte(startDate).lte(endDate));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.addCriteria(Criteria.where("categories.name").regex(categoryName));
        }
        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(keyword, "i"),
                Criteria.where("description").regex(keyword, "i")
            ));
        }
        if (description != null && !description.isEmpty()) {
            query.addCriteria(Criteria.where("description").regex(description, "i"));
        }

        return mongoTemplate.find(query, PublicationsModel.class);
    }


    // Metodo para buscar los metadatos de la publicacion
    @Override
    public PublicationMetadatos findByIdMetadatos(ObjectId id) {
        Optional<PublicationMetadatos> publicationMetadatos = publicationsRepository.findMetadatosById(id);
        return publicationMetadatos.orElseThrow(()-> new RecursoNoEncontrado("la publicacion no existe en la BD"));
    }

    // Metodo para buscar las publicaciones de un autor
    @Override
    public List<PublicationsModel> findByAuthor(String username) {
        List<PublicationsModel> publications = publicationsRepository.findByAuthor(username);

        if (publications.isEmpty()) {
            throw new RecursoNoEncontrado("El autor no tiene publicaciones disponibles ");
        }

        return publications;
    }

    // metodo para buscar publicacion por id
    @Override
    public PublicationsModel findPublicationsByid(String id) {
        Optional<PublicationsModel> publication = publicationsRepository.findById(id);
        return publication.orElseThrow(()-> new RecursoNoEncontrado("la publicacion no existe en la BD"));
    }

    @Override
    // Método para obtener todas las publicaciones públicas
    public List<PublicationsModel> getAllPublicPublications() {
        return publicationsRepository.findByVisibility("publics");
    }


    //Metodo para Crear una publicacion
    @Override
    public PublicationsModel createPublication(MultipartFile file, PublicationsModel publication) throws IOException {
        publication.setData(file.getBytes());
        publication.setType(file.getContentType());
        return publicationsRepository.save(publication);

    }

    // Metodo para actualizar una publicacion
    @Override
    public String updatePublication(PublicationsModel publication, String id) {
        Optional<PublicationsModel> publicationRecuperada = publicationsRepository.findById(id);

        PublicationsModel publicationsModel = publicationRecuperada.get();
        publicationsModel.setTitle(publication.getTitle());
        publicationsModel.setDescription(publication.getDescription());
        publicationsModel.setVisibility(publication.getVisibility());

        publicationsRepository.save(publicationsModel);
        return "La Publicacion con el id: "+publicationsModel.getId()+" Se actualizo Correctamente.";


    }

    @Override
    public Optional<PublicationsModel> descargar(String id) throws IOException {
        Optional<PublicationsModel> publication = publicationsRepository.findById(id);
        if (publication.isPresent()) {
            return publication;
        }
        throw new FileNotFoundException();
    }

    @Override
    public boolean deletePublication(String publicationId, String idUser) {
    Optional<PublicationsModel> publicationOptional = publicationsRepository.findById(publicationId);
    if (!publicationOptional.isPresent()) {
        throw new RecursoNoEncontrado("La publicación no existe");
    }

    PublicationsModel publication = publicationOptional.get();

    // Verifica si el usuario tiene permisos para eliminar la publicación
    boolean hasPermission = publication.getAuthors().stream()
        .anyMatch(author -> author.getIdUser().equals(idUser) && author.getUserRoleAuthors().equals(userRoleAuthors.ADMIN));

    if (hasPermission) {
        publicationsRepository.deleteById(publicationId);
        return true;
    } else {
        return false; // No tiene permisos para eliminar
    }
    }
}


//Krs