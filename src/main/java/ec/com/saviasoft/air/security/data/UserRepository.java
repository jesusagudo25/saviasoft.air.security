package ec.com.saviasoft.air.security.data;

import ec.com.saviasoft.air.security.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);
}
