package se.iths.projektarbetekomplexjava.dto;

import se.iths.projektarbetekomplexjava.entity.Role;

import java.util.Collection;
import java.util.List;


public class CustomerDTO {
 String jwt;
 Collection authorities;
 String userName;
 List<String> role;

 public CustomerDTO(String jwt, Collection authorities, String userName, List<String> role) {
  this.jwt = jwt;
  this.authorities = authorities;
  this.userName = userName;
  this.role = role;
 }
}


