package bg.softuni.mobilele.model.dtos;

import bg.softuni.mobilele.model.entity.Role;

import java.time.LocalDateTime;

public class UserDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String imageUrl;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Role role;

    public UserDto(String username, String password, String firstName, String lastName, Boolean isActive,
                   String imageUrl, LocalDateTime created, LocalDateTime modified, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.created = created;
        this.modified = modified;
        this.role = role;
    }
}
