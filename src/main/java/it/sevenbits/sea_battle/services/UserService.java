package it.sevenbits.sea_battle.services;

import it.sevenbits.sea_battle.entity.User;
import it.sevenbits.sea_battle.repository.UserRepository;
import it.sevenbits.sea_battle.services.interfaces.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User> {
    private final UserRepository userRepository;
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public void update(Long id, User userToBeUpdated) {
        if(userRepository.existsById(id)) {
            userRepository.save(userToBeUpdated);
        }
    }

    @Override
    public void save(User userToSave) {
        userRepository.save(userToSave);
    }
}
