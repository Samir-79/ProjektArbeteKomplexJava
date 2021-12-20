package se.iths.projektarbetekomplexjava.entity;

import org.hibernate.annotations.Cascade;
import se.iths.projektarbetekomplexjava.service.Role1;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role1 role;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();


    @OneToOne
    private ShoppingCart shoppingCart;

    public Customer(String firstName, String lastName, String address, String phone, String username, String email, String password,Role1 role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role= Role1.USER;
    }

    public Customer() {

    }

//    public void addRole(Role role){
//        roles.add(role);
//        role.getCustomers().add(this);
//    }



    public void addShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
        //shoppingCart.setCustomer(this);
    }

//    public Set<Role> getRoles(){
//        return roles;
//    }

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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }


    public Role1 getRole() {
        return role;
    }

    public void setRole(Role1 role) {
        this.role = role;
    }
}
