package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta de la validacion de un usuario.
 * Contiene la informacion del usuario validado, el password del usuario, y el token de la respuesta.
 * @see UserValidationRequest
 */
@Data
@Builder
public class UserValidationResponse {
    @JsonProperty("user")
    String user;
    @JsonProperty("pwd")
    String pwd;
    @JsonProperty("token")
    String token;

    public UserValidationResponse() {
    }

    public UserValidationResponse(String user, String pwd, String token) {
        this.user = user;
        this.pwd = pwd;
        this.token = token;
    }

    public UserValidationResponse(String validToken) {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
