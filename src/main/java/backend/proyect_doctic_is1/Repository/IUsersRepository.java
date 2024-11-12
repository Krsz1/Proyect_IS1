package backend.proyect_doctic_is1.Repository;

import backend.proyect_doctic_is1.Model.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUsersRepository extends MongoRepository<UsersModel, String> {
    Optional<UsersModel> findByUsername(String username);
    Optional<UsersModel> findByEmail(String email);
    Optional<UsersModel> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
