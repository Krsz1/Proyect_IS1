package backend.proyect_doctic_is1.Controller;


import java.util.List;

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
    IPublicationsService publicationsService;

    @GetMapping("/SearchByTitle/{title}")
    public ResponseEntity<List<PublicationsModel>> getPubBytitle(@PathVariable String title){
        List<PublicationsModel> publication = publicationsService.findAllbyTitle(title);
        if(publication.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(publication);
    }

    public ResponseEntity<List<PublicationsModel>> listAll(){
        
        return new ResponseEntity<List<PublicationsModel>>(publicationsService.listAll(), HttpStatus.OK);
    }
}