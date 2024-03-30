package it.sevenbits.seabattle.core.service.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * user service
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * constructor
     *
     * @param userRepository - user repos
     */
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * get user by id
     *
     * @param id - user id
     * @return user
     */
    public Optional<User> getById(final Long id) {
        return userRepository.findById(id);
    }

    /**
     * get user dto by id
     *
     * @param id - user id
     * @return user dto
     */
    public UserDTO getByIdDTO(final Long id) {
        Optional<User> user = userRepository.findById(id);
        return new UserDTO(user.get().getId(), user.get().getLogin(), user.get().getRating());
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * remove user from db
     *
     * @param id - user id
     */
    public void remove(final Long id) {
        userRepository.deleteById(id);
    }

    /**
     * update user
     *
     * @param id              - user id
     * @param userToBeUpdated user
     */

    public void update(final Long id, final User userToBeUpdated) {
        if (userRepository.existsById(id)) {
            userRepository.save(userToBeUpdated);
        }
    }

    /**
     * save user
     *
     * @param userForm - user form (login and password)
     * @return userDTO
     */
    public UserDTO save(final UserForm userForm) {
        User userToSave = new User();
        userToSave.setLogin(userForm.getLogin());
        userToSave.setPassword(userForm.getPassword());
        userToSave.setRating(0);
        User user = userRepository.findUserByLogin(userForm.getLogin());
        if (user != null) {
            return null;
        }
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getLogin(), userToSave.getRating());
    }

    public UserDTO loginUser(final UserForm userForm) {
        User user = userRepository.findUserByLogin(userForm.getLogin());
        if (user == null) {
            return null;
        } else {
            if (Objects.equals(user.getPassword(), userForm.getPassword())) {
                return user.toDTO();
            } else {
                return null;
            }
        }
    }
}
