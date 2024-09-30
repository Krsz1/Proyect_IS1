package backend.proyect_doctic_is1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Buscar publicaciones por autor
    @GetMapping("/author/{idUser}")
    public ResponseEntity<List<PublicationsModel>> getPublicationsByAuthor(@PathVariable String idUser) {
        List<PublicationsModel> publications = publicationsService.getPublicationsByAuthor(idUser);
        if (publications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publications);
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
}
