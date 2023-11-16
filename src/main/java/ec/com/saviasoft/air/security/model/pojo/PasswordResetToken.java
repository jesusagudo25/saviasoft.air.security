package ec.com.saviasoft.air.security.model.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    private String email;

    private Long expiryDate;

    private Date createdDate = new Date();

}
