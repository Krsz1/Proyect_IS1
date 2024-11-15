package backend.proyect_doctic_is1.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.proyect_doctic_is1.Exception.RecursoNoEncontrado;
import backend.proyect_doctic_is1.Model.PublicationsModel;
import backend.proyect_doctic_is1.Model.UsersModel;
import backend.proyect_doctic_is1.Service.IPublicationsService;
import backend.proyect_doctic_is1.Service.IUserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IPublicationsService publicationsService;

    // endpoint para el login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String,String> credentials){
        String email = credentials.get("email");
        String password = credentials.get("password");

        UsersModel user = userService.authenticate(email, password);
        if (user != null) {
            return ResponseEntity.ok(Map.of("userId", user.getId())); // Devolver el userId en la respuesta
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    //endpoint para el registro
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UsersModel user, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado");
        }

        UsersModel registeredUser = userService.registerUser(user);
        return ResponseEntity.ok("Usuario registrado exitosamente con ID: " + registeredUser.getId());
    }


    // Endpoint para ver los datos del usuario
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UsersModel> getUserProfile(@PathVariable String userId) {
        UsersModel user = userService.getUserProfile(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Endpoint para editar los datos del usuario
    @PutMapping("/profile/{userId}")
    public ResponseEntity<UsersModel> updateUserProfile(@PathVariable String userId, @RequestBody UsersModel updatedUser) {
        UsersModel user = userService.updateUserProfile(userId, updatedUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
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

}
