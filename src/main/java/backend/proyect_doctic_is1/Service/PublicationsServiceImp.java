package backend.proyect_doctic_is1.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Exception.RecursoNoEncontrado;
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

    @Override
    public List<PublicationsModel> sortMostValuedPublications() {
        return publicationsRepository.findAll(Sort.by(Sort.Direction.DESC, "DocsFilesInfo.avgRating"));
    }

    @Override
    public List<PublicationsModel> sortMostSeenPublications() {
        return publicationsRepository.findAll(Sort.by(Sort.Direction.DESC, "DocsFilesInfo.totalViews"));
    }

    // Lógica para obtener todas las publicaciones de un usuario por su ID
    public List<PublicationsModel> getPublicationsByUserId(String userId) {
        return publicationsRepository.findByAuthors_idUser(userId);
    }

    @Override
    public String guardarPublicacion(PublicationsModel publicacion) {
        publicationsRepository.save(publicacion);
        return "La publicacion fue guardada con exito";
    }
    


}

//Krs