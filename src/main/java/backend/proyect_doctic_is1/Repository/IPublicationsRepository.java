package backend.proyect_doctic_is1.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    // Buscar publicaciones por título
    @Query("{titulo: '?0'}")
    List<PublicationsModel> findAllByTitle(String title);

    // Buscar publicaciones por el Id de un autor en la lista de autores
    List<PublicationsModel> findByAuthors_IdUser(String idUser);

    // Buscar metadatos de la publicación por Id
    @Query(value = "{idDocument:'?0'}", fields = "{'publicationDate':1 , 'authors':1 , 'description':1, 'categories':1}")
    Optional<PublicationsModel> findByIdMetadatos(String idDocument);

    // Buscar publicacion por su id
    Optional<PublicationsModel> findById(String idDocument);

    // buscar publicaciones por autor S1
    public interface PublicationsRepository extends MongoRepository<PublicationsModel, String> {
        
        List<PublicationsModel> findByAuthorsId(String authorId);
    }

    Optional<PublicationsModel> findByPublicationId(String publicationId2);
}

// :)