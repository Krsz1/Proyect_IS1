package backend.proyect_doctic_is1.Controller;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.proyect_doctic_is1.Exception.FileNotFoundExceptionCustom;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Service.IPublicationsService;
import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/publications")
@RestController
public class PublicationsController {

    @Autowired
    private IPublicationsService publicationsService;

    // Endpoint para filtrar publicaciones
    @GetMapping("/filter")
    public ResponseEntity<List<PublicationsModel>> filterPublications(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) String categoryId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String description) {

        List<PublicationsModel> publications = publicationsService.filterPublications(startDate, categoryId, keyword, description);
        if (publications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(publications);
    }

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

    // Método para cargar el archivo
    private Resource loadFileAsResource(String filePath) {
        try {
            Path file = Paths.get(filePath).normalize();
            Resource resource = new UrlResource(file.toUri());  

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundExceptionCustom("Archivo no encontrado: " + filePath);
            }
        } catch (Exception ex) {
            throw new FileNotFoundExceptionCustom("Archivo no encontrado: " + filePath, ex);
            
        }
    }

    // Descargar publicación en PDF
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadPublication(@PathVariable String id, HttpServletRequest request) {
        Optional<PublicationsModel> publicationOptional = publicationsService.findPublicationsByid(id);

        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        PublicationsModel publication = publicationOptional.get();

        // Aquí obtendrías la URL del archivo PDF (publicado previamente)
        String urlFile = publication.getUrlFiles();

        // Cargar el archivo PDF como un recurso
        Resource resource = loadFileAsResource(urlFile);

        // Registrar la descarga
        publicationsService.registerDownload(publication, request.getUserPrincipal().getName());

        // Devolver el archivo al usuario
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + publication.getTitle() + ".pdf\"")
                .body(resource);
    }
}
