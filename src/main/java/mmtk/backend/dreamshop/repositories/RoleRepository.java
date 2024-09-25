package mmtk.backend.dreamshop.repositories;

import mmtk.backend.dreamshop.enums.RoleEnum;
import mmtk.backend.dreamshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * @Author quarr
 * @Created 9/25/24 1:32 PM
 * @Project DreamShop
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}
