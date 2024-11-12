package backend.proyect_doctic_is1.Service;

import backend.proyect_doctic_is1.Model.UsersModel;
import backend.proyect_doctic_is1.Repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private IUsersRepository usersRepository;

    @Override
    public UsersModel createUser(UsersModel user) throws Exception {
        if (usersRepository.existsByUsername(user.getUsername())) {
            throw new Exception("El nombre de usuario ya está en uso.");
        }
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new Exception("El correo ya está en uso.");
        }
        return usersRepository.save(user);
    }

    @Override
    public UsersModel login(String usernameOrEmail, String password) throws Exception {
        Optional<UsersModel> user = usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user.isEmpty() || !user.get().getCredentials().getPassword().equals(password)) {
            throw new Exception("Usuario o contraseña incorrecta.");
        }
        return user.get();
    }

    @Override
    public void deleteUser(String id) throws Exception {
        if (!usersRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado.");
        }
        usersRepository.deleteById(id);
    }

    @Override
    public UsersModel updateUser(String id, UsersModel updatedUser) throws Exception {
        UsersModel user = usersRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado."));

        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getProfilePicture() != null) user.setProfilePicture(updatedUser.getProfilePicture());

        return usersRepository.save(user);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) throws Exception {
        UsersModel user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new Exception("Correo no registrado."));
        if (!user.getCredentials().getPassword().equals(oldPassword)) {
            throw new Exception("La contraseña actual es incorrecta.");
        }
        if (!newPassword.matches("^(?=.*[A-Z]).{8,}$")) {
            throw new Exception("La nueva contraseña debe tener al menos 8 caracteres y una letra mayúscula.");
        }
        user.getCredentials().setPassword(newPassword);
        usersRepository.save(user);
    }

    @Override
    public void changeSecurityQuestion(String email, String newQuestion, String newAnswer) throws Exception {
        UsersModel user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new Exception("Correo no registrado."));
        user.getSecurityQuestion().setQuestion(newQuestion);
        user.getSecurityQuestion().setAnswer(newAnswer);
        usersRepository.save(user);
    }

    @Override
    public void recoverPassword(String email, String securityAnswer) throws Exception {
        UsersModel user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new Exception("Correo no registrado."));
        if (!user.getSecurityQuestion().getAnswer().equalsIgnoreCase(securityAnswer)) {
            throw new Exception("Respuesta de seguridad incorrecta.");
        }
        // Implementa la lógica para enviar un correo de recuperación si es necesario
    }
}
