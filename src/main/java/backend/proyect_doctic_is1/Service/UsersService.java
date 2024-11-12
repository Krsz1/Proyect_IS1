package backend.proyect_doctic_is1.Service;

import backend.proyect_doctic_is1.Model.UsersModel;

public interface UsersService {
    UsersModel createUser(UsersModel user) throws Exception;
    UsersModel login(String usernameOrEmail, String password) throws Exception;
    void deleteUser(String id) throws Exception;
    UsersModel updateUser(String id, UsersModel updatedUser) throws Exception;
    void changePassword(String email, String oldPassword, String newPassword) throws Exception;
    void changeSecurityQuestion(String email, String newQuestion, String newAnswer) throws Exception;
    void recoverPassword(String email, String securityAnswer) throws Exception;
}
