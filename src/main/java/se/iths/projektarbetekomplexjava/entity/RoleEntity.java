package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<CustomerEntity> customers;

    @ManyToMany(mappedBy = "roles")
    private Set<EmployeeEntity> employees;

    public RoleEntity(String role) {
        this.role = role;
    }

    public RoleEntity() {
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
    public Set<CustomerEntity> getCustomers(){
        return customers;
    }

    public void setCustomers(Set<CustomerEntity> customers){
        this.customers = customers;
    }

    @JsonIgnore
    public Set<EmployeeEntity> getEmployees(){
        return employees;
    }

    public void setEmployees(Set<EmployeeEntity> employees){
        this.employees = employees;
    }
}
