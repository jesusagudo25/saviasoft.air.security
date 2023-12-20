package ec.com.saviasoft.air.security.service;

import ec.com.saviasoft.air.security.data.UserRepository;
import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.ChangePasswordRequest;
import ec.com.saviasoft.air.security.model.request.ChangeUserPasswordRequest;
import ec.com.saviasoft.air.security.model.request.RegisterRequest;
import ec.com.saviasoft.air.security.util.EmailUtil;
import ec.com.saviasoft.air.security.util.PasswordUtil;
import ec.com.saviasoft.air.security.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private EmailUtil emailUtil;

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public List<User> findByName(String name) {
        return repository.findByFirstNameAndLastName(name);
    }

    public User getUser(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public User createUser(RegisterRequest registerRequest) {

        String password = passwordUtil.generateRandomPassword();

        // send email with password
        try {
            emailUtil.sendUserCredentials(registerRequest.getEmail(), password);
        } catch (Exception e) {
            throw new IllegalStateException("Error sending email");
        }

        return repository.save(User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(password))
                .role(registerRequest.getRole())
                .createdDate(new Date())
                .updatedDate(new Date())
                .status(true)
                .build());
    }

    public User updateUser(Integer id, User user) {
        User userToUpdate = repository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setUpdatedDate(new Date());

            return repository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public User setStatus(Integer id, Boolean status) {
        User userToUpdate = repository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setStatus(status);
            userToUpdate.setUpdatedDate(new Date());
            return repository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public User changePassword(Integer id, Map<String, String> payload) {

        if(!payload.get("password").equals(payload.get("passwordConfirm"))) {
            throw new IllegalStateException("Password are not the same");
        }

        User userToUpdate = repository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setPassword(passwordEncoder.encode(payload.get("password")));
            userToUpdate.setUpdatedDate(new Date());
            return repository.save(userToUpdate);
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    public User userChangePassword(ChangeUserPasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("La contraseña actual es incorrecta");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);

        return user;
    }
}
