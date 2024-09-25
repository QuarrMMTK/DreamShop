package mmtk.backend.dreamshop.controllers;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import mmtk.backend.dreamshop.services.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Author quarr
 * @Created 9/25/24 2:05 PM
 * @Project DreamShop
 */
@RequestMapping("api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final IUserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody CreateUserRequest request){
        User createAdmin = userService.createAdministrator(request);
        return ResponseEntity.ok(createAdmin);
    }
}
