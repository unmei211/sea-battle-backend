package it.sevenbits.seabattle.core.service.user;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
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
        if(userRepository.existsById(id)) {
            userRepository.save(userToBeUpdated);
        }
    }

    public void save(User userToSave) {
        userRepository.save(userToSave);
    }
}
