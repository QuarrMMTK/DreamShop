package mmtk.backend.dreamshop.controllers;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.exceptions.AlreadyExistsException;
import mmtk.backend.dreamshop.exceptions.ResourceNotFoundException;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import mmtk.backend.dreamshop.requests.LoginUserRequest;
import mmtk.backend.dreamshop.responses.ApiResponse;
import mmtk.backend.dreamshop.services.auth.AuthenticationService;
import mmtk.backend.dreamshop.services.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/*
 * @Author quarr
 * @Created 9/25/24 9:38 AM
 * @Project DreamShop
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody CreateUserRequest userRequest){
        try{
            User registeredUser = authenticationService.signUp(userRequest);
            return ResponseEntity.ok(new ApiResponse("Add User Success", null));
        } catch(AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginUserRequest userRequest){
        try{
            User authenticatedUser = authenticationService.authenticate(userRequest);

            String jwtToken = jwtService.generateToken(authenticatedUser);

            return ResponseEntity.ok(new ApiResponse("Authentication successful", jwtToken));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
