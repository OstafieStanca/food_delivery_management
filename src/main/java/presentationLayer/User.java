package presentationLayer;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    private int userId ;
    private static final long serialVersionUID = 1L;

    public User(String username, String email, String password, UserRole userRole, int userId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.userId = userId;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", userId=" + userId +
                '}';
    }
}
