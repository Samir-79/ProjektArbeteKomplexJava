package se.iths.projektarbetekomplexjava.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "First Name may not be empty")
    @Size(min = 2, max = 32, message = "Firstname must be between 2 and 32 characters long")
    private String firstName;

    @NotEmpty(message = "Last Name may not be empty")
    @Size(min = 3, max = 32, message = "Lastname must be between 3 and 32 characters long")
    private String lastName;

    @NotEmpty(message = "Address may not be empty")
    @Size(min = 5, max = 32, message = "Address must be between 5 and 32 characters long")
    private String address;

    @NotEmpty(message = "Phone may not be empty")
    @Size(min = 5, max = 32, message = "Phone must be between 5 and 32 characters long")
    private String phone;

    @NotEmpty(message = "Username may not be empty")
    @Size(min = 2, max = 32, message = "Username must be between 2 and 32 characters long")
    private String username;

    @NotEmpty(message = "Email may not be empty")
    @Size(min = 5, max = 32, message = "Email must be between 5 and 32 characters long")
    private String email;

    @NotEmpty(message = "Password may not be empty")
    @Size(min = 8, message = "Password must be between 8 and 32 characters long")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public Employee(String firstName, String lastName, String address, String phone, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Employee() {
    }

    public void addRole(Role role){
        roles.add(role);
        role.getEmployees().add(this);
    }

    public Set<Role> getRoles(){
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
