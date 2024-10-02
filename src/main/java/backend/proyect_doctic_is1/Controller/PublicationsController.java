package backend.proyect_doctic_is1.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.proyect_doctic_is1.Exception.NoFoundException;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Service.IPublicationsService;

@RequestMapping("/api/publications")
@RestController
public class PublicationsController {

    @Autowired
    private IPublicationsService publicationsService;

    // Buscar publicación por título
    @GetMapping("/SearchByTitle/{title}")
    public ResponseEntity<List<PublicationsModel>> getPubBytitle(@PathVariable String title) {
        List<PublicationsModel> publications = publicationsService.findAllbyTitle(title);
        if (publications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publications);
    }

    // Listar todas las publicaciones
    @GetMapping("/listAll")
    public ResponseEntity<List<PublicationsModel>> listAll() {
        List<PublicationsModel> publications = publicationsService.listAll();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    // buscar publicaciones por autor S1
    @GetMapping("/publications/author/{authorId}")
    public ResponseEntity<List<PublicationsModel>> getPublicationsByAuthor(@PathVariable String authorId) {
        List<PublicationsModel> publications = publicationsService.getPublicationsByAuthor(authorId);
        if (!publications.isEmpty()) {
            return new ResponseEntity<>(publications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar publicación por ID
    @GetMapping("/{id}")
    public ResponseEntity<PublicationsModel> findById(@PathVariable String id) {
        Optional<PublicationsModel> publication = publicationsService.findPublicationsByid(id);
        return publication.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // Buscar metadatos de la publicación por ID
    @GetMapping("/metadatos/{id}")
    public ResponseEntity<PublicationsModel> findByIdMetadatos(@PathVariable String id) {
        Optional<PublicationsModel> metadatos = publicationsService.findByIdMetadatos(id);
        return metadatos.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // Método para obtener la información del autor de una publicación
    @GetMapping("/publications/{publicationId}/author")
    public ResponseEntity<?> getAuthorByPublicationId(@PathVariable String publicationId) {
        // Este método invoca el servicio

        try {

            User variable = publicationsService.findAuthorByPublicationId(publicationId);

            return new ResponseEntity<User>(variable, HttpStatus.OK);

        }
        catch(NoFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
        
        
    }



}