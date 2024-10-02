package backend.proyect_doctic_is1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/search")
    public ResponseEntity<List<PublicationsModel>> searchPublications(@RequestParam String query) {
        List<PublicationsModel> publications = publicationsService.searchPublications(query);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }
}    
