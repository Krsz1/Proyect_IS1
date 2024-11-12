package backend.proyect_doctic_is1.Model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;

import backend.proyect_doctic_is1.Model.ENUM.status;
import backend.proyect_doctic_is1.Model.ENUM.userRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class UsersModel {
    
    @Id
    private String id;
    
    @NotNull
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres.")
    private String username;
    
    @Email(message = "Debe ser un correo válido.")
    private String email;
    
    private userRole userRole;
    
    private LocalDate registrationDate;
    private String profilePicture;
    
    private Credentials credentials; // Subdocumento para almacenar las credenciales.
    
    private SecurityQuestion securityQuestion; 
    
    private List<DownloadedDocs> downloadedDocs; // List para almacenar documentos descargados.
    private List<DocHistory> docHistory; // Historial de documentos.

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credentials {
        
        @Pattern(regexp = "^(?=.*[A-Z]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres y una letra mayúscula.")
        private String password;
        
        private status status;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SecurityQuestion {
        
        @NotBlank(message = "La pregunta de seguridad no puede estar vacía.")
        private String question;
        
        @NotBlank(message = "La respuesta de seguridad no puede estar vacía.")
        private String answer;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DownloadedDocs {
        private String idDocument;
        private String title;
        private LocalDate date;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocHistory {
        private String idDocument;
        private String title;
        private LocalDate date;
    }
}
