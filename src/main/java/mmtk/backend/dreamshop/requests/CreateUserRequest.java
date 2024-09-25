package mmtk.backend.dreamshop.requests;

import lombok.Data;
import mmtk.backend.dreamshop.models.Role;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
