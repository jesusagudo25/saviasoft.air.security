package ec.com.saviasoft.air.security.service;

import ec.com.saviasoft.air.security.data.UserRepository;
import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUser(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(Integer id, User user) {
        User userToUpdate = repository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setRole(user.getRole());

            return repository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public User setStatus(Integer id, Boolean status) {
        User userToUpdate = repository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setStatus(status);
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
            return repository.save(userToUpdate);
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    /*
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }*/
}
