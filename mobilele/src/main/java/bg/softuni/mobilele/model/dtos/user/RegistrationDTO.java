package bg.softuni.mobilele.model.dtos.user;

import jakarta.validation.constraints.NotEmpty;

public record RegistrationDTO(
        @NotEmpty(message = "First name is required.") String firstName,
        @NotEmpty(message = "Last name is required.") String lastName,
        @NotEmpty(message = "Username cannot be empty.") String username,
        @NotEmpty(message = "Password cannot be empty.") String password
) {
}