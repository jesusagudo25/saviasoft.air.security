package ec.com.saviasoft.air.security.controller;

import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.ChangeUserPasswordRequest;
import ec.com.saviasoft.air.security.model.request.RegisterRequest;
import ec.com.saviasoft.air.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            @RequestParam (required = false) String name
    ) {
        List<User> userList;

        if (name != null) {
            userList = service.findByName(name);
        } else {
            userList = service.getUsers();
        }

        if (Objects.nonNull(userList)) {
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
       try {
           return ResponseEntity.ok(service.getUser(id));
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RegisterRequest user) {
       try {
           return ResponseEntity.ok(service.createUser(user));
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
       try {
           return ResponseEntity.ok(service.updateUser(id, user));
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<User> setStatus(@PathVariable Integer id, @RequestBody Map<String, Boolean> payload) {
       try {
           return ResponseEntity.ok(service.setStatus(id, payload.get("status")));
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<User> changePassword(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
       try {
           return ResponseEntity.ok(service.changePassword(id, payload));
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
    }

    @PatchMapping
    public ResponseEntity<?> userChangePassword(
            @RequestBody ChangeUserPasswordRequest request,
            Principal connectedUser
    ) {
        try {
            return ResponseEntity.ok(service.userChangePassword(request, connectedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
