package it.sevenbits.seabattle.web.controllers.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.security.auth.AuthRequired;
import it.sevenbits.seabattle.core.security.auth.IUserCredentials;
import it.sevenbits.seabattle.core.security.auth.UserCredentials;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import it.sevenbits.seabattle.web.model.token.ComplexToken;
import it.sevenbits.seabattle.web.model.DeleteUserRequest;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * user controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * constructor
     *
     * @param userService - user service
     */
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * get user
     *
     * @param id - user id
     * @return user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserData(
            @PathVariable final Long id
    ) {
        try {
            UserDTO userDTO = userService.getByIdDTO(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * update user
     *
     * @param id   - user id
     * @param user - user
     */
    @PatchMapping("/{id}")
    public void updateUser(
            @PathVariable final Long id,
            @ModelAttribute(name = "user") final User user
    ) {
        userService.update(id, user);
    }

    /**
     * add a user
     *
     * @param userForm user form (login and password)
     * @return return user without password
     */
    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody final UserForm userForm
    ) {
        userService.save(userForm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ComplexToken> loginUser(
            @RequestBody final UserForm userForm
    ) {
        return new ResponseEntity<>(userService.loginUser(userForm), HttpStatus.OK);
    }

    @AuthRequired
    @GetMapping("/whoami")
    public ResponseEntity<UserDTO> whoami(
            final IUserCredentials userCredentials
    ) {
        User user = userService.getById(userCredentials.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        return new ResponseEntity<>(user.toDTO(), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public void deleteUser(
            @RequestBody final DeleteUserRequest deleteUserRequest) {
        userService.deleteUser(deleteUserRequest.getUserId());
    }
}
