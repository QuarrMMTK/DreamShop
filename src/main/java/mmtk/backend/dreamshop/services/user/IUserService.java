package mmtk.backend.dreamshop.services.user;

import mmtk.backend.dreamshop.dtos.UserDto;
import mmtk.backend.dreamshop.models.User;
import mmtk.backend.dreamshop.requests.CreateUserRequest;
import mmtk.backend.dreamshop.requests.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
