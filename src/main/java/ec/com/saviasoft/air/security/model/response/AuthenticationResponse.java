package ec.com.saviasoft.air.security.model.response;

import ec.com.saviasoft.air.security.model.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private Integer id;

    private Role role;
}
