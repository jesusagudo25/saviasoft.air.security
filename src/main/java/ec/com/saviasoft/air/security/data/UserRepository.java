package ec.com.saviasoft.air.security.data;

import ec.com.saviasoft.air.security.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1%")
    List<User> findByFirstNameAndLastName(String name);

}
