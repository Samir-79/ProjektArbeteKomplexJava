package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Entity
public class Role {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//    private String role;
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<Customer> customers = new HashSet<>();
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<Employee> employees = new HashSet<>();
//
//    public Role(String role) {
//        this.role = role;
//    }
//
//    public Role() {
//    }
//
//    public void addCustomer(Customer customer){
//        customers.add(customer);
//        customer.getRoles().add(this);
//    }
//
//    public void addEmployee(Employee employee){
//        employees.add(employee);
//        employee.getRoles().add(this);
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public Set<Customer> getCustomers(){
//        return customers;
//    }
//
//    public void setCustomers(Set<Customer> customers){
//        this.customers = customers;
//    }
//
//    public Set<Employee> getEmployees(){
//        return employees;
//    }
//
//    public void setEmployees(Set<Employee> employees){
//        this.employees = employees;
//    }
}
