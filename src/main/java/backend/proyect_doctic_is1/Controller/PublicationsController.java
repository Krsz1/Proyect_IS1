package backend.proyect_doctic_is1.Controller;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Exception.RecursoNoEncontrado;
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
        return new ResponseEntity<List<PublicationsModel>>(publications, HttpStatus.OK);
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
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String description) {

        List<PublicationsModel> publications = publicationsService.filterPublications(startDate, endDate, categoryName, keyword, description);
        if (publications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(publications);
    }

    // Buscar metadatos de la publicación por ID
    @GetMapping("/metadatos/{id}")
    public ResponseEntity<?> findByIdMetadatos (@PathVariable ObjectId id){
        try {
            PublicationMetadatos publicationMetadatos = publicationsService.findByIdMetadatos(id);
            return ResponseEntity.ok(publicationMetadatos);
        } catch (RecursoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Metodo para buscar las publicaciones por nombre de autor
    @GetMapping("/author/{name}")
    public ResponseEntity<?> getPublicationesByAuthor(@PathVariable String name){
        try {
            List<PublicationsModel> publications = publicationsService.findByAuthor(name);
            return ResponseEntity.ok(publications);
        } catch (RecursoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
    }


     // Metodo para Buscar publicacion por Id
    @GetMapping("{id}")
     public ResponseEntity<?> findById (@PathVariable String id){
         try {
             PublicationsModel publication = publicationsService.findPublicationsByid(id);
             return ResponseEntity.ok(publication);
         } catch (RecursoNoEncontrado e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }
        


    // Metodo para descargar los archivos 
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String id){
        PublicationsModel publication = publicationsService.findPublicationsByid(id);

        // Descargar el documento desde el Url
        String fileUrl = publication.getUrlFiles();
        RestTemplate restTemplate = new RestTemplate();
        byte[] fileBytes = restTemplate.getForObject(fileUrl, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + publication.getTitle()+".pdf");
        
        return new ResponseEntity<>(fileBytes, headers , HttpStatus.OK);
    }

    

}    

//Krs