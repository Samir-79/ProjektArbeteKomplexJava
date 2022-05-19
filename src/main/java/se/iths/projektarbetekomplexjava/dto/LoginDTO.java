package se.iths.projektarbetekomplexjava.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class LoginDTO {
    @NotBlank String userName;
    @NotBlank String password;
}

