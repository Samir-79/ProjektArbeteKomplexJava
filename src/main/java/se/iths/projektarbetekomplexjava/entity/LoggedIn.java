//package se.iths.projektarbetekomplexjava.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//@Entity
//public class LoggedIn {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private boolean loggedIn;
//
//    @JsonIgnore
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
//    private Customer customer;
//
//    @JsonIgnore
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToOne(fetch = FetchType.EAGER, targetEntity = Employee.class)
//    private Employee employee;
//
//    public LoggedIn(boolean loggedIn) {
//        this.loggedIn = loggedIn;
//    }
//
//    public LoggedIn() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public boolean isLoggedIn() {
//        return loggedIn;
//    }
//
//    public void setLoggedIn(boolean loggedIn) {
//        this.loggedIn = loggedIn;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//}