package backend.proyect_doctic_is1.Controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    
    // Endpoint para obtener todas las publicaciones con visibilidad pública
    @GetMapping("/public")
    public ResponseEntity<List<PublicationsModel>> getPublicPublications() {
        List<PublicationsModel> publications = publicationsService.getAllPublicPublications();
        if (publications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/MostValued")
    public ResponseEntity<List<PublicationsModel>> sortByRating(){
        List<PublicationsModel> publications = publicationsService.sortMostValuedPublications();
        if(publications.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/MostSeen")
    public ResponseEntity<List<PublicationsModel>> sortByviews(){
        List<PublicationsModel> publications = publicationsService.sortMostSeenPublications();
        if(publications.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publications);
    }

    // Endpoint para obtener todas las publicaciones de un usuario por su ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PublicationsModel>> getPublicationsByUserId(@PathVariable String userId) {
        List<PublicationsModel> publications = publicationsService.getPublicationsByUserId (userId);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @PostMapping("/createPublication")
    public ResponseEntity<String> crearPublicacion(@RequestBody PublicationsModel publicaacion){
        return new ResponseEntity<String>(publicationsService.guardarPublicacion(publicaacion), HttpStatus.CREATED);
    }

    //aca
    @DeleteMapping("/{publicationId}")     public ResponseEntity<String> deletePublication(@PathVariable String publicationId, @RequestParam String userId) {         boolean isDeleted = publicationsService.deletePublication(publicationId, userId);         if (isDeleted) {             return new ResponseEntity<>("Publicación eliminada correctamente", HttpStatus.OK);         } else {             return new ResponseEntity<>("No tiene permisos para eliminar esta publicación", HttpStatus.FORBIDDEN);         }     }     public PublicationsController(PublicationsModel publicationsService) {         this.publicationsService = (IPublicationsService) publicationsService;     }     // Método para listar publicaciones por cliente (idUser)     @GetMapping("/user/{idUser}")     public List<PublicationsModel> getPublicationsByUser(@PathVariable String idUser) {         return publicationsService.findByUserId(idUser);     } } // Método GET para filtrar publicaciones
    @GetMapping("/filter")
    public ResponseEntity<List<PublicationsModel>> filterPublications(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "idUser", required = false) String idUser,
            @RequestParam(value = "idCategory", required = false) String idCategory,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<PublicationsModel> filteredPublications = publicationsService.filterPublications(title, idUser, idCategory, startDate, endDate);

        if (filteredPublications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(filteredPublications, HttpStatus.OK);
    }
}//end controller    

//Krs