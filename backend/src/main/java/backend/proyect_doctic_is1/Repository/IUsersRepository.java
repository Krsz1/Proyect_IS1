package backend.proyect_doctic_is1.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import backend.proyect_doctic_is1.Model.UsersModel;

public interface IUsersRepository extends MongoRepository<UsersModel, String> {
    UsersModel findByUsername(String username);
}
