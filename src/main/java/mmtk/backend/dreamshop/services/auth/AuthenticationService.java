package mmtk.backend.dreamshop.services.auth;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.exceptions.ResourceNotFoundException;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.repositories.UserRepository;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import mmtk.backend.dreamshop.requests.LoginUserRequest;
import mmtk.backend.dreamshop.services.user.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * @Author quarr
 * @Created 9/25/24 9:10 AM
 * @Project DreamShop
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    public User signUp(CreateUserRequest input){
        return userService.createUser(input);
    }

    public User authenticate(LoginUserRequest input){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userService.findUserByEmail(input.getEmail()).orElse(null);
    }
}
