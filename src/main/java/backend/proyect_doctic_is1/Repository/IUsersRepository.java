package backend.proyect_doctic_is1.Repository;

import backend.proyect_doctic_is1.Model.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUsersRepository extends MongoRepository<UsersModel, String> {
    UsersModel findByUsername(String username);
    UsersModel findByEmail(String email);
}
