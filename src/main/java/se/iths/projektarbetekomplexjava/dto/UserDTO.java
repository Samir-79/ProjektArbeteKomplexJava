package se.iths.projektarbetekomplexjava.dto;


import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Value
public class UserDTO {
 String token;
 String type = "Bearer";
 private String refreshToken;
 String userName;
 List<String> role;

 public UserDTO(String token, String refreshToken, String userName, List<String> role) {
  this.token = token;
  this.refreshToken = refreshToken;
  this.userName = userName;
  this.role = role;
 }
}



