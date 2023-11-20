package ec.com.saviasoft.air.security.model.request;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    private String token;

    private String password;

    private String passwordConfirm;
}
