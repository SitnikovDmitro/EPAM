package entity;

public class UserX {
    private int role;
    private String password;
    private String username;


    public UserX() {}

    public UserX(int role, String password, String username) {
        this.role = role;
        this.password = password;
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}