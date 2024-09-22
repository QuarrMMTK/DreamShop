package mmtk.backend.dreamshop.requests;

import lombok.Data;

/*
 * @Author quarr
 * @Created 9/25/24 9:21 AM
 * @Project DreamShop
 */
@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
