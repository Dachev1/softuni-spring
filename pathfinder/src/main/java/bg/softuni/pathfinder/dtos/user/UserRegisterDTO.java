package bg.softuni.pathfinder.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    @NotBlank(message = "Please enter your username.")
    @Size(min = 5, max = 20, message = "Your username must be between 5 and 20 characters long.")
    private String username;

    @NotBlank(message = "Please provide your full name.")
    @Size(min = 5, max = 20, message = "Your full name must be between 5 and 20 characters long.")
    private String fullName;

    @NotBlank(message = "Please provide your email address.")
    @Email(message = "The email address you entered is not valid. Please enter a valid email.")
    private String email;

    private Integer age;

    @NotBlank(message = "Please enter a password.")
    @Size(min = 5, max = 20, message = "Your password must be between 5 and 20 characters long.")
    private String password;

    @NotBlank(message = "Please confirm your password.")
    @Size(min = 5, max = 20, message = "Your confirmation password must be between 5 and 20 characters long.")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
