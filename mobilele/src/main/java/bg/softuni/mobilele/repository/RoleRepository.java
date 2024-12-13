package bg.softuni.mobilele.repository;

import bg.softuni.mobilele.model.entity.Role;
import bg.softuni.mobilele.model.enums.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(UserRoleType name);

    boolean existsByName(UserRoleType name);
}