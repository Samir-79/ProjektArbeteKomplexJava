package se.iths.projektarbetekomplexjava.dto;


import lombok.Value;

import java.util.List;


@Value
public class CustomerDTO {
 String token;
 String type = "Bearer";
 String userName;
 List<String> role;

 public CustomerDTO(String accessToken, String userName, List<String> role) {
  this.token = accessToken;
  this.userName = userName;
  this.role = role;
 }
}


