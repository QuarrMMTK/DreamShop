package mmtk.backend.dreamshop.services.user;

import mmtk.backend.dreamshop.dtos.UserDto;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import mmtk.backend.dreamshop.requests.UserUpdateRequest;

import java.util.Optional;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    Optional<User> findUserByEmail(String email);

    UserDto convertUserToDto(User user);
}
