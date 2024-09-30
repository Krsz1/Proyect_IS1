package backend.proyect_doctic_is1.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import backend.proyect_doctic_is1.Model.PublicationsModel;

@Repository
public interface IPublicationsRepository extends MongoRepository<PublicationsModel, String> {

    // Buscar todas las publicaciones que coincidan con el título (ignora mayúsculas/minúsculas)
    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<PublicationsModel> findAllByTitle(String title);

    // Buscar publicaciones por el ID del autor
    List<PublicationsModel> findByAuthorId(String id);

    // Buscar una publicación por su ID
    Optional<PublicationsModel> findById(String id);

    // Buscar metadatos de la publicación por ID, mostrando solo ciertos campos
    @Query(value = "{id: '?0'}", fields = "{'publicationDate': 1, 'authors': 1, 'description': 1, 'categories': 1}")
    Optional<PublicationsModel> findByIdMetadatos(String id);
}
