package models;

public class Coordinator {
    private int idPersonal;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private int userType;

    public Coordinator(String password, String name, String lastName, String email) {
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userType = 2;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
