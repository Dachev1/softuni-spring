package bg.softuni.mobilele.model.entity;

import bg.softuni.mobilele.model.enums.UserRoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private UserRoleType name;

    // Getters and setters

    public UserRoleType getName() {
        return name;
    }

    public void setName(UserRoleType name) {
        this.name = name;
    }
}
