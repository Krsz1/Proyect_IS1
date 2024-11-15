package backend.proyect_doctic_is1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.proyect_doctic_is1.Model.UsersModel;
import backend.proyect_doctic_is1.Service.UsersService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsersController {

    @Autowired
    private UsersService usersService;  

    @PostMapping("/crear")
    public ResponseEntity<?> createUser(@RequestBody @Valid UsersModel user) {
        try {
            return ResponseEntity.ok(usersService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> login(@RequestParam String usernameOrEmail, @RequestParam String password) {
        try {
            return ResponseEntity.ok(usersService.login(usernameOrEmail, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.ok("Cuenta eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UsersModel updatedUser) {
        try {
            return ResponseEntity.ok(usersService.updateUser(id, updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            usersService.changePassword(email, oldPassword, newPassword);
            return ResponseEntity.ok("Contraseña cambiada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cambiar-pregunta-seguridad")
    public ResponseEntity<?> changeSecurityQuestion(@RequestParam String email, @RequestParam String newQuestion, @RequestParam String newAnswer) {
        try {
            usersService.changeSecurityQuestion(email, newQuestion, newAnswer);
            return ResponseEntity.ok("Pregunta de seguridad actualizada.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
