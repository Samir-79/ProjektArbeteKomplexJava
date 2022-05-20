package se.iths.projektarbetekomplexjava.dto;


import lombok.Value;

import java.util.List;


@Value
public class UserDTO {
 String token;
 String type = "Bearer";
 String userName;
 List<String> role;

 public UserDTO(String accessToken, String userName, List<String> role) {
  this.token = accessToken;
  this.userName = userName;
  this.role = role;
 }
}


