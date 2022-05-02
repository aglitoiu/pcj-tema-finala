package ro.aglitoiu.tema_finala_pcj.UserService.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.aglitoiu.tema_finala_pcj.UserService.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
