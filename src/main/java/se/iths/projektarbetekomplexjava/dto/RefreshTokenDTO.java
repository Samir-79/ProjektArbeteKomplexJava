package se.iths.projektarbetekomplexjava.dto;


import lombok.Value;



@Value
public class RefreshTokenDTO {
    String token;
    String type = "Bearer";
    private String refreshToken;

    public RefreshTokenDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

