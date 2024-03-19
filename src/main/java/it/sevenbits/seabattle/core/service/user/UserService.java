package it.sevenbits.seabattle.core.service.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO getByIdDTO(Long id) {
        Optional<User> user = userRepository.findById(id);
        return new UserDTO(user.get().getId(), user.get().getLogin(), user.get().getRating());
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public void remove(User user) {
        userRepository.delete(user);
    }

    public void update(Long id, User userToBeUpdated) {
        if (userRepository.existsById(id)) {
            userRepository.save(userToBeUpdated);
        }
    }

    public UserDTO save(UserForm userForm) {
        User userToSave = new User();
        userToSave.setLogin(userForm.getLogin());
        userToSave.setPassword(userForm.getPassword());
        userToSave.setRating(0);
        userRepository.save(userToSave);
        return new UserDTO(userToSave.getId(), userToSave.getLogin(), userToSave.getRating());
    }
}
