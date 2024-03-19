package it.sevenbits.seabattle.web.controllers.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserData(
            @PathVariable Long id
    ) {
        try {
            UserDTO userDTO = userService.getByIdDTO(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public void updateUser(
            @PathVariable Long id,
            @ModelAttribute(name = "user") User user
    ) {
        userService.update(id, user);
    }

    @PostMapping
    public UserDTO addUser(
            @RequestBody UserForm userForm
    ) {
        return userService.save(userForm);
    }
}
