package ec.com.saviasoft.air.security.controller;

import ec.com.saviasoft.air.security.model.pojo.User;
import ec.com.saviasoft.air.security.model.request.ChangePasswordRequest;
import ec.com.saviasoft.air.security.model.request.RegisterRequest;
import ec.com.saviasoft.air.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
       try {
           return ResponseEntity.ok(service.getUsers());
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
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

    /*@PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }*/
}
