package ec.com.saviasoft.air.security.data;

import ec.com.saviasoft.air.security.model.pojo.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByEmail(String email);
}
