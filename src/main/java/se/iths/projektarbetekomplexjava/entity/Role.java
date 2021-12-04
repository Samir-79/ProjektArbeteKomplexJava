package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<Customer> customers;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public Set<Customer> getCustomers(){
        return customers;
    }

    public void setCustomers(Set<Customer> customers){
        this.customers = customers;
    }

    @JsonIgnore
    public Set<Employee> getEmployees(){
        return employees;
    }

    public void setEmployees(Set<Employee> employees){
        this.employees = employees;
    }
}
