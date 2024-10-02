package backend.proyect_doctic_is1.Controller;

import java.time.LocalDate;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Service.IPublicationsService;

@RequestMapping("/api/publications")
@RestController
public class PublicationsController {
    
    @Autowired
    private IPublicationsService publicationsService;

    @GetMapping("/listAllPublications")
    public ResponseEntity<List<PublicationsModel>> getAllPublications() {
        List<PublicationsModel> publications = publicationsService.listAll();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<PublicationsModel>> searchPublications(@PathVariable String searchTerm) {
        List<PublicationsModel> publications = publicationsService.searchPublicationsByTitleOrDescription(searchTerm);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }
    
    // Endpoint para filtrar publicaciones
    @GetMapping("/filter")
    public ResponseEntity<List<PublicationsModel>> filterPublications(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) String categoryId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String description) {

        List<PublicationsModel> publications = publicationsService.filterPublications(startDate, endDate, categoryId, keyword, description);
        if (publications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/view/{idDocument}")
    public ResponseEntity<PublicationsModel> viewPublication(@PathVariable String idDocument) {
        Optional<PublicationsModel> publication = publicationsService.viewPublication(idDocument);
        return publication.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Buscar metadatos de la publicación por ID
    @GetMapping("/metadata/{idDocument}")
    public ResponseEntity<Map<String, Object>> getMetadata(@PathVariable String idDocument) {
        return publicationsService.viewPublication(idDocument)
            .map(publication -> {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("title", publication.getTitle());
                metadata.put("description", publication.getDescription());
                metadata.put("publicationDate", publication.getPublicationDate());
                metadata.put("authors", publication.getAuthors());
                return ResponseEntity.ok(metadata);
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    //Acceder a la información del autor desde la publicación        
    @GetMapping("/author-info/{idDocument}")
    public ResponseEntity<List<PublicationsModel.Authors>> getAuthorInfo(@PathVariable String idDocument) {
        return publicationsService.viewPublication(idDocument)
            .map(publication -> ResponseEntity.ok(publication.getAuthors()))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/author/{authorId}/publications")
    public ResponseEntity<List<PublicationsModel>> getPublicationsByAuthor(@PathVariable String authorId) {
        List<PublicationsModel> publications = publicationsService.getPublicationsByAuthor(authorId);
        if (publications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.ok(publications);
    }
}    
