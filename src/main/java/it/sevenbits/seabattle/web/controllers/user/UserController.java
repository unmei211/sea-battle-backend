package it.sevenbits.seabattle.web.controllers.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user controller
 */
@RestController
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
    public ResponseEntity<?> addUser(
            @RequestBody final UserForm userForm
    ) {
        UserDTO user = userService.save(userForm);
        System.out.println("TEST");
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody final UserForm userForm
    ) {
        try {
            UserDTO user = userService.loginUser(userForm);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
