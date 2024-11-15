package backend.proyect_doctic_is1.Service;


import backend.proyect_doctic_is1.Model.UsersModel;

public interface IUserService {
    public UsersModel authenticate (String email, String password);
    public UsersModel registerUser (UsersModel user);
    public boolean emailExists(String email);
    public UsersModel getUserProfile(String id);
    public UsersModel updateUserProfile(String id, UsersModel user);
}