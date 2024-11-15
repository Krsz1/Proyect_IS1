package backend.proyect_doctic_is1.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;

import backend.proyect_doctic_is1.DTOs.PublicationMetadatos;
import backend.proyect_doctic_is1.Exception.RecursoNoEncontrado;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Model.PublicationsModel.Rating;
import backend.proyect_doctic_is1.Response.ResponseMessage;
import backend.proyect_doctic_is1.Service.IPublicationsService;

@RequestMapping("/api/publications")
@RestController
@CrossOrigin(origins = "*")
public class PublicationsController {
    
    @Autowired
    private IPublicationsService publicationsService;

    @GetMapping("/listAllPublications")
    public List<PublicationsModel> getAllPublications(@RequestParam(value="search",required = false) String search) {
        List<PublicationsModel> publications = publicationsService.getAllPublicPublications();
        // Filtrar si se proporciona un término de búsqueda
        if (search != null && !search.isEmpty()) {
            String lowerCaseSearch = search.toLowerCase();
            publications = publications.stream()
                .filter(pub -> pub.getTitle().toLowerCase().contains(lowerCaseSearch)
                        || pub.getDescription().toLowerCase().contains(lowerCaseSearch)
                        || pub.getAuthors().stream().anyMatch(author -> author.getUsername().toLowerCase().contains(lowerCaseSearch)))
                .collect(Collectors.toList());
        }

        return publications;
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
             Optional<PublicationsModel> publication = publicationsService.findPublicationsByid(id);
             return ResponseEntity.ok(publication);
         } catch (RecursoNoEncontrado e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }
        


    // Metodo para descargar los archivos 
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String id){
        Optional<PublicationsModel> publication = publicationsService.findPublicationsByid(id);
        

        if (publication.isPresent() && publication.get().getData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filaname=\""+ publication.get().getTitle()+".pdf\"")
                    .body(publication.get().getData());
        }else{
            return ResponseEntity.notFound().build();
        }
        
        // String fileUrl = publication.getUrlFiles();
        // RestTemplate restTemplate = new RestTemplate();
        // byte[] fileBytes = restTemplate.getForObject(fileUrl, byte[].class);

        // HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + publication.getTitle()+".pdf");
        
        // return new ResponseEntity<>(fileBytes, headers , HttpStatus.OK);
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


    // Endpoint para crear las publicaciones
    @PostMapping("/createPublication")
    public ResponseEntity<ResponseMessage> createPublication(@RequestPart MultipartFile file, @RequestPart PublicationsModel publication) throws IOException{
        publicationsService.createPublication(file,publication);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Publicacion Subida Exitosamente"));
    }

    // EndPoint para descargar una publicacion
    @GetMapping("/descargar/{id}")
    public ResponseEntity<byte[]> descargar (@PathVariable String id) throws IOException {
        PublicationsModel publication = publicationsService.descargar(id).get();
        return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, publication.getType())
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + publication.getTitle()+"\"")
        .body(publication.getData());
    }

    //Endpoint para actualizar una pubicacion
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePublication(@RequestBody PublicationsModel publication, @PathVariable String id){
        try {
            Optional<PublicationsModel> publicationRecuperada = publicationsService.findPublicationsByid(id);
            if (publicationRecuperada != null) {
                return new ResponseEntity<String>(publicationsService.updatePublication(publication, id),HttpStatus.OK);
            }
        } catch (RecursoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return null;
    }

    // Método para eliminar una publicación por su ID
    @DeleteMapping("/{publicationId}")
    public ResponseEntity<String> deletePublication(@PathVariable String publicationId, @RequestParam String idUser) {
        try {
            boolean isDeleted = publicationsService.deletePublication(publicationId, idUser);
             if (isDeleted) {
            return new ResponseEntity<>("Publicación eliminada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No tiene permisos para eliminar esta publicación", HttpStatus.FORBIDDEN);
        }
    } catch (RecursoNoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
//listar publicaciones por autor
@GetMapping("/buscarPorAutor/{username}")
public ResponseEntity<List<PublicationsModel>> findByAuthor(@PathVariable String username) {
    List<PublicationsModel> publicaciones = publicationsService.findByAuthor(username);
    return ResponseEntity.ok(publicaciones); // Cambiando a ResponseEntity.ok()
}

@PutMapping("/{id}/addComment")
public ResponseEntity<PublicationsModel> addComment(@PathVariable String id, @RequestBody Rating comment){
    Optional<PublicationsModel> publication = publicationsService.findPublicationsByid(id);

    if (publication.isPresent()) {
        PublicationsModel pub = publication.get();
        pub.getRatings().add(comment);
        // Aumentar el contador de totalComments en docsFilesInfo
        if (pub.getDocsFilesInfo() != null && !pub.getDocsFilesInfo().isEmpty()) {
            pub.getDocsFilesInfo().get(0).setTotalComments(pub.getDocsFilesInfo().get(0).getTotalComments() + 1);
        } else {
            // Inicializar docsFilesInfo si está vacío y añadir totalComments
            PublicationsModel.DocsFilesInfo docsInfo = new PublicationsModel.DocsFilesInfo();
            docsInfo.setTotalComments(1);
            pub.setDocsFilesInfo(Collections.singletonList(docsInfo));
        }
        publicationsService.save(pub);
        return ResponseEntity.ok(pub);
    }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
}

  

//Krs