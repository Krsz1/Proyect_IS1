package backend.proyect_doctic_is1.DTOs;

import backend.proyect_doctic_is1.Model.ENUM.userRole;
import backend.proyect_doctic_is1.Model.ENUM.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private String id;  
    private String username;
    private String email;
    private userRole userRole;  
    private LocalDate registrationDate;  
    private ProfileDTO profile;  
    private List<CredentialDTO> credentials;  
    private SecurityQuestionDTO securityQuestion;  
}

@Data
@AllArgsConstructor
@NoArgsConstructor

class ProfileDTO {
    private String fullName;
    private String profilePicture;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CredentialDTO {
    private String password;
    private status status;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SecurityQuestionDTO {
    private String question;
    private String answerQuestion;
}
