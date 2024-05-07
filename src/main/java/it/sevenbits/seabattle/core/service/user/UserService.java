package it.sevenbits.seabattle.core.service.user;

import it.sevenbits.seabattle.core.model.token.RefreshToken;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.token.TokenRepository;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.core.security.auth.JwtTokenService;
import it.sevenbits.seabattle.core.security.encrypt.PasswordEncoder;
import it.sevenbits.seabattle.core.util.exceptions.ConflictException;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import it.sevenbits.seabattle.core.util.exceptions.UnauthorizedException;
import it.sevenbits.seabattle.core.validator.session.BadValidException;
import it.sevenbits.seabattle.core.validator.session.StringValidator;
import it.sevenbits.seabattle.web.model.token.ComplexToken;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import it.sevenbits.seabattle.web.model.UserForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * user service
 */
public class UserService {
    private final UserRepository userRepository;
    private final StringValidator stringValidator;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtTokenService tokenService;

    /**
     * constructor
     *
     * @param userRepository - user repos
     */
    public UserService(final UserRepository userRepository, final StringValidator stringValidator,
                       final PasswordEncoder passwordEncoder,
                       final TokenRepository tokenRepository,
                       final JwtTokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.stringValidator = stringValidator;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
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
    public void save(final UserForm userForm) {
        if (!stringValidator.validate(userForm.getLogin()) || !stringValidator.validate(userForm.getPassword())) {
            throw new BadValidException("Bad login or password");
        }

        if (userRepository.findByLogin(userForm.getLogin()).isPresent()) {
            throw new ConflictException("user is exist");
        }

        User userToSave = new User();
        userToSave.setLogin(userForm.getLogin());
        userToSave.setPassword(passwordEncoder.encrypt(userForm.getPassword()));
        userToSave.setRating(0);

        userRepository.save(userToSave);
    }

    public ComplexToken loginUser(final UserForm userForm) {
        if (!stringValidator.validate(userForm.getLogin()) || !stringValidator.validate(userForm.getPassword())) {
            throw new BadValidException("Bad login or password");
        }

        User user = userRepository.findByLogin(userForm.getLogin()).orElseThrow(
                () -> new UnauthorizedException("user not found")
        );

        if (!passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("no matches pass");
        }

        String refreshToken = tokenService.createOrUpdateRefreshToken(user);
        String accessToken = tokenService.createAccessToken(user);

        return new ComplexToken(accessToken, refreshToken);
    }

    public void changeRating(final Long playerId, final Integer rating) {
        User user = userRepository.findById(playerId).get();
        if (user.getRating() - rating <= 0) {
            user.setRating(user.getRating() + rating);
        }
        userRepository.save(user);
    }

    public void deleteUser(final Long id) {
        if (userRepository.existsById(id)) {
            tokenRepository.deleteByUser(userRepository.findById(id).get());
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("user not found");
        }
    }
}
