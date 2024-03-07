package it.sevenbits.sea_battle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.sea_battle.entity.User;
import it.sevenbits.sea_battle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objMapper;

    @Autowired
    public UserController(final UserService userService, final ObjectMapper objectMapper) {
        this.userService = userService;
        this.objMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public String getUserData(
            @PathVariable Long id
    ) {
        User user = userService.getById(id).get();
        String jsonUserData;
        try {
            jsonUserData = objMapper.writeValueAsString(user);
        } catch (JsonProcessingException exception) {
            jsonUserData = "error parse";
        }
        return jsonUserData;
    }

    @PatchMapping("/{id}")
    public void updateUser(
            @PathVariable Long id,
            @ModelAttribute(name = "user") User user
    ) {
        userService.update(id, user);
    }

    @PostMapping
    public void saveUser(
//            User user
    ) {
        User user = new User();
        user.setLogin("Name");
        user.setPassword("Pass");
        user.setRating(5L);

        userService.save(user);
    }
}
