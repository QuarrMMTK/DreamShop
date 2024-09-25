package mmtk.backend.dreamshop;

import lombok.RequiredArgsConstructor;
import mmtk.backend.dreamshop.enums.RoleEnum;
import mmtk.backend.dreamshop.models.Role;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.repositories.RoleRepository;
import mmtk.backend.dreamshop.repositories.UserRepository;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * @Author quarr
 * @Created 9/25/24 2:08 PM
 * @Project DreamShop
 */
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        CreateUserRequest superAdminUser = new CreateUserRequest();
        superAdminUser.setFirstName("Super");
        superAdminUser.setLastName("Admin");
        superAdminUser.setEmail("superadminuser@gmail.com");
        superAdminUser.setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(superAdminUser.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }
        var user = new User();
        user.setFirstName(superAdminUser.getFirstName());
        user.setLastName(superAdminUser.getLastName());
        user.setEmail(superAdminUser.getEmail());
        user.setPassword(passwordEncoder.encode(superAdminUser.getPassword()));
        user.setRole(optionalRole.get());
    userRepository.save(user);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
