package backend.proyect_doctic_is1.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.proyect_doctic_is1.Model.UsersModel;
import backend.proyect_doctic_is1.Repository.IUsersRepository;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private IUsersRepository userRepository;


    @Override
    public UsersModel authenticate(String email, String password) {
        UsersModel user = userRepository.findByEmail(email);
        if (user != null && user.getCredentials().get(0).getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public UsersModel registerUser(UsersModel user) {
        user.setRegistrationDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public UsersModel getUserProfile(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UsersModel updateUserProfile(String id, UsersModel user) {
        UsersModel userRecuperado = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRecuperado.setUsername(user.getUsername());
            userRecuperado.setEmail(user.getEmail());
            userRepository.save(user);
        }
        return user;
    }

}
