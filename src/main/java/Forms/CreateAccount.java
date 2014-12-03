package Forms;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAccount {

    @NotNull
    @Size(min=4, max=30)
    private String Username;

    @NotNull
    @Size(min=6, max=30)
    private String Password;

    @NotNull
    @Size(min=6, max=30)
    private String ConfirmPassword;

    @NotNull
    @Size(min=1,max=20)
    private String FirstName;

    @NotNull
    @Size(min=1,max=20)
    private String LastName;

    @Email
    @NotNull
    private String Email;

    public CreateAccount() {
        this.Username = "";
        this.Password = "";
        this.FirstName = "";
        this.LastName = "";
        this.Email = "";
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String toString() {
        return "Create Account (username: " + this.Username + ", Password: " + this.Password + ")";
    }

}
